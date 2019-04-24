$(document).ready(function() {

	var memberId, memberName;

	$(".remove-member-lnk").click(function() {
		memberId = $(this).attr('data-member-id');
		memberName = $(this).attr('data-member-name');
		$('.remove-member-modal').find('#member-name').text(memberName);
	});

	$("#remove-member-btn").click(function() {
		$('.remove-member-modal').modal('hide');
		window.location = "/member/remove/" + memberId;
	});

});