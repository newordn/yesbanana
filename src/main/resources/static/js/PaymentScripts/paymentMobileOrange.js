
var selectedOperator="undefined";
$("#operatorSelection").change(function(){
    selectedOperator = $(this).children("option:selected").val();
});
function pay()
{
    var data = {
        amount : $("#amount").val(),
        phone_number: $("#phone_number").val(),
        operator: selectedOperator,
        user : $("#user_name").val(),
        email : $("#user_email").val(),
        notify_url : "http://localhost:8008/payment/transactions",
        return_url : "http://localhost:8008/payment/transactions",

    };
//operator for orange: CM_ORANGEMONEY
//operator for mtn: CM_MTNMOBILEMONEY
    var options = {
        url: "https://api.monetbil.com/widget/v2.1/Up5A27uT0sJ3C8emyKGVsM0hE2nD7y2F",
        dataType: "json",
        type: "POST",
        crossDomain: true,
        contentType: "application/json; charset=utf-8",
        data:  JSON.stringify( data ) , // Our valid JSON string
        success: function( data, status, xhr ) {
            console.log(data);
            $("#mobileForm").attr('action',data.payment_url);
            $("#mobileForm").submit();
            //$("#transaction_success span").trigger("click");
        },
        error: function( xhr, status, error ) {
            console.log(error);

          //  $("#transaction_error span").trigger("click");
        }
    } ;
    if(selectedOperator=="undefined")
    {
        alert("Vous devez choisir un opérateur");
        return 0;
    }
    $.ajax( options );
}


