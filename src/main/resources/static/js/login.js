$(function () {
  // 验证初始化
  $.validate.init("userForm",{
    fields: {
      userName: {
        validators: {
          notEmpty: {
            message: '请输入帐号'
          }
        }
      },
      password: {
        validators: {
          notEmpty: {
            message: '请输入密码'
          }
        }
      }
    }
  });
  // 登录
  $("#btn_login").click(function () {
    if ($.validate.isValid()) {
      $.operate.login(ctx + "login", ctx + "index", $("#userForm").serialize());
    }
  });
  // 回车
  $.enter.keydown("btn_login");
});