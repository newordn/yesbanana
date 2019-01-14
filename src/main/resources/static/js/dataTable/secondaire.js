/**
 * Created by derteuffel on 06/01/2019.
 */
$(document).ready( function () {
    var secondaries = $('#secondaire').DataTable({
        "sAjaxSource": "/secondaries",
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
                    var str6='';
                    str6 += '<a href="/user/detail/'+mData+'"><i class="fa fa-eye"></i></a>';
                    return str6;
                }}

        ]
    })

});
