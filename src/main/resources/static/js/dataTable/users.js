/**
 * Created by derteuffel on 06/01/2019.
 */
$(document).ready( function () {

    var professeurs = $('#professeur').DataTable({
        "sAjaxSource": "/professeurs",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        language: {
            url: '//cdn.datatables.net/plug-ins/1.10.19/i18n/French.json'
        },
        "aoColumns": [
            { "mData": "name"},
            { "mData": "email" },
            { "mData": "country" },
            { "mData": "region" },
            { "mData": "number" },
            { "mData": "category" },
            { "mData": "userId",
                mRender: function (mData,type,row){
                    var str3='';
                    str3 += '<a href="/user/view/'+mData+'"><i class="fa fa-eye"></i></a>';
                    return str3;
                }}

        ]
    })
    var groupeUsers = $('#users').DataTable({
        "sAjaxSource": "/user/groupe",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        language: {
            url: '//cdn.datatables.net/plug-ins/1.10.19/i18n/French.json'
        },
        "aoColumns": [
            { "mData": "name"},
            { "mData": "email" },
            { "mData": "country" },
            { "mData": "region" },
            { "mData": "number" },
            { "mData": "userId",
                mRender: function (mData,type,row){
                    var str3='';
                    str3 += '<a href="/user/staffs/'+mData+'">Ouvrir<i class="fa fa-eye"></i></a>';
                    return str3;
                }}

        ]
    })

});
