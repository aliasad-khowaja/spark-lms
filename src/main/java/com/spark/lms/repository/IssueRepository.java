package com.spark.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spark.lms.model.Issue;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

}
