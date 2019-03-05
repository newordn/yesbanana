/**
 * Created by derteuffel on 06/01/2019.
 */
$(document).ready( function () {

    var theses = $('#theses').DataTable({
        "sAjaxSource": "/theses",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        language: {
            url: '//cdn.datatables.net/plug-ins/1.10.19/i18n/French.json'
        },
        "aoColumns": [
            { "mData": "country"},
            { "mData": "regions" },
            { "mData": "university" },
            { "mData": "faculty" },
            { "mData": "options" },
            { "mData": "status" },
            { "mData": "subject" },
            { "mData": "theseId",
                mRender: function (mData,type,row){
                    var str3='';
                    str3 += '<a href="/these/these/'+mData+'" class="btn">Ouvrir&nbsp;&nbsp;<i class="fa fa-eye"></i></a>';
                    str3 += '<a href="/these/general/edit/'+mData+'" class="btn">Editer&nbsp;&nbsp;<i class="fa fa-pencil"></i></a>';
                    return str3;
                }}

        ]
    })
    var these = $('#these').DataTable({
        "sAjaxSource": "/theses/user",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        language: {
            url: '//cdn.datatables.net/plug-ins/1.10.19/i18n/French.json'
        },
        "aoColumns": [
            { "mData": "country"},
            { "mData": "regions" },
            { "mData": "university" },
            { "mData": "faculty" },
            { "mData": "options" },
            { "mData": "subject" },
            { "mData": "theseId",
                mRender: function (mData,type,row){
                    var str='';
                    str += '<a href="/groupe/groupe/these/'+mData+'" class="btn">Ouvrir&nbsp;&nbsp;<i class="fa fa-eye"></i></a>';
                    str += '<a href="/groupe/groupe/these/general/edit/'+mData+'" class="btn">Editer&nbsp;&nbsp;<i class="fa fa-pencil"></i></a>';
                    return str;
                }}

        ]
    })

});
