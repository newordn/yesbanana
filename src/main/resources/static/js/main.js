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
// file picker
$("#addButton").click(() => {

    $("#customFile").click();
document.getElementById("file_image").addEventListener('change', handleImg, false);
function handleImg() {
    let img = this.files[0];
    console.log(img);
    $("#image").val(img.name);
}
});
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
    if(countrySelect.val()=="Afghanistan")
{

    $("#number").val("+93");
}
else if (countrySelect.val() == "Aland Islands")
{
    $("#number").val("+358");
}
else if (countrySelect.val() == "Albania")
{
    $("#number").val("+355");
}
else if (countrySelect.val() == "Algeria")
{
    $("#number").val("+213");
}
else if (countrySelect.val() == "AmericanSamoa")
{
    $("#number").val("+1 684");
}
else if (countrySelect.val() == "Andorra")
{
    $("#number").val("+376");
}
else if (countrySelect.val() == "Angola")
{
    $("#number").val("+244");
}
else if (countrySelect.val() == "Anguilla")
{
    $("#number").val("+1 264");
}
else if (countrySelect.val() == "Antarctica")
{
    $("#number").val("+672");
}
else if (countrySelect.val() == "Antigua and Barbuda")
{
    $("#number").val("+1268");
}
else if (countrySelect.val() == "Argentina")
{
    $("#number").val("+54");
}
else if (countrySelect.val() == "Armenia")
{
    $("#number").val("+374");
}
else if (countrySelect.val() == "Aruba")
{
    $("#number").val("+297");
}

