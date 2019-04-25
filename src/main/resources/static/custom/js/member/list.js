var memberId, memberName;

function removeMemberDialog(el) {
	memberId = $(el).attr('data-member-id');
	memberName = $(el).attr('data-member-name');
	$('.remove-member-modal').find('#member-name').text(memberName);
}

function removeMember() {
	$('.remove-member-modal').modal('hide');
	window.location = "/member/remove/" + memberId;
}