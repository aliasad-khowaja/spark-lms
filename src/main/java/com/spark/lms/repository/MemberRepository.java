package com.spark.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spark.lms.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	public List<Member> findAllByOrderByFirstNameAscMiddleNameAscLastNameAsc();
	public Long countByType(String type);
}
