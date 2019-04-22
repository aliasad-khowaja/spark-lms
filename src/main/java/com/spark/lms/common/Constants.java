package com.spark.lms.common;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	public static final String ROLE_ADMIN = "Admin";
	public static final String ROLE_LIBRARIAN = "Librarian";
	
	public static final List<String> MEMBER_TYPES = new ArrayList<String>() {{
	    add("Parent");
	    add("Student");
	    add("Other");
	}};
	
}
