$(document).ready(function() {
	
	var members = [];
	function initMembers() {
		$.get('/rest/member/list', function(data) {
			if( data ) {
				members = data;
			}
		});
	}
	initMembers();
	
	function getMembersByType(memberType) {
		var filteredMembers = [];
		for(var k=0 ; k<members.length ; k++) {
			if( members[k].type==memberType ) {
				filteredMembers.push( members[k] );
			}
		}
		return filteredMembers;
	}
	
	function populateMembersList( membersList ) {
		$('#memberSel').empty().append('<option selected="selected" value="">-- Select Member --</option>');
		$.each(membersList, function(k, v) {   
		     $('#memberSel').append($("<option></option>")
		                    .attr("value",v.id).text(v.firstName + ' ' + v.middleName + (v.lastName?' '+v.lastName:'') )); 
		});
	}
	
	$('#memberTypeSel').on('change', function() {
		var value = $(this).val();
		if( value ) {
			var fiteredMembers = getMembersByType( value );
			populateMembersList( fiteredMembers );
		} else {
			populateMembersList( [] );
		}
	});
	
	
	function getBooksByCategory(value) {
		$.get('/rest/book/' + value + '/available', function(data) {
			if( data ) {
				populateBooksList( data );
			}
		});
	}
	
	function populateBooksList( booksList ) {
		$('#booksSel').empty().append('<option selected="selected" value="">-- Select Book --</option>');
		$.each(booksList, function(k, v) {   
		     $('#booksSel').append($("<option></option>")
		                    .attr("value",v.id).text(v.title)
		                    .attr("data-authors", v.authors)
		                    .attr("data-tag", v.tag)
		                    .attr("data-publisher", v.publisher));
		});
	}
	
	$('#categorySel').on('change', function(){
		var value = $(this).val();
		if( value ) {
			var books = getBooksByCategory( value );
		} else {
			populateBooksList( [] );
		}
	});
	
	
	$('#addBookBtn').on('click', function() {
		var id = $('#booksSel').val();
		var title = $("#booksSel option:selected").text();
		var tag = $("#booksSel option:selected").attr("data-tag");
		var authors = $("#booksSel option:selected").attr("data-authors");
		
		if( id && !bookAlreadyExist(id) && title && tag && authors ) {
			var book = { id: id, title: title, tag: tag, authors: authors };
			booksToIssue.push(book);
			$('#booksSel').val('');
			initBooksInTable();
		}
	});
	
	function bookAlreadyExist(id) {
		for(var k=0 ; k<booksToIssue.length ; k++) {
			if( booksToIssue[k].id == id ) {
				return true;
			}
		}
		return false;
	}
	
	$('#saveBtn').on('click', function(){
		var errors = validate();
		if( errors.length > 0 ) {
			$('.errors-modal').find('.modal-body').html( errors.join('<br />') );
			$('.errors-modal').modal('show');
		} else {
			var issue = { 
					member: $('#memberSel').val(),
					books: getIssuedBookIds().join()
			}
			$.post( "/rest/issue/save", issue).done(function (data){
				if( data=='success' ) {
					window.location = '/issue/new';
				}
			});
		}
	});
	
	function getIssuedBookIds() {
		var ids = [];
		for(var k=0 ; k<booksToIssue.length ; k++) {
			ids.push( booksToIssue[k].id );
		}
		return ids;
	}
	
	function validate() {
		var errors = []
		var member = $('#memberSel').val();
		if( !member ) {
			errors.push('- Select Member');
		}
		if( booksToIssue.length == 0 ) {
			errors.push('- Add Books to issue');
		}
		return errors;
	}
	
});

var booksToIssue = [];

function initBooksInTable() {
	
	var trs = '';
	for( var k=0 ; k<booksToIssue.length ; k++ ) {
		var rowNum = k+1;
		trs += '<tr>';
		trs += '<td>'+rowNum+'</td>';
		trs += '<td>'+booksToIssue[k].tag+'</td>';
		trs += '<td>'+booksToIssue[k].title+'</td>';
		trs += '<td>'+booksToIssue[k].authors+'</td>';
		trs += '<td><a href="javascript:void(0)"  onclick="removeFromTable('+rowNum+', '+booksToIssue[k].id+')"><i class="fa fa-remove"></i></a></td>';
		trs += '</tr>';
	}
	$("#issueBooksTable").find("tr:gt(0)").remove();
	$('#issueBooksTable').append( trs );
}

function removeFromTable(rowNum, id) {
	$('#issueBooksTable tr:eq('+(rowNum)+')').remove();
	removeFromBooksIssuedList(id);
	initBooksInTable();
}

function removeFromBooksIssuedList(id) {
	for( var k=0 ; k<booksToIssue.length ; k++ ) {
		if( booksToIssue[k].id == id ) {
			booksToIssue.splice(k, 1);
			break;
		}
	}
}