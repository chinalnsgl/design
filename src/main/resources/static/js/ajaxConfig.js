$.ajaxSetup({
  cache:false,
  complete: function (XMLHttpRequest, textStatus) {
    if (textStatus == "parsererror") {
      window.top.location.href = "/";
    }
  }
});

$.fn.dataTable.ext.errMode = 'none';