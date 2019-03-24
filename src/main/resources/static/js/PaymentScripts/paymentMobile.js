var data = {
    service: "Up5A27uT0sJ3C8emyKGVsM0hE2nD7y2F",
    amount : "500",
    phonenumber: "237694126158",
    operator: "CM_ORANGEMOBILEMONEY",
    item_ref: "",
    payment_ref: "",
    country:"",
    user : "",
    first_name : "",
    last_name : "",
    email : "",
    notify_url : ""

};
//operator for mtn: CM_MTNMOBILEMONEY
var options = {
    url: "https://api.monetbil.com/payment/v1/placePayment",
    dataType: "json",
    type: "POST",
    crossDomain: true,
    contentType: "application/json; charset=utf-8",
    data:  JSON.stringify( data ) , // Our valid JSON string
    success: function( data, status, xhr ) {
        console.log(data);
    },
    error: function( xhr, status, error ) {
        console.log(error);
    }
};
function pay()
{
    $.ajax( options );
}


