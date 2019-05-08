/**
 * Created by newordn on 02/03/2019.
 */
$(document).ready( function () {

    var options = $('#options').DataTable()

});

$(document).ready(function() {
    $('#example').DataTable();

    $('input.global_filter').on( 'keyup click', function () {
        filterGlobal();
    } );

    $('input.column_filter').on( 'keyup click', function () {
        filterColumn( $(this).parents('tr').attr('data-column') );
    } );
} );