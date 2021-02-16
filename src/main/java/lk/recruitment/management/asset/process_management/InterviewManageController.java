package lk.recruitment.management.asset.process_management;

import lk.recruitment.management.asset.applicant.entity.Applicant;
import lk.recruitment.management.asset.applicant.entity.Enum.ApplicantStatus;
import lk.recruitment.management.asset.applicant.service.ApplicantService;
import lk.recruitment.management.asset.applicant_sis_crd_cid_result.entity.ApplicantSisCrdCid;
import lk.recruitment.management.asset.applicant_sis_crd_cid_result.entity.enums.InternalDivision;
import lk.recruitment.management.util.service.FileHandelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping( "/interviewManage" )
public class InterviewManageController {
  private final ApplicantService applicantService;
  private final FileHandelService fileHandelService;
  private final ServletContext context;

  public InterviewManageController(ApplicantService applicantService, FileHandelService fileHandelService,
                                   ServletContext context) {
    this.applicantService = applicantService;
    this.fileHandelService = fileHandelService;
    this.context = context;
  }

  private String commonThing(Model model, List< Applicant > applicants, String title, String uriPdf,
                             String btnTextPdf, String uriExcel, String btnTextExcel) {
    model.addAttribute("applicants", applicants);
    System.out.println(applicants.size()+"   hehhe");
    model.addAttribute("headerTitle", title);
    model.addAttribute("uriPdf", uriPdf);
    model.addAttribute("btnTextPdf", btnTextPdf);
    model.addAttribute("uriExcel", uriExcel);
    model.addAttribute("btnTextExcel", btnTextExcel);
    return "interviewSchedule/interview";
  }

  /*
   @GetMapping( value = "/pdf" )
    public void allPdf(HttpServletRequest request, HttpServletResponse response) {
      List< Applicant > employees = applicantService.getAllEmployeePdfAndExcel();
      boolean isFlag = applicantService.createPdf(employees, context, request, response);

      if ( isFlag ) {
        String fullPath = request.getServletContext().getRealPath("/resources/report/" + "employees" + ".pdf");
        fileHandelService.filedownload(fullPath, response, "employees.pdf");
      }
    }
  */
  @GetMapping( value = "/{interviewType}" )
  public void allExcel(@PathVariable( "interviewType" ) String interviewType, HttpServletRequest request,
                       HttpServletResponse response) {
    List< Applicant > applicants;
    String sheetName;
    switch ( interviewType ) {
      case "firstInterviewExcel":
        applicants = applicantService.findByApplicantStatus(ApplicantStatus.FST);
        sheetName = "First Interview Applicant List";
        break;
      case "secondInterviewExcel":
        applicants = applicantService.findByApplicantStatus(ApplicantStatus.SND);
        sheetName = "Second Interview Applicant List";
        break;
      case "thirdInterviewExcel":
        applicants = applicantService.findByApplicantStatus(ApplicantStatus.TND);
        sheetName = "Third Interview Applicant List";
        break;
      case "fourthInterviewExcel":
        applicants = applicantService.findByApplicantStatus(ApplicantStatus.FTH);
        sheetName = "Fourth Interview Applicant List";
        break;
      case "SIS":
        applicants = applicantService.findByApplicantStatus(ApplicantStatus.FSTP);
        sheetName = "Report to SIS";
        break;
      case "CID":
        applicants = applicantService.findByApplicantStatus(ApplicantStatus.FSTP);
        sheetName = "Report to CID";
        break;
      case "CRD":
        applicants = applicantService.findByApplicantStatus(ApplicantStatus.FSTP);
        sheetName = "Report to CRD";
        break;
      default:
        applicants = null;
        sheetName = "No Applicant to show";
    }
    boolean isFlag = applicantService.createExcel(applicants, context, request, response, sheetName);
    if ( isFlag ) {
      String fullPath = request.getServletContext().getRealPath("/resources/report/" + sheetName + ".xls");
      fileHandelService.fileDownload(fullPath, response, sheetName+".xls");
    }
  }

//todo-> no need to manage pdf to 3rd and 4th

  @GetMapping( "/firstInterview" )
  public String firstInterview(Model model) {
    return commonThing(model, applicantService.findByApplicantStatus(ApplicantStatus.FST), "First Interview",
                       "firstInterviewPdf", "First Interview Pdf", "firstInterviewExcel", "First Interview Excel");
  }

  //todo -> first interview result enter

  @GetMapping( "/secondInterview" )
  public String secondInterview(Model model) {
    return commonThing(model, applicantService.findByApplicantStatus(ApplicantStatus.SND), "Second Interview",
                       "secondInterviewPdf", "Second Interview Pdf",
                       "secondInterviewExcel", "Second Interview Excel");
  }

  //todo-> second interview result enter

  @GetMapping( "/thirdInterview" )
  public String thirdInterview(Model model) {
    return commonThing(model, applicantService.findByApplicantStatus(ApplicantStatus.TND), "Third Interview",
                       null, null,
                       "thirdInterviewExcel", "Third Interview Excel");
  }

  //todo-> third interview result enter
  @GetMapping( "/fourthInterview" )
  public String fourthInterview(Model model) {
    return commonThing(model, applicantService.findByApplicantStatus(ApplicantStatus.FTH), "Fourth Interview",
                       null, null,
                       "fourthInterviewExcel", "Fourth Interview Excel");
  }

  //todo-> fourth interview result enter

  @GetMapping("/cidcrdsis")
  public String cidCRDSIS(Model model) {
    model.addAttribute("applicants", applicantService.findByApplicantStatus(ApplicantStatus.FTH));
//form action
    model.addAttribute("formAction", "cidcrdsis");
    //cid
    model.addAttribute("uriCID", "CID");
    model.addAttribute("btnTextCID", "Get CID Excel");
    model.addAttribute("internalDivisionCID", InternalDivision.CID);
    //sis
    model.addAttribute("uriSIS", "SIS");
    model.addAttribute("btnTextSIS","Get SIS Excel");
    model.addAttribute("internalDivisionSIS", InternalDivision.SIS);
    //crd
    model.addAttribute("uriCRD", "CRD");
    model.addAttribute("btnTextCRD", "Get CRD Excel");
    model.addAttribute("internalDivisionCRD", InternalDivision.CRD);
    return "interviewSchedule/interviewCIDSISCRD";
  }

  @PostMapping("/cidcrdsis")
  public String saveResult(@ModelAttribute ApplicantSisCrdCid applicantSisCrdCid){
    //todo 1. need to find applicant using nic

    // 2. result get and convert to upper case and validate
    // 3. before save need to check result already entered or not
    System.out.println(applicantSisCrdCid.getInternalDivision().toString());
    System.out.println(applicantSisCrdCid.getMultipartFile().getOriginalFilename());
    return "redirect:/interviewManage/cidcrdsis";
  }
}
