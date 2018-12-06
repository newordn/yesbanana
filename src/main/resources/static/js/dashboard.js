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
    let biblioVal = $("#bibliographyAuthor").val() + ":" + $("#bibliographyTitle").val() + ":" + $("#bibliographyContent").val() + ";";
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
// toggle acomp fields
$("#accompToggle").change(()=>{
    if($("#accompToggle").is(":checked"))
{
    $("#accompDiv").css("display","block");
}
else
{
    $("#accompDiv").css("display", "none");
}
});
/*!
 * Bootstrap 4 multi dropdown navbar ( https://bootstrapthemes.co/demo/resource/bootstrap-4-multi-dropdown-navbar/ )
 * Copyright 2017.
 * Licensed under the GPL license
 */


$( document ).ready( function () {
    $( '.dropdown-menu a.dropdown-toggle' ).on( 'click', function ( e ) {
        var $el = $( this );
        var $parent = $( this ).offsetParent( ".dropdown-menu" );
        if ( !$( this ).next().hasClass( 'show' ) ) {
            $( this ).parents( '.dropdown-menu' ).first().find( '.show' ).removeClass( "show" );
        }
        var $subMenu = $( this ).next( ".dropdown-menu" );
        $subMenu.toggleClass( 'show' );

        $( this ).parent( "li" ).toggleClass( 'show' );

        $( this ).parents( 'li.nav-item.dropdown.show' ).on( 'hidden.bs.dropdown', function ( e ) {
            $( '.dropdown-menu .show' ).removeClass( "show" );
        } );

        if ( !$parent.parent().hasClass( 'navbar-nav' ) ) {
            $el.next().css( { "top": $el[0].offsetTop, "left": $parent.outerWidth() - 4 } );
        }

        return false;
    } );
} );