else if (countrySelect.val() == "Australia")
{
    $("#number").val("+61");
}
else if (countrySelect.val() == "Austria")
{
    $("#number").val("+43");
}
else if (countrySelect.val() == "Azerbaijan")
{
    $("#number").val("+994");
}
else if (countrySelect.val() == "Bahamas")
{
    $("#number").val("+1 242");
}
else if (countrySelect.val() == "Bahrain")
{
    $("#number").val("+973");
}
else if (countrySelect.val() == "Bangladesh")
{
    $("#number").val("+880");
}
else if (countrySelect.val() == "Barbados")
{
    $("#number").val("+1 246");
}
else if (countrySelect.val() == "Belarus")
{
    $("#number").val("+375");
}
else if (countrySelect.val() == "Belgium")
{
    $("#number").val("+32");
}
else if (countrySelect.val() == "Belize")
{
    $("#number").val("+501");
}
else if (countrySelect.val() == "Benin")
{
    $("#number").val("+229");
}
else if (countrySelect.val() == "Bermuda")
{
    $("#number").val("+441");
}
else if (countrySelect.val() == "Bhutan")
{
    $("#number").val("+975");
}
else if (countrySelect.val() == "Bolivia")
{
    $("#number").val("+591");
}
else if (countrySelect.val() == "Bosnia and Herzegovina")
{
    $("#number").val("+387");
}
else if (countrySelect.val() == "Botswana")
{
    $("#number").val("+267");
}
else if (countrySelect.val() == "Brazil")
{
    $("#number").val("+55");
}
else if (countrySelect.val() == "British Indian Ocean Territory")
{
    $("#number").val("+246");
}
else if (countrySelect.val() == "Brunei Darussalam")
{
    $("#number").val("+673");
}
else if (countrySelect.val() == "Bulgaria")
{
    $("#number").val("+359");
}
else if (countrySelect.val() == "Burkina Faso")
{
    $("#number").val("+226");
}
else if (countrySelect.val() == "Burundi")
{
    $("#number").val("+257");
}
else if (countrySelect.val() == "Cambodia")
{
    $("#number").val("+855");
}
else if (countrySelect.val() == "Cameroon")
{
    $("#number").val("+237");
}
else if (countrySelect.val() == "Canada")
{
    $("#number").val("+1");
}
else if (countrySelect.val() == "Cape Verde")
{
    $("#number").val("+238");
}
else if (countrySelect.val() == "Cayman Islands")
{
    $("#number").val("+345");
}
else if (countrySelect.val() == "Central African Republic")
{
    $("#number").val("+236");
}
else if (countrySelect.val() == "Chad")
{
    $("#number").val("+235");
}
else if (countrySelect.val() == "Chile")
{
    $("#number").val("+56");
}
else if (countrySelect.val() == "China")
{
    $("#number").val("+86");
}
else if (countrySelect.val() == "Christmas Islands")
{
    $("#number").val("+61");
}
else if (countrySelect.val() == "Cocos (Keeling) Islands")
{
    $("#number").val("+61");
}
else if (countrySelect.val() == "Colombia")
{
    $("#number").val("+57");
}
else if (countrySelect.val() == "Comoros")
{
    $("#number").val("+269");
}
else if (countrySelect.val() == "Congo")
{
    $("#number").val("+242");
}
else if (countrySelect.val() == "Congo, The Democratic Republic of the Congo")
{
    $("#number").val("+243");
}
else if (countrySelect.val() == "Cook Islands")
{
    $("#number").val("+682");
}
else if (countrySelect.val() == "Costa Rica")
{
    $("#number").val("+506");
}
else if (countrySelect.val() == "Cote d'Ivoire")
{
    $("#number").val("+225");
}
else if (countrySelect.val() == "Croatia")
{
    $("#number").val("+385");
}
else if (countrySelect.val() == "Cuba")
{
    $("#number").val("+53");
}
else if (countrySelect.val() == "Cyprus")
{
    $("#number").val("+357");
}
else if (countrySelect.val() == "Czech Republic")
{
    $("#number").val("+420");
}
else if (countrySelect.val() == "Denmark")
{
    $("#number").val("+45");
}
else if (countrySelect.val() == "Djibouti")
{
    $("#number").val("+253");
}
else if (countrySelect.val() == "Dominica")
{
    $("#number").val("+1 767");
}
else if (countrySelect.val() == "Dominican Republic")
{
    $("#number").val("+1 849");
}
else if (countrySelect.val() == "Ecuador")
{
    $("#number").val("+593");
}
else if (countrySelect.val() == "Egypt")
{
    $("#number").val("+20");
}
else if (countrySelect.val() == "El Salvador")
{
    $("#number").val("+503");
}
else if (countrySelect.val() == "Equatorial Guinea")
{
    $("#number").val("+240");
}
else if (countrySelect.val() == "Eritrea")
{
    $("#number").val("+291");
}
else if (countrySelect.val() == "Estonia")
{
    $("#number").val("+372");
}
else if (countrySelect.val() == "Ethiopia")
{
    $("#number").val("+251");
}
else if (countrySelect.val() == "Falkland Islands")
{
    $("#number").val("+500");
}
else if (countrySelect.val() == "Faroe Islands")
{
    $("#number").val("+298");
}
else if (countrySelect.val() == "Fiji")
{
    $("#number").val("+679");
}
else if (countrySelect.val() == "Finland")
{
    $("#number").val("+358");
}
else if (countrySelect.val() == "France")
{
    $("#number").val("+33");
}
else if (countrySelect.val() == "French Guiana")
{
    $("#number").val("+594");
}
else if (countrySelect.val() == "French Polynesia")
{
    $("#number").val("+689");
}
else if (countrySelect.val() == "Gabon")
{
    $("#number").val("+241");
}
else if (countrySelect.val() == "Gambia")
{
    $("#number").val("+220");
}
else if (countrySelect.val() == "Georgia")
{
    $("#number").val("+995");
}
else if (countrySelect.val() == "Germany")
{
    $("#number").val("+49");
}
else if (countrySelect.val() == "Ghana")
{
    $("#number").val("+233");
}
else if (countrySelect.val() == "Gibraltar")
{
    $("#number").val("+350");
}
else if (countrySelect.val() == "Greece")
{
    $("#number").val("+30");
}
else if (countrySelect.val() == "Greenland")
{
    $("#number").val("+299");
}
else if (countrySelect.val() == "Grenada")
{
    $("#number").val("+1 473");
}
else if (countrySelect.val() == "Guadeloupe")
{
    $("#number").val("+590");
}
else if (countrySelect.val() == "Guam")
{
    $("#number").val("+1 671");
}
else if (countrySelect.val() == "Guatemala")
{
    $("#number").val("+502");
}
else if (countrySelect.val() == "Guernsey")
{
    $("#number").val("+44");
}
else if (countrySelect.val() == "Guinea")
{
    $("#number").val("+224");
}
else if (countrySelect.val() == "Guinea-Bissau")
{
    $("#number").val("+245");
}
else if (countrySelect.val() == "Guyana")
{
    $("#number").val("+595");
}
else if (countrySelect.val() == "Haiti")
{
    $("#number").val("+509");
}
else if (countrySelect.val() == "Holy See (Vatican City State)")
{
    $("#number").val("+379");
}
else if (countrySelect.val() == "Honduras")
{
    $("#number").val("+504");
}
else if (countrySelect.val() == "Hong Kong")
{
    $("#number").val("+852");
}
else if (countrySelect.val() == "Hungary")
{
    $("#number").val("+36");
}
else if (countrySelect.val() == "Iceland")
{
    $("#number").val("+354");
}
else if (countrySelect.val() == "India")
{
    $("#number").val("+91");
}
else if (countrySelect.val() == "Indonesia")
{
    $("#number").val("+62");
}
else if (countrySelect.val() == "Iran")
{
    $("#number").val("+98");
}
else if (countrySelect.val() == "Iraq")
{
    $("#number").val("+964");
}
else if (countrySelect.val() == "Ireland")
{
    $("#number").val("+353");
}
else if (countrySelect.val() == "Isle of Man")
{
    $("#number").val("+44");
}
else if (countrySelect.val() == "Israel")
{
    $("#number").val("+972");
}
else if (countrySelect.val() == "Italy")
{
    $("#number").val("+39");
}
else if (countrySelect.val() == "Jamaica")
{
    $("#number").val("+1 876");
}
else if (countrySelect.val() == "Japan")
{
    $("#number").val("+81");
}
else if (countrySelect.val() == "Jersey")
{
    $("#number").val("+44");
}
else if (countrySelect.val() == "Jordan")
{
    $("#number").val("+962");
}
else if (countrySelect.val() == "Kazakhstan")
{
    $("#number").val("+7 7");
}
else if (countrySelect.val() == "Kenya")
{
    $("#number").val("+254");
}
else if (countrySelect.val() == "Kiribati")
{
    $("#number").val("+686");
}
else if (countrySelect.val() == "Korea, Democratic People's Republic of Korea")
{
    $("#number").val("+850");
}
else if (countrySelect.val() == "Korea, Republic of South Korea")
    {
        $("#number").val("+82");
    }
