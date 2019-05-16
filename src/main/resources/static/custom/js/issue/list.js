var issuedBooks = [];
var selectedIssueId = undefined;

function returnBook(issueId) {
	selectedIssueId = issueId;
	issuedBooks = [];
	jQuery('#issue_'+issueId).find('li').each(function (i){
		var book = {id: $(this).attr('data-id'), title: $(this).text()}
		issuedBooks.push( book );
	});
	
	populateReturnBookTable();
	jQuery('#select-all').prop('checked', false);
	jQuery('.returnBookChk').prop('checked', false);
	$('.return-book-modal').modal('show');
	
}

function populateReturnBookTable() {
	var trs = '';
	for(var k=0 ; k<issuedBooks.length ; k++) {
		trs += '<tr>';
		trs += '<td><input type="checkbox" value="'+issuedBooks[k].id+'" class="returnBookChk" onclick="returnBookChkClicked()" /></td>';
		trs += '<td>'+issuedBooks[k].title+'</td>';
		trs += '</tr>';
	}
	jQuery('#returnBookTable tr:gt(0)').remove();
	jQuery('#returnBookTable').append(trs);
}

function returnBookChkClicked() {
	var total = jQuery('.returnBookChk').length;
	var checked = jQuery('.returnBookChk:checkbox:checked').length;
	if( total == checked ) {
		jQuery('#select-all').prop('checked', true);
	} else {
		jQuery('#select-all').prop('checked', false);
	}
}

function sellectAll() {
	if( jQuery('#select-all').prop('checked') ) {
		jQuery('.returnBookChk').prop('checked', true);
	} else {
		jQuery('.returnBookChk').prop('checked', false);
	}
}

function returnBookConfirm() {
	var checked = jQuery('.returnBookChk:checkbox:checked').length;
	if( checked > 0 ) {
		var total = jQuery('.returnBookChk').length;
		if( total == checked ) {
			$.get('/rest/issue/'+ selectedIssueId + '/return/all', function(msg) {
				if( msg == 'successful' ) {
					window.location = '/issue/list';
				}
			});
		} else {
			var ids = [];
			jQuery('.returnBookChk:checkbox:checked').each(function (i){
				ids.push( $(this).val() );
			});
			$.post( '/rest/issue/'+selectedIssueId+'/return' , {ids: ids.join(',')} ).done(function (msg){
				if( msg=='successful' ) {
					window.location = '/issue/list';
				}
			});
		}
	}
}
