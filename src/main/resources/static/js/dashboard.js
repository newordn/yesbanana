$("#img").mouseenter(
    ()=>{
        $("#upload").css("opacity", 1);
    }
)
$("#img").mouseleave(
    () => {
        $("#upload").css("opacity", 0);
    }
)
// handling bibliography and library adding
 // libray
$("#confirmLibraryAdding").click(()=>{
    let libVal= $("#library").val();
    $("#library").val( libVal + ($("#libraryContent").val())+";")
    $("#libraryList").append('<li class="list-group-item">'+ 
    $("#libraryContent").val()+'</li>');
    $("#library").attr("value", $("#library").val())
})
// bibliography
$("#confirmBibliographyAdding").click(() => {
    let biblioVal = $("#bibliographyAuthor").val() + ":" + $("#bibliographyTitle").val() + ":" + $("#bibliographyContent").val();
    $("#bibliography").val(biblioVal + ($("#bibliography").val()))
    $("#bibliographyList").append('<li class="list-group-item">' +
        $("#bibliographyTitle").val() + '</li>');

$("#bibliography").attr("value", $("#bibliography").val())
})

// file picker
$("#addButton").click(() => {

    $("#abstract_file").click();
document.getElementById("abstract_file").addEventListener('change', handleImg, false);
function handleImg() {
    let img = this.files[0];
    console.log(img);
    $("#abstract").val(img.name);
}
});
// submitting update form
$("#confirmOldPass").click(()=>{
    $.ajax({
    url: "http://localhost:8080/" + $("#oldPassword").val()
}).done((data) => {
    if(data)
    $("#submitUpdateForm").click();

else
{
    $("#oldPasswordError").text("Erreur de mot de passe");
}
})
})
// toggle password update inputs
$("#passwordToggle").change(()=>{
    if($("#passwordToggle").is(":checked"))
{
    $("#passwordInputs").css("display","flex");
}
else
{
    $("#passwordInputs").css("display", "none");
}
})