else if (countrySelect.val() == "Kuwait")
    {
        $("#number").val("+965");
    }
else if (countrySelect.val() == "Kyrgyzstan")
    {
        $("#number").val("+996");
    }
else if (countrySelect.val() == "Laos")
    {
        $("#number").val("+856");
    }
else if (countrySelect.val() == "Latvia")
    {
        $("#number").val("+371");
    }
else if (countrySelect.val() == "Lebanon")
    {
        $("#number").val("+961");
    }
else if (countrySelect.val() == "Lesotho")
    {
        $("#number").val("+266");
    }
else if (countrySelect.val() == "Liberia")
    {
        $("#number").val("+231");
    }
else if (countrySelect.val() == "Libyan Arab Jamahiriya")
    {
        $("#number").val("+218");
    }
else if (countrySelect.val() == "Liechtenstein")
    {
        $("#number").val("+423");
    }
else if (countrySelect.val() == "Lithuania")
    {
        $("#number").val("+370");
    }
else if (countrySelect.val() == "Luxembourg")
    {
        $("#number").val("+352");
    }
else if (countrySelect.val() == "Macao")
    {
        $("#number").val("+853");
    }
else if (countrySelect.val() == "Macedonia")
    {
        $("#number").val("+389");
    }
else if (countrySelect.val() == "Madagascar")
    {
        $("#number").val("+261");
    }
else if (countrySelect.val() == "Malawi")
    {
        $("#number").val("+265");
    }
else if (countrySelect.val() == "Malaysia")
    {
        $("#number").val("+60");
    }
else if (countrySelect.val() == "Maldives")
    {
        $("#number").val("+960");
    }
else if (countrySelect.val() == "Mali")
    {
        $("#number").val("+223");
    }
else if (countrySelect.val() == "Malta")
    {
        $("#number").val("+356");
    }
else if (countrySelect.val() == "Marshall Islands")
    {
        $("#number").val("+692");
    }
else if (countrySelect.val() == "Martinique")
    {
        $("#number").val("+596");
    }
else if (countrySelect.val() == "Mauritania")
    {
        $("#number").val("+222");
    }
else if (countrySelect.val() == "Mauritius")
    {
        $("#number").val("+230");
    }
else if (countrySelect.val() == "Mayotte")
    {
        $("#number").val("+262");
    }
else if (countrySelect.val() == "Mexico")
    {
        $("#number").val("+52");
    }
else if (countrySelect.val() == "Micronesia")
    {
        $("#number").val("+691");
    }
else if (countrySelect.val() == "Moldova")
    {
        $("#number").val("+373");
    }
else if (countrySelect.val() == "Monaco")
    {
        $("#number").val("+377");
    }
else if (countrySelect.val() == "Mongolia")
    {
        $("#number").val("+976");
    }
else if (countrySelect.val() == "Montenegro")
    {
        $("#number").val("+382");
    }
else if (countrySelect.val() == "Montserrat")
    {
        $("#number").val("+1664");
    }
else if (countrySelect.val() == "Morocco")
    {
        $("#number").val("+212");
    }
