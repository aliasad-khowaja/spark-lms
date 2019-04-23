package com.spark.lms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.lms.common.Constants;

@Service
public class HomeService {

	@Autowired
	private MemberService memberService;
	
	public Map<String, Long> getTopTilesMap() {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("totalMembers", memberService.getTotalCount());
		map.put("totalStudents", memberService.getStudentsCount());
		map.put("totalParents", memberService.getParentsCount());
		return map;
	}
	
}
