var bookId, bookName;

function removeBookDialog(el) {
	bookId = $(el).attr('data-book-id');
	bookName = $(el).attr('data-book-name');
	$('.remove-book-modal').find('#book-name').text(bookName);
}

function removeBook() {
	$('.remove-book-modal').modal('hide');
	window.location = "/book/remove/" + bookId;
}