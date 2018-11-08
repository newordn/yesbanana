document.addEventListener('keypress', function (e) {

    var key =e.keyCode;
    if (key == 13) {
        $("#submit").click();
    }
});