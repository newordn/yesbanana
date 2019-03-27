
function pay()
{
    var data = {
        service: "Up5A27uT0sJ3C8emyKGVsM0hE2nD7y2F",
        amount : "500",
        phonenumber: $("#phone_number").val(),
        operator: "CM_ORANGEMONEY",
        user : $("#user_name").val(),
        email : $("#user_email").val(),
        notify_url : "http://localhost:8080/payment/success"

    };
//operator for orange: CM_ORANGEMONEY
    var options = {
        url: "https://api.monetbil.com/payment/v1/placePayment",
        dataType: "json",
        type: "POST",
        crossDomain: true,
        contentType: "application/json; charset=utf-8",
        data:  JSON.stringify( data ) , // Our valid JSON string
        success: function( data, status, xhr ) {
            console.log(data);
            $("#transaction_success span").trigger("click");
        },
        error: function( xhr, status, error ) {
            console.log(error);

            $("#transaction_error span").trigger("click");
        }
    };
    $.ajax( options );
}


