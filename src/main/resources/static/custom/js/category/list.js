$(document).ready(function() {

	var categoryId, categoryName;

	$(".remove-category-lnk").click(function() {
		categoryId = $(this).attr('data-category-id');
		categoryName = $(this).attr('data-category-name');
		$('.remove-category-modal').find('#category-name').text(categoryName);
	});

	$("#remove-category-btn").click(function() {
		$('.remove-category-modal').modal('hide');
		window.location = "/category/remove/" + categoryId;
	});

});