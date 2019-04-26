$(document).ready(function() {
	
	var members = [];
	
	function initEntities() {
		members = getAllMembers();
	}
	initEntities();
	
	function getAllMembers() {
		var members = [];
		$.get('/v1/member/list', function(data) {
			if( data ) {
				members = data;
			}
		});
		return members;
	}
	
	function getAllCategories() {
		
	}
	
	function getAllBooks() {
		
	}
	
});