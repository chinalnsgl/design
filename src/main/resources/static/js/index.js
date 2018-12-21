function changeFrameHeight() {
  var ifm = document.getElementById("content");
  ifm.height = document.documentElement.clientHeight - 56;
}

window.onresize = function () {
  changeFrameHeight();
};

function getReceiver() {
  $("#unread-message").html('');
  $("#messageCount").html('');
  $.operate.get(ctx + "user/receiver", $("#userForm").serialize(), function (result) {
    if (result.content.length > 0) {
      $("#messageCount").html(result.content.length);
      // $("#titleQuery-message").html('未读消息');
      var data = result.content;
      for (var i=0;i<data.length;i++){
        var img = data[i].message.user.img;
        if (img == null) {
          img = ctx + "img/person.png";
        }
        $("#unread-message").append('<li><ul class="menu"><li><a class="message-single" href="' + ctx + 'board/single/' + data[i].project.id + '/' + data[i].id +
            '" target="content"><div class="pull-left">' +
            '<img class="img-circle" alt="User Image" src="' + img
            + '"></div><h4>' + data[i].message.user.name +
            '<small><i class="fa fa-clock-o"></i>' + data[i].message.createTime +
            '</small></h4>' +
            '<p>' +data[i].message.content +
            '</p></a></li></ul></li>');
      }
    }
  });
}

var initCropperInModal = function(img, input, modal){
  var $image = img;
  var $inputImage = input;
  var $modal = modal;
  var options = {
    aspectRatio: 1, // 纵横比
    viewMode: 2,
    preview: '.img-preview' // 预览图的class名
  };
  // 模态框隐藏后需要保存的数据对象
  var saveData = {};
  var URL = window.URL || window.webkitURL;
  var blobURL;
  $modal.on('show.bs.modal',function () {
    // 如果打开模态框时没有选择文件就点击“打开图片”按钮
    if(!$inputImage.val()){
      $inputImage.click();
    }
  }).on('shown.bs.modal', function () {
    // 重新创建
    $image.cropper( $.extend(options, {
      ready: function () {
        // 当剪切界面就绪后，恢复数据
        if(saveData.canvasData){
          $image.cropper('setCanvasData', saveData.canvasData);
          $image.cropper('setCropBoxData', saveData.cropBoxData);
        }
      }
    }));
  }).on('hidden.bs.modal', function () {
    // 保存相关数据
    saveData.cropBoxData = $image.cropper('getCropBoxData');
    saveData.canvasData = $image.cropper('getCanvasData');
    // 销毁并将图片保存在img标签
    $image.cropper('destroy').attr('src',blobURL);
  });
  if (URL) {
    $inputImage.change(function() {
      var files = this.files;
      var file;
      if (!$image.data('cropper')) {
        return;
      }
      if (files && files.length) {
        file = files[0];
        if (/^image\/\w+$/.test(file.type)) {

          if(blobURL) {
            URL.revokeObjectURL(blobURL);
          }
          blobURL = URL.createObjectURL(file);

          // 重置cropper，将图像替换
          $image.cropper('reset').cropper('replace', blobURL);

          // 选择文件后，显示和隐藏相关内容
          $('.img-container').removeClass('hidden');
          $('.img-preview-box').removeClass('hidden');
          $('#btn-upload').removeAttr('disabled').removeClass('disabled');
          $('.tip-info').addClass('hidden');

        } else {
          window.alert('请选择一个图像文件！');
        }
      }
    });
  } else {
    $inputImage.prop('disabled', true).addClass('disabled');
  }
}

var sendPhoto = function(){
  var photo = $('#photo').cropper('getCroppedCanvas', {
    width: 300,
    height: 300
  }).toDataURL('image/png');
  $.operate.post(ctx + "update/user/img", {imageData : photo}, function (result) {
    $('#userImage').attr('src', result.content);
    $.modal.hide("modal-upload");
  });
}

$(function () {
  // iframe 自动高度
  changeFrameHeight();

  // 验证初始化
  $.validate.init("userForm",{
    fields: {
      password: {validators: {notEmpty: {message: '请输入密码'}}},
      rePassword: {validators: {notEmpty: {message: '请确认密码'},identical: { field: 'password', message: '两次密码不一致'}}}
    }
  });
  // 修改密码
  $("#btn-updatePassword").click(function () {
    if ($.validate.isValid()) {
      $.operate.save(ctx + "update/user/pwd",$("#userForm").serialize(), "modal-updatePassword");
    }
  });

  $(".treeview-menu>li").click(function () {
    $(".treeview-menu>li").removeClass("active");
    $(this).addClass("active");
  });

  getReceiver();

  initCropperInModal($('#photo'),$('#photoInput'),$('#modal-upload'));

});