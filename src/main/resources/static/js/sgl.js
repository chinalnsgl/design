/**
 * 通用JS方法封装处理
 */
(function ($) {
  $.extend({
    // 表格封装处理
    table: {
      _table: {},
      _selector: {},
      // 初始化表格
      init: function(options) {
        $.table._selector = $("#" + options.id);
        $.table._table = $.table._selector
            .on( 'preXhr.dt', function () {                                                           // ajax 请求之前处理
              $.modal.loading("处理中，请稍假...");
            })
            .on( 'xhr.dt', function () {                                                              // ajax 请求之后处理
              $.modal.closeLoading();
            })
            .on( 'draw.dt', function () {                                                             // 表格绘制完成处理
              if (options.drawCallback) {
                options.drawCallback();
              }
            }).DataTable({
              buttons: options.buttons ? options.buttons : {},                                          // 按钮配置（导出等）
              fixedHeader: options.fixedHeader ? options.fixedHeader : false,                           // 固定表头
              scrollX: options.scrollX ? options.scrollX : false,                                       // 水平滚动条
              scrollY: false,                                                                           // 重直滚动条
              info: options.info ? options.info : false,                                                // 表格左下角的信息
              ordering: false,                                                                          // 开启排序
              autoWidth: options.autoWidth ? options.autoWidth : true,                                  // 自适应宽度
              lengthChange: false,                                                                      // 允许用户改变表格每页显示的记录数
              pageLength: options.pageLength ? options.pageLength : 300,                                // 每页多少条数据
              dom: options.dom ? options.dom : "Tft<'row DTTTFooter'<'col-sm-6'li><'col-sm-6'p>>",      // 组件元素的显示和显示顺序
              searching: false,                                                                         // 开启本地搜索
              processing: true,                                                                         // 显示处理状态
              serverSide: true,                                                                         // 开启服务器模式
              deferRender: true,                                                                        // 延迟渲染
              ajax: {                                                                                   // ajax请求
                url : options.url,
                type : options.type ? options.type : "POST",
                data : options.data ? options.data : {},
                dataSrc : function (result) {                                                           // ajax请求之后处理数据
                  if (result.status === 200) {
                    result.draw = result.content.draw;
                    result.recordsTotal = result.content.recordsTotal;
                    result.recordsFiltered = result.content.recordsFiltered;
                    result.data = result.content.data;
                  }
                  return result.data;
                }
              },
              columns: options.columns,                                                                  // 列配置
              createdRow: options.createdRow ? options.createdRow : function (node) {  },               // 创建行事件
              language : {                                                                               // 国际化配置
                "sProcessing" : "正在获取数据，请稍后...",
                "sLengthMenu" : "显示 _MENU_ 条",
                "sZeroRecords" : "没有找到数据",
                "info" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
                "infoEmpty" : "记录数为0",
                "infoFiltered" : "(全部记录数 _MAX_ 条)",
                "paginate" : {
                  "first" : "第一页",
                  "previous" : "上一页",
                  "next" : "下一页",
                  "last" : "最后一页"
                }
              }
            });
      },
      // 搜索
      search: function(data) {
        $.table._table.settings()[0].ajax.data = data;
        $.table._table.ajax.reload();
      },
      // 行点击事件
      rowClick: function (callback) {
        if (callback) {
          $.table._selector.on('click', 'tbody tr', function () {
            if ($(this).hasClass("active")) {
              return false;
            } else {
              $.table._selector.find("tr").removeClass("active");
              $(this).addClass("active");
              callback($.table._table.row(this).data());
            }
          });
        }
      },
      // 刷新
      refresh: function () {
        $.table._table.ajax.reload(null, false);
      },
      reload: function () {
        $.table._table.ajax.reload();
      }
    },
    // 表单封装处理
    form: {
      // 表单重置
      reset: function(formId) {
        var currentId = $.common.isEmpty(formId) ? $('form').attr('id') : formId;
        $("#" + currentId)[0].reset();
      },
      // 获取选中复选框项
      selectCheckeds: function(name) {
        var checkeds = "";
        $('input:checkbox[name="' + name + '"]:checked').each(function(i) {
          if (0 === i) {
            checkeds = $(this).val();
          } else {
            checkeds += ("," + $(this).val());
          }
        });
        return checkeds;
      }
    },
    // 弹出层封装处理
    modal: {
      // 消息提示
      msg: function(content,type) {
        switch (type) {
          case alertStatus.SUCCESS:
            toastr.success(content);
            break;
          case alertStatus.FAIL:
            toastr.error(content);
            break;
          case alertStatus.WARNING:
            toastr.warning(content);
            break;
          default:
            toastr.info(content);
        }
      },
      // 错误消息
      fail: function(content) {
        $.modal.msg(content, alertStatus.FAIL);
      },
      // 成功消息
      success: function(content) {
        $.modal.msg(content, alertStatus.SUCCESS);
      },
      // 警告消息
      warning: function(content) {
        $.modal.msg(content, alertStatus.WARNING);
      },
      // 打开模态框
      open: function (id) {
        $("#" + id).modal('show');
      },
      // 关闭模态框
      hide: function (id) {
        $("#" + id).modal('hide');
      },
      // 打开遮罩层
      loading: function (message) {
        $.blockUI({ message: '<div class="loaderbox"><div class="loading-activity"></div> ' + message + '</div>' });
      },
      // 关闭遮罩层
      closeLoading: function () {
        setTimeout(function(){
          $.unblockUI();
        }, 50);
      },
      // 确认消息
      confirm: function (content,callBack) {
        bootbox.confirm(content, function (result) {
          if (result) {
            callBack();
          }
        })
      }
    },
    // 操作封装处理
    operate: {
      // 提交数据
      submit: function(url, type, dataType, data, callBack) {
        $.modal.loading("处理中，请稍后...");
        var config = {
          url: url,
          type: type,
          traditional: true,
          dataType: dataType,
          data: data,
          success: function(result) {
            if (callBack) {
              $.modal.closeLoading();
              $.modal.success("操作成功！O(∩_∩)O~ ！");
              if (result.status === web_status.SUCCESS) {
                callBack(result);
              } else {
                $.modal.fail("操作失败 (┬＿┬) ！！");
              }
            } else {
              $.operate.ajaxSuccess(result);
            }
          },
          error: function () {
            $.modal.closeLoading();
            $.modal.fail("系统错误！");
          }
        };
        $.ajax(config)
      },
      // post请求传输
      post: function(url, data, callBack) {
        $.operate.submit(url, "post", "json", data, callBack);
      },
      // 登录
      login: function (url, redirect, data) {
        $.operate.post(url, data, function (result) {
          if (result.status === web_status.SUCCESS) {
            location.href = redirect;
          } else {
            $.modal.closeLoading();
            $.modal.fail("帐号密码错误");
          }
        })
      },
      get: function (url, data, callBack) {
        var config = {
          url: url,
          type: "get",
          dataType: "json",
          data: data,
          success: function(result) {
            if (callBack) {
              callBack(result);
              $.modal.closeLoading();
            } else {
              $.operate.ajaxSuccess(result);
            }
          },
          error: function () {
            $.modal.closeLoading();
            $.modal.fail("系统错误！");
          }
        };
        $.ajax(config)
      },
      // 保存信息
      save: function(url, data, modalId) {
        $.modal.loading("处理中，请稍后...");
        var config = {
          url: url,
          type: "post",
          dataType: "json",
          data: data,
          success: function(result) {
            $.operate.saveSuccess(result, modalId);
          }
        };
        $.ajax(config)
      },
      // 保存结果弹出msg刷新table表格
      ajaxSuccess: function (result) {
        if (result.status === web_status.SUCCESS) {
          $.modal.success(result.content);
        } else {
          $.modal.fail(result.content);
        }
        $.modal.closeLoading();
      },
      // 保存结果提示msg
      saveSuccess: function (result, modalId) {
        if (result.status === web_status.SUCCESS) {
          $.modal.hide(modalId);
          $.modal.success(result.content);
        } else {
          $.modal.fail(result.content);
        }
        $.modal.closeLoading();
      }
    },
    // 校验封装处理
    validate: {
      _from : {},
      // 验证初始化
      init: function (formId, option) {
        $.validate._form = $("#" + formId);
        var _option = {
          feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
          }
        };
        $.validate._form.bootstrapValidator($.extend({},_option,option));
      },
      // 表单验证
      validing: function () {
        $.validate._form.bootstrapValidator('validate');
      },
      // 是否验证成功
      isValid: function(){
        $.validate._form.bootstrapValidator('validate');
        return $.validate._form.data('bootstrapValidator').isValid();
      },
      reset: function () {
        $.validate._form.data('bootstrapValidator').resetForm();
      }
    },
    // 回索
    enter: {
      // 回车搜索
      keydown: function (id) {
        $("body").keydown(function(event) {
          if (event.keyCode === 13) {//keyCode=13是回车键
            $("#" + id).click();
          }
        });
      }
    },
    // 树插件封装处理
    tree: {
      _tree: {},
      _option: {},
      // 初始化树结构
      init: function(options) {
        $.tree._option = options;
        $.tree._tree = $.fn.zTree.init($("#" + options.id),{
          async: {
            enable: true,                                                                     // 采用异步加载
            dataFilter: options.ajaxDataFilter ? options.ajaxDataFilter : $.tree.ajaxDataFilter,// 预处理数据
            url: options.url,                                                                 // url
            dataType: options.dataType ? options.dataType : "json",                           // 数据类型
            type: options.type ? options.type : "get"                                         // 请求方式
          },
          check: {
            enable: !options.check,                                                                     // 是否显示多选框
            chkStyle: options.chkStyle ? options.chkStyle : 'checkbox',
            chkboxType: { "Y": "p", "N": "" },                                                 // 父子节点的关联关系   Y 属性定义 checkbox 被勾选后的情况；N 属性定义 checkbox 取消勾选后的情况； "p" 表示操作会影响父级节点； "s" 表示操作会影响子级节点。
            radioType: "all"
          },
          callback: {
            beforeClick: function () {                                                       // 节点不可选择
              return !!options.click;
            },
            onClick: function (event, treeId, treeNode) {
              if (options.click) {
                options.click(treeNode);
              }
            },
            onAsyncSuccess: function () {
              $.tree._tree.expandAll(true);
            }
          },
          view: {
            dblClickExpand: false,                                                            // 双击节点时，是否自动展开父节点的标识
            showLine: true,                                                                   // 否显示节点之间的连线。
            selectedMulti: false,                                                             // 是否允许同时选中多个节点。
            txtSelectedEnable: false,                                                         // 是否允许可以选择 zTree DOM 内的文本。
            addHoverDom: options.addHoverDom ? options.addHoverDom : function () {},           // 鼠标移入显示自定义控件
            removeHoverDom: options.removeHoverDom ? options.removeHoverDom : function () {}   // 鼠标移出隐藏自定义控件
          },
          data: {
            key: {
              checked: options.checked ? options.checked : "checkFlag",                       // 节点数据中保存 check 状态的属性名称。
              children: options.children ? options.children :"sub",                      // 节点数据中保存子节点数据的属性名称。
              name: options.name ? options.name : "name",                                     // 节点数据保存节点名称的属性名称
              url: null                                                                       // 点数据保存节点链接的目标 URL 的属性名称。
            },
            simpleData: {                                                                     // 数据是否采用简单数据模式 (Array)
              enable:true,                                                                    // 数据是否采用简单数据模式 (Array)
              idKey: options.idKey ? options.idKey : "id",                                    // 节点数据中保存唯一标识的属性名称
              pIdKey: options.pIdKey ? options.pIdKey : "pid",                                // 节点数据中保存其父节点唯一标识的属性名称。
              rootPId: options.rootPId ? options.rootPId : null                               // 用于修正根节点父节点数据，即 pIdKey 指定的属性值。
            }
          }
        },null);
      },
      // 处理数据
      ajaxDataFilter: function(treeId, parentNode, responseData) {
        var treeData = [];
        $.each(responseData,function (i, v) {
          if (v.parent) {
            v.pid = v.parent.id;
          } else {
            v.pid = null;
          }
          treeData.push(v);
        });
        return treeData;
      },
      // 获取当前被勾选集合
      getCheckedNodes: function() {
        var nodes = $.tree._tree.getCheckedNodes(true);
        return $.map(nodes, function (node) {
          return node.id;
        });
      },
      // 刷新
      remove: function (node) {
        $.tree._tree.removeNode(node);
      },
      refresh: function () {
        $.tree._tree.reAsyncChildNodes(null, "refresh");
      },
      cancelSelect: function (node) {
        $.tree._tree.cancelSelectedNode(node);
      }
    },
    // 树插件封装处理
    doubleTree: {
      _tree: {},
      _option: {},
      // 初始化树结构
      init: function(options) {
        $.doubleTree._option = options;
        $.doubleTree._tree = $.fn.zTree.init($("#" + options.id),{
          async: {
            enable: true,                                                                     // 采用异步加载
            dataFilter: options.ajaxDataFilter ? options.ajaxDataFilter : $.tree.ajaxDataFilter,// 预处理数据
            url: options.url,                                                                 // url
            dataType: options.dataType ? options.dataType : "json",                           // 数据类型
            type: options.type ? options.type : "get"                                         // 请求方式
          },
          check: {
            enable: true,                                                                     // 是否显示多选框
            chkboxType: { "Y": "p", "N": "" }                                                 // 父子节点的关联关系   Y 属性定义 checkbox 被勾选后的情况；N 属性定义 checkbox 取消勾选后的情况； "p" 表示操作会影响父级节点； "s" 表示操作会影响子级节点。
          },
          callback: {
            beforeClick: function () {                                                       // 节点不可选择
              return false;
            },
            onAsyncSuccess: function () {
              $.doubleTree._tree.expandAll(true);
            }
          },
          view: {
            dblClickExpand: false,                                                            // 双击节点时，是否自动展开父节点的标识
            showLine: true,                                                                   // 否显示节点之间的连线。
            selectedMulti: false,                                                             // 是否允许同时选中多个节点。
            txtSelectedEnable: false,                                                         // 是否允许可以选择 zTree DOM 内的文本。
            addHoverDom: options.addHoverDom ? options.addHoverDom : function () {},           // 鼠标移入显示自定义控件
            removeHoverDom: options.removeHoverDom ? options.removeHoverDom : function () {}   // 鼠标移出隐藏自定义控件
          },
          data: {
            key: {
              checked: options.checked ? options.checked : "checkFlag",                       // 节点数据中保存 check 状态的属性名称。
              children: options.children ? options.children :"sub",                      // 节点数据中保存子节点数据的属性名称。
              name: options.name ? options.name : "name",                                     // 节点数据保存节点名称的属性名称
              url: null                                                                       // 点数据保存节点链接的目标 URL 的属性名称。
            },
            simpleData: {                                                                     // 数据是否采用简单数据模式 (Array)
              enable:true,                                                                    // 数据是否采用简单数据模式 (Array)
              idKey: options.idKey ? options.idKey : "id",                                    // 节点数据中保存唯一标识的属性名称
              pIdKey: options.pIdKey ? options.pIdKey : "pid",                                // 节点数据中保存其父节点唯一标识的属性名称。
              rootPId: options.rootPId ? options.rootPId : null                               // 用于修正根节点父节点数据，即 pIdKey 指定的属性值。
            }
          }
        },null);
      },
      // 处理数据
      ajaxDataFilter: function(treeId, parentNode, responseData) {
        var treeData = [];
        $.each(responseData,function (i, v) {
          if (v.parent) {
            v.pid = v.parent.id;
          } else {
            v.pid = null;
          }
          treeData.push(v);
        });
        return treeData;
      },
      // 获取当前被勾选集合
      getCheckedNodes: function() {
        var nodes = $.doubleTree._tree.getCheckedNodes(true);
        return $.map(nodes, function (node) {
          return node.id;
        });
      },
      // 刷新
      remove: function (node) {
        $.doubleTree._tree.removeNode(node);
      },
      refresh: function () {
        $.doubleTree._tree.reAsyncChildNodes(null, "refresh");
      },
      destroy: function () {
        $.fn.zTree.destroy($.doubleTree._option.id);
      }
    },
    // 通用方法封装处理
    common: {
      // 判断字符串是否为空
      isEmpty: function (value) {
        return value == null || this.trim(value) === "";
      },
      // 判断一个字符串是否为非空串
      isNotEmpty: function (value) {
        return !$.common.isEmpty(value);
      },
      // 空格截取
      trim: function (value) {
        if (value == null) {
          return "";
        }
        return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
      },
      // 指定随机数返回
      random: function (min, max) {
        return Math.floor((Math.random() * max) + min);
      }
    }
  });
})(jQuery);

/** 消息状态码 */
web_status = {
  SUCCESS: 200,
  FAIL: 400
};

/** 弹窗状态码 */
alertStatus = {
  SUCCESS: 1,
  FAIL: 2,
  WARNING: 3
};

// 日期控件配置
$('.datepicker').datepicker({
  format: 'yyyy-mm-dd',
  language: 'zh-cn',
  autoclose: true,
  clearBtn: true
});

/** 设置bootbox中文 */
bootbox.setLocale("zh_CN");

var topDistance = 500;
var showDistance = 1;
var thisTop = $(window).scrollTop() + topDistance;
if ($(window).scrollTop() < showDistance) {
  $('#goToTop').hide();
}
$(window).scroll(function () {
  thisTop = $(this).scrollTop() + topDistance;        //获取当前window向上滚动的距离
  if ($(this).scrollTop() > showDistance) {
    $('#goToTop').fadeIn();
  } else {
    $('#goToTop').fadeOut();
  }
});