else if (countrySelect.val() == "Mozambique")
    {
        $("#number").val("+258");
    }
else if (countrySelect.val() == "Myanmar")
    {
        $("#number").val("+95");
    }
else if (countrySelect.val() == "Namibia")
    {
        $("#number").val("+264");
    }
else if (countrySelect.val() == "Nauru")
    {
        $("#number").val("+674");
    }
else if (countrySelect.val() == "Nepal")
    {
        $("#number").val("+977");
    }
else if (countrySelect.val() == "Netherlands")
    {
        $("#number").val("+31");
    }
else if (countrySelect.val() == "Netherlands Antilles")
    {
        $("#number").val("+599");
    }
else if (countrySelect.val() == "New Caledonia")
    {
        $("#number").val("+687");
    }
else if (countrySelect.val() == "New Zealand")
    {
        $("#number").val("+64");
    }
else if (countrySelect.val() == "Nicaragua")
    {
        $("#number").val("+505");
    }
else if (countrySelect.val() == "Niger")
    {
        $("#number").val("+227");
    }
else if (countrySelect.val() == "Nigeria")
    {
        $("#number").val("+234");
    }
else if (countrySelect.val() == "Niue")
    {
        $("#number").val("+683");
    }
else if (countrySelect.val() == "Norfolk Island")
    {
        $("#number").val("+672");
    }
else if (countrySelect.val() == "Northern Mariana Islands")
    {
        $("#number").val("+1 670");
    }
else if (countrySelect.val() == "Norway")
    {
        $("#number").val("+47");
    }
else if (countrySelect.val() == "Oman")
    {
        $("#number").val("+968");
    }
else if (countrySelect.val() == "Pakistan")
    {
        $("#number").val("+92");
    }
else if (countrySelect.val() == "Palau")
    {
        $("#number").val("+680");
    }
else if (countrySelect.val() == "Palestinian")
    {
        $("#number").val("+970");
    }
else if (countrySelect.val() == "Panama")
    {
        $("#number").val("+507");
    }
else if (countrySelect.val() == "Papua New Guinea")
    {
        $("#number").val("+675");
    }
else if (countrySelect.val() == "Paraguay")
    {
        $("#number").val("+595");
    }
else if (countrySelect.val() == "Peru")
    {
        $("#number").val("+51");
    }
else if (countrySelect.val() == "Philippines")
    {
        $("#number").val("+63");
    }
else if (countrySelect.val() == "Pitcairn")
    {
        $("#number").val("+872");
    }
else if (countrySelect.val() == "Poland")
    {
        $("#number").val("+48");
    }
else if (countrySelect.val() == "Portugal")
    {
        $("#number").val("+351");
    }
else if (countrySelect.val() == "Puerto Rico")
    {
        $("#number").val("+1 939");
    }
else if (countrySelect.val() == "Qatar")
    {
        $("#number").val("+974");
    }
else if (countrySelect.val() == "Romania")
    {
        $("#number").val("+40");
    }
else if (countrySelect.val() == "Russia")
    {
        $("#number").val("+7");
    }
else if (countrySelect.val() == "Rwanda")
    {
        $("#number").val("+250");
    }
else if (countrySelect.val() == "Reunion")
    {
        $("#number").val("+262");
    }
else if (countrySelect.val() == "Saint Barthelemy")
    {
        $("#number").val("+590");
    }
else if (countrySelect.val() == "Saint Helena")
    {
        $("#number").val("+290");
    }
else if (countrySelect.val() == "Saint Kitts and Nevis")
    {
        $("#number").val("+1 869");
    }
else if (countrySelect.val() == "Saint Lucia")
    {
        $("#number").val("+1 758");
    }
else if (countrySelect.val() == "Saint Martin")
    {
        $("#number").val("+590");
    }
else if (countrySelect.val() == "Saint Pierre and Miquelon")
    {
        $("#number").val("+508");
    }
else if (countrySelect.val() == "Saint Vincent and the Grenadines")
    {
        $("#number").val("+1 784");
    }
else if (countrySelect.val() == "Samoa")
    {
        $("#number").val("+685");
    }
else if (countrySelect.val() == "San Marino")
    {
        $("#number").val("+378");
    }
else if (countrySelect.val() == "Sao Tome")
    {
        $("#number").val("+239");
    }
else if (countrySelect.val() == "Saudi Arabia")
    {
        $("#number").val("+966");
    }
else if (countrySelect.val() == "Senegal")
    {
        $("#number").val("+221");
    }
else if (countrySelect.val() == "Serbia")
    {
        $("#number").val("+381");
    }
