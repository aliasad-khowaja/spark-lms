$(document).ready(function() {
	
	var members = [];
	function initMembers() {
		$.get('/rest/member/list', function(data) {
			if( data ) {
				members = data;
				console.log( members );
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
		$.get('/rest/book/' + value + '/list', function(data) {
			if( data ) {
				populateBooksList( data );
			}
		});
	}
	
	function populateBooksList( booksList ) {
		$('#booksSel').empty().append('<option selected="selected" value="">-- Select Book --</option>');
		$.each(booksList, function(k, v) {   
		     $('#booksSel').append($("<option></option>")
		                    .attr("value",v.id).text(v.title)); 
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
	
	
	
});