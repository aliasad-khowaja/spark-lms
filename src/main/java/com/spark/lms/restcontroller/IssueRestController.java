package com.spark.lms.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.lms.common.Constants;
import com.spark.lms.model.Book;
import com.spark.lms.model.Issue;
import com.spark.lms.model.IssuedBook;
import com.spark.lms.model.Member;
import com.spark.lms.service.BookService;
import com.spark.lms.service.IssueService;
import com.spark.lms.service.IssuedBookService;
import com.spark.lms.service.MemberService;

@RestController
@RequestMapping(value = "/rest/issue")
public class IssueRestController {

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private IssueService issueService;
	
	@Autowired
	private IssuedBookService issuedBookService;
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(@RequestParam Map<String, String> payload) {
		
		String memberIdStr = (String)payload.get("member");
		String[] bookIdsStr = payload.get("books").toString().split(",");
		
		Long memberId = null;
		List<Long> bookIds = new ArrayList<Long>();
		try {
			memberId = Long.parseLong( memberIdStr );
			for(int k=0 ; k<bookIdsStr.length ; k++) {
				bookIds.add( Long.parseLong(bookIdsStr[k]) );
			}
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return "invalid number format";
		}
		
		Member member = memberService.get( memberId );
		List<Book> books = bookService.get(bookIds);
		
		Issue issue = new Issue();
		issue.setMember(member);
		issue = issueService.addNew(issue);
		
		for( int k=0 ; k<books.size() ; k++ ) {
			Book book = books.get(k);
			book.setStatus( Constants.BOOK_STATUS_ISSUED );
			book = bookService.save(book);
			
			IssuedBook ib = new IssuedBook();
			ib.setBook( book );
			ib.setIssue( issue );
			issuedBookService.addNew( ib );
			
		}
		
		return "success";
	}
	
	@RequestMapping(value = "/{id}/return/all", method = RequestMethod.GET)
	public String returnAll(@PathVariable(name = "id") Long id) {
		Issue issue = issueService.get(id);
		if( issue != null ) {
			List<IssuedBook> issuedBooks = issue.getIssuedBooks();
			for( int k=0 ; k<issuedBooks.size() ; k++ ) {
				IssuedBook ib = issuedBooks.get(k);
				ib.setReturned( Constants.BOOK_RETURNED );
				issuedBookService.save( ib );
				
				Book book = ib.getBook();
				book.setStatus( Constants.BOOK_STATUS_AVAILABLE );
				bookService.save(book);
			}
			
			issue.setReturned( Constants.BOOK_RETURNED );
			issueService.save(issue);
			
			return "successful";
		} else {
			return "unsuccessful";
		}
	}
	
	@RequestMapping(value="/{id}/return", method = RequestMethod.POST)
	public String returnSelected(@RequestParam Map<String, String> payload, @PathVariable(name = "id") Long id) {
		Issue issue = issueService.get(id);
		String[] issuedBookIds = payload.get("ids").split(",");
		if( issue != null ) {
			
			List<IssuedBook> issuedBooks = issue.getIssuedBooks();
			for( int k=0 ; k<issuedBooks.size() ; k++ ) {
				IssuedBook ib = issuedBooks.get(k);
				if( Arrays.asList(issuedBookIds).contains( ib.getId().toString() ) ) {
					ib.setReturned( Constants.BOOK_RETURNED );
					issuedBookService.save( ib );
					
					Book book = ib.getBook();
					book.setStatus( Constants.BOOK_STATUS_AVAILABLE );
					bookService.save(book);
				}
			}
			
			return "successful";
		} else {
			return "unsuccessful";
		}
	}
	
}