else if (countrySelect.val() == "Seychelles")
    {
        $("#number").val("+248");
    }
else if (countrySelect.val() == "Sierra Leone")
    {
        $("#number").val("+232");
    }
else if (countrySelect.val() == "Singapore")
    {
        $("#number").val("+65");
    }
else if (countrySelect.val() == "Slovakia")
    {
        $("#number").val("+421");
    }
else if (countrySelect.val() == "Slovenia")
    {
        $("#number").val("+386");
    }
else if (countrySelect.val() == "Solomon Islands")
    {
        $("#number").val("+677");
    }
else if (countrySelect.val() == "Somalia")
    {
        $("#number").val("+252");
    }
else if (countrySelect.val() == "South Africa")
    {
        $("#number").val("+27");
    }
else if (countrySelect.val() == "South Georgia")
    {
        $("#number").val("+500");
    }
else if (countrySelect.val() == "Spain")
    {
        $("#number").val("+34");
    }
else if (countrySelect.val() == "Sudan")
    {
        $("#number").val("+249");
    }
else if (countrySelect.val() == "Suriname")
    {
        $("#number").val("+597");
    }
else if (countrySelect.val() == "Svalbard and Jan Mayen")
    {
        $("#number").val("+47");
    }
else if (countrySelect.val() == "Swaziland")
    {
        $("#number").val("+268");
    }
else if (countrySelect.val() == "Sweden")
    {
        $("#number").val("+46");
    }
else if (countrySelect.val() == "Switzerland")
    {
        $("#number").val("+41");
    }
else if (countrySelect.val() == "Syrian Arab Republic")
    {
        $("#number").val("+963");
    }
else if (countrySelect.val() == "Taiwan")
    {
        $("#number").val("+886");
    }
else if (countrySelect.val() == "Tajikistan")
    {
        $("#number").val("+992");
    }
else if (countrySelect.val() == "Tanzania")
    {
        $("#number").val("+255");
    }
else if (countrySelect.val() == "Thailand")
    {
        $("#number").val("+66");
    }
else if (countrySelect.val() == "Timor-Leste")
    {
        $("#number").val("+670");
    }
else if (countrySelect.val() == "Togo")
    {
        $("#number").val("+228");
    }
else if (countrySelect.val() == "Tokelau")
    {
        $("#number").val("+690");
    }
else if (countrySelect.val() == "Tonga")
    {
        $("#number").val("+676");
    }
else if (countrySelect.val() == "Trinidad and Tobago")
    {
        $("#number").val("+1 868");
    }
else if (countrySelect.val() == "Tunisia")
    {
        $("#number").val("+216");
    }
else if (countrySelect.val() == "Turkey")
    {
        $("#number").val("+90");
    }
else if (countrySelect.val() == "Turkmenistan")
    {
        $("#number").val("+993");
    }
else if (countrySelect.val() == "Turks and Caicos Islands")
    {
        $("#number").val("+1 649");
    }
else if (countrySelect.val() == "Tuvalu")
    {
        $("#number").val("+688");
    }
else if (countrySelect.val() == "Uganda")
    {
        $("#number").val("+256");
    }
else if (countrySelect.val() == "Ukraine")
    {
        $("#number").val("+380");
    }
else if (countrySelect.val() == "United Arab Emirates")
    {
        $("#number").val("+971");
    }
else if (countrySelect.val() == "United Kingdom")
    {
        $("#number").val("+44");
    }
else if (countrySelect.val() == "United States")
    {
        $("#number").val("+1");
    }
else if (countrySelect.val() == "Uruguay")
    {
        $("#number").val("+598");
    }
else if (countrySelect.val() == "Uzbekistan")
    {
        $("#number").val("+998");
    }
else if (countrySelect.val() == "Vanuatu")
    {
        $("#number").val("+678");
    }
else if (countrySelect.val() == "Venezuela")
    {
        $("#number").val("+58");
    }
else if (countrySelect.val() == "Vietnam")
    {
        $("#number").val("+84");
    }
else if (countrySelect.val() == "Virgin Islands, British")
    {
        $("#number").val("+1 284");
    }
else if (countrySelect.val() == "Virgin Islands, U.S")
    {
        $("#number").val("+1 340");
    }
else if (countrySelect.val() == "Wallis and Futuna")
    {
        $("#number").val("+681");
    }
else if (countrySelect.val() == "Yemen")
    {
        $("#number").val("+967");
    }
else if (countrySelect.val() == "Zambia")
    {
        $("#number").val("+260");
    }
else if (countrySelect.val() == "Zimbabwe")
    {
        $("#number").val("+263");
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

