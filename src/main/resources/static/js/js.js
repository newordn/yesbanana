/**
 * Created by derteuffel on 29/11/2018.
 */

$( '#element .list-group li' ).on( 'click', function () {
    $( '#element .list-group' ).find( 'a.active' ).removeClass( 'active' );
    $( this ).parent( 'a' ).addClass( 'active' );
});

$( '#navigation ul a' ).on( 'click', function () {
    $( '#navigation ul a' ).find( 'li.active' ).removeClass( 'active' );
    $( this ).parent( 'li' ).addClass( 'active' );
});
