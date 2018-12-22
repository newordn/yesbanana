/**
 * Created by derteuffel on 29/11/2018.
 */

let sel1=$("#sel1");
sel1.change(()=>{
    if(sel1.val() == "Education civique"){
    $("#niveau").val(parseInt("3"));
}
else if (sel1.val() == "Education primaire"){
    $("#niveau").val(parseInt("1"));
}
else if (sel1.val() == "Education secondaire"){
    $("#niveau").val(parseInt("2"));
}
else (sel1.val() == "Autres services"){
    $("#niveau").val(parseInt("4"));
}
});
