


$.each(document.getElementsByClassName("detailLink"),(i,v)=>{
    v.addEventListener("click",()=>{
    $.ajax({
    url: "http://localhost:8080/these/getOne/"+ v.getAttribute("href").substr(1)
}).done((data)=>{
    console.log(data);
$("#detailDate").text(data.theseDate)
$("#detailRegion").text(data.regions)
$("#detailUniversity").text(data.university)
$("#detailFaculty").text(data.faculty)
$("#detailCountry").text(data.country)
$("#detailOption").text(data.options)
$("#detailLevel").text(data.level)
$("#detailSubject").text(data.subject)
$("#detailAbstract").attr("src",data.resumes)
$("#detailStudent").text(data.student)
$("#detailChiefOfWork").text(data.workChief)
$("#detailProf").text(data.profesor)
$("#detailAssistant").text(data.assistant)
$("#detailUpdateRoute").attr("href","http://localhost:8080/these/update/"+ v.getAttribute("href").substr(1))
$("#detailBibliography").html("");
$("#detailLibrary").html("");
let bibliography = data.bibliography.split(":");
for(let i=0;i<bibliography.length;i+=3)
{
    if(bibliography[i+1]!=undefined)
        $("#detailBibliography").append('<li class="list-group-item" data-placement="top" data-toggle="tooltip" data-html="true" title="<table class=`table-bordered` style=`background-color:white;color:black`> <thead><th>Auteur</th><th>Titre</th><th>Description</th></thead><tbody><tr><td>' + bibliography[i] + '</td><td>' + bibliography[i + 1] + '</td><td>' + bibliography[i+2] +'</td></tr></tbody></table>">'+ bibliography[i]  + '</li>');


}
// enabling tooltips
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
})
let library = data.library.split(";");
for (let i = 0; i < library.length-1;i ++) {
    if(library[i]!=undefined)

        $("#detailLibrary").append("<li class='list-group-item'>" + library[i] + "</li>");
}
})
})
})

$(".addBL").click(()=>{
    $("#addThese").css("overflow","scroll");
})

