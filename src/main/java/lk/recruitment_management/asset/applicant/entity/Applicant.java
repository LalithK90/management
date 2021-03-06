package lk.recruitment_management.asset.applicant.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.recruitment_management.asset.applicant.entity.enums.Nationality;
import lk.recruitment_management.asset.applicant_degree_result.entity.ApplicantDegreeResult;
import lk.recruitment_management.asset.applicant_gazette.entity.ApplicantGazette;
import lk.recruitment_management.asset.applicant_non_relative.entity.ApplicantNonRelative;
import lk.recruitment_management.asset.applicant_result.entity.ApplicantResult;
import lk.recruitment_management.asset.common_asset.model.Enum.CivilStatus;
import lk.recruitment_management.asset.common_asset.model.Enum.Gender;
import lk.recruitment_management.asset.grama_niladhari.entity.GramaNiladhari;
import lk.recruitment_management.util.audit.AuditEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Applicant" )
public class Applicant extends AuditEntity {

  @Column( nullable = false, unique = true )
  private String code;

  @Column( nullable = false )
  private String nameInFullName;

  @Column( nullable = false )
  private String nameWithInitial;

  @Column( nullable = false )
  private String nic;

  @Column( nullable = false )
  private String height;

  @Column( nullable = false )
  private String weight;

  @Column( nullable = false )
  private String chest;

  @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
  private String mobile;

  private String land;

  @Column( unique = true )
  private String email;

  @Column( columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin NULL", length = 255 )
  private String address;

  @Enumerated( EnumType.STRING )
  private Gender gender;

  @Enumerated( EnumType.STRING )
  private CivilStatus civilStatus;

  @Enumerated( EnumType.STRING )
  private Nationality nationality;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate dateOfBirth;

  @ManyToOne
  private GramaNiladhari gramaNiladhari;

  @OneToMany( mappedBy = "applicant" )
  private List< ApplicantGazette > applicantGazettes;

  @OneToMany( mappedBy = "applicant", cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
  private List< ApplicantResult > applicantResults;

  @OneToMany( mappedBy = "applicant", cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
  private List< ApplicantDegreeResult > applicantDegreeResults;

  @OneToMany( mappedBy = "applicant", cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
  private List< ApplicantNonRelative > applicantNonRelatives;


  @Transient
  private MultipartFile file;

  @Transient
  private MultipartFile nicImage;

  @Transient
  private MultipartFile birthCertificateImage;

  @Transient
  private MultipartFile gramaNilahdariImage;

  @Transient
  private List< MultipartFile > educationalImages;

  @Transient
  private List< MultipartFile > sportImages;


  public Applicant(String email) {
    this.email = email;
  }
}
