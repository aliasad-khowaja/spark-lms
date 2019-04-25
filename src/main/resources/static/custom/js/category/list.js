var categoryId, categoryName;

function removeCategoryDialog(el) {
	categoryId = $(el).attr('data-category-id');
	categoryName = $(el).attr('data-category-name');
	$('.remove-category-modal').find('#category-name').text(categoryName);
}

function removeCategory() {
	$('.remove-category-modal').modal('hide');
	window.location = "/category/remove/" + categoryId;
}