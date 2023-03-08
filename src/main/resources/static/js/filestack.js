"use strict";

$(function () {

    const client = filestack.init($("#apikey").val());

    $("#test").on("click", function (e) {
        e.preventDefault();
        console.log(e)
        // client.picker().open();
        // client.picker.onFileUploadFinished(data=>console.log(data))
        console.log(e)
        client.picker({
            accept: ["image/*"],
            transformations: {
                circle: true,
                crop: false,
                rotate: true,
                force: true
            },
            onFileUploadFinished: function (file) {
                // console.log(file)
                $("#image").val(file.url).submit();
                // print.console.log(file.url);
            }
        }).open()
    });
});