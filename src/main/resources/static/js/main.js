import {name,email,password} from "./formVerificationHelper.js"; // export form verification helpers
// constraints
let nameErrorMsg = "Le nom doit contenir au moins 2 caractères.";
let emailErrorMsg = "Doit être une adresse email valide";
let regionErrorMsg = "Doit contenir au moins 2 caractères";
let universityErrorMsg = "Doit contenir au moins 2 caractères";
let facultyErrorMsg = "Doit contenir au moins 2 caractères";
let passwordErrorMsg = "Doit contenir au moins 6 caractères";
let repassErrorMsg = "Doit correspondre au mot de passe entré";
// constraints

// form validation
//name
let nameInput = $("#name");
let nameError = $("#nameError").text(" ");
nameInput.blur(()=>{
    if(!name(nameInput.val()))
    nameError.text(nameErrorMsg);
else
nameError.text("");
});
//email
let emailInput = $("#email");
let emailError = $("#emailError").text(" ");
emailInput.blur(() => {
    if (!email(emailInput.val()))
    emailError.text(emailErrorMsg);
else
emailError.text("");
});
// region
let regionInput = $("#region");
let regionError = $("#regionError").text(" ");
regionInput.blur(() => {
    if (!name(regionInput.val()))
    regionError.text(regionErrorMsg);
else
regionError.text("");
});
//university
let universityInput = $("#university");
let universityError = $("#universityError").text(" ");
universityInput.blur(() => {
    if (!name(universityInput.val()))
    universityError.text(universityErrorMsg);
else
universityError.text("");
});
//faculty
let facultyInput = $("#faculty");
let facultyError = $("#facultyError").text(" ");
facultyInput.blur(() => {
    if (!name(facultyInput.val()))
    facultyError.text(facultyErrorMsg);
else
facultyError.text("");
});
// password
let passwordInput = $("#password");
let passwordError = $("#passwordError").text(" ");
passwordInput.blur(() => {
    if (!password(passwordInput.val()))
    passwordError.text(passwordErrorMsg);
else
passwordError.text("");
});
// password confirmation
let repassInput = $("#repass");
let repassError = $("#repassError").text(" ");
repassInput.blur(() => {
    console.log(repassInput.val());
console.log(passwordInput.val())
if(repassInput.val() != passwordInput.val())
    repassError.text(repassErrorMsg);
else
    $("#repassError").text("");

});

// request to the server to save an user
let form = document.getElementById("register-form");
let form1 = document.getElementById("login-form");

// form registration and updating
$("#submit").click(
    ()=>
{

    if (email(emailInput.val()) && password(passwordInput.val()) && name(nameInput.val()) && name(regionInput.val()) && name(universityInput.val()) && name(facultyInput.val()))
    form.submit();
}
)
// form login
$("#submit-login").click(
    () => {
    if (email(emailInput.val())&& password(passwordInput.val()))
    form1.submit();
}
)


// country automatical changing indicatif
let countrySelect = $("#countrySelect");
countrySelect.change(()=>{
    if(countrySelect.val()=="Cameroun")
{

    $("#number").val("+237");
}
else if (countrySelect.val() == "Côte d'ivoire")
{
    $("#number").val("+221");
}
});
// file picker for img
$("#addButton").click(()=>{

    $("#file_image").click();
document.getElementById("file_image").addEventListener('change',handleImg,false);
function handleImg()
{
    let img = this.files[0];
    console.log(img);
    $("#image").val(img.name);
}
});
// file picker for cv
$("#addButtonCv").click(()=>{

    $("#file_image_cv").click();
document.getElementById("file_image_cv").addEventListener('change',handleImg,false);
function handleImg()
{
    let img = this.files[0];
    console.log(img);
    $("#cv").val(img.name);
}
});

