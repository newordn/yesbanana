/**
 * Created by derteuffel on 02/03/2019.
 */
$(document).ready( function () {
    $('#livres').DataTable({
        "searching": true, // false to disable search (or any other option)
        "pagingType": "full_numbers"
    });
    $('.dataTables_length').addClass('bs-select');

})