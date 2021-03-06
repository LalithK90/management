package lk.recruitment_management.asset.applicant_gazette_interview.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.recruitment_management.asset.applicant_gazette.entity.ApplicantGazette;
import lk.recruitment_management.asset.applicant_gazette_interview.entity.enums.ApplicantGazetteInterviewStatus;
import lk.recruitment_management.asset.applicant_gazette_interview_result.entity.ApplicantGazetteInterviewResult;
import lk.recruitment_management.asset.applicant_gazette_sis_crd_cid_result.entity.enums.PassFailed;
import lk.recruitment_management.asset.interview_board.entity.InterviewBoard;
import lk.recruitment_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("ApplicantGazetteInterview")
public class ApplicantGazetteInterview extends AuditEntity {

  @Enumerated( EnumType.STRING)
  private ApplicantGazetteInterviewStatus applicantGazetteInterviewStatus;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate interviewDate;

  private String message;

  private String remark;

  @Enumerated(EnumType.STRING)
  private PassFailed passFailed;

  @ManyToOne
  private InterviewBoard interviewBoard;

  @ManyToOne
  private ApplicantGazette applicantGazette;

  @OneToMany(mappedBy = "applicantGazetteInterview")
  private List< ApplicantGazetteInterviewResult > applicantGazetteInterviewResults;

}
