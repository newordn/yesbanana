var body={
    "acquirerCountryCode": "", // numeric country code of the acquirer(the sender's bank)
    "acquiringBin": "", // bank identification number the first six digits on the card of the sender
    "amount": "",  // the amount which will be deliver to the recipeint
    "businessApplicationId": "AA", // account to account
    "cardAcceptor": {
        "address": {
            "country": "", // sender's country(country ISO code)
            "county": "", // is like state required if country is USA
            "state": "", // state required if country is USA
            "zipCode": "" // zipcode required if country is USA
        },
        "idCode": "CA-IDCode-77765",
        "name": "Visa Inc. USA-Foster City",
        "terminalId": "TID-9999"
    },
    "localTransactionDateTime": "2019-03-20T13:35:25", // local date time
    "merchantCategoryCode": "",
    "pointOfServiceData": {
        "motoECIIndicator": "0",
        "panEntryMode": "90",
        "posConditionCode": "00"
    },
    "recipientName": "",
    "recipientPrimaryAccountNumber": "",
    "retrievalReferenceNumber": "412770451018",
    "senderAccountNumber": "",
    "senderAddress": "",
    "senderCity": "",
    "senderCountryCode": "",
    "senderName": "",
    "senderReference": "",
    "senderStateCode": "",
    "sourceOfFundsCode": "05", // 04 visa credit, 05 visa debit, 06 visa prepaid
    "systemsTraceAuditNumber": "451018",
    "transactionCurrencyCode": "USD", // the currency
    "transactionIdentifier": "381228649430015" // the same value as previous or not define
}
var options = {
    url: "",
    dataType: "json",
    type: "POST",
    crossDomain: true,
    contentType: "application/json;application/octet-stream; charset=utf-8",
    Authorization: "base64 encoded userid: password",
    data:  JSON.stringify(body) , // Our valid JSON string
    success: function( data, status, xhr ) {
        console.log(data);
    },
    error: function( xhr, status, error ) {
        console.log(error);
    }
};
function payVisa()
{
    $.ajax( options );
}
