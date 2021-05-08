package lk.recruitment_management.asset.applicant_interview.dao;


import lk.recruitment_management.asset.applicant.entity.Applicant;
import lk.recruitment_management.asset.applicant_interview.entity.ApplicantInterview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantInterviewDao extends JpaRepository< ApplicantInterview, Integer > {
  List< ApplicantInterview > findByApplicant(Applicant applicant);

}