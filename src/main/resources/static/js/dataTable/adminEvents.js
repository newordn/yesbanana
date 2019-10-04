/**
 * Created by derteuffel on 06/01/2019.
 */
$(document).ready( function () {
    var table = $('#admins').DataTable({
        "sAjaxSource": "/event/events",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        language: {
            url: '//cdn.datatables.net/plug-ins/1.10.19/i18n/French.json'
        },
        "aoColumns": [
            { "mData": "type"},
            { "mData": "title" },
            { "mData": "releaseDate" },
            { "mData": "price" }

        ]
    })

});
