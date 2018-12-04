$.ajaxSetup({
  cache:false,
  complete: function (XMLHttpRequest, textStatus) {
    if (textStatus == "parsererror") {
      $.modal.confirm("登录超时，请重新登录",function () {
        window.top.location.href = "/";
      })
    }
  }
});

$.fn.dataTable.ext.errMode = 'none';