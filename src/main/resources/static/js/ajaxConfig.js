$.ajaxSetup({
  complete: function (XMLHttpRequest, textStatus) {
    if (textStatus == "parsererror") {
      window.top.location.href = "/";
    }
  }
});