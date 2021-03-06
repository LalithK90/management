package lk.recruitment_management.asset.common_asset.controller;

import lk.recruitment_management.asset.applicant.controller.ApplicantController;
import lk.recruitment_management.asset.applicant.entity.Applicant;
import lk.recruitment_management.asset.applicant.service.ApplicantService;
import lk.recruitment_management.asset.user_management.entity.PasswordChange;
import lk.recruitment_management.asset.user_management.entity.User;
import lk.recruitment_management.asset.user_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ProfileController {
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final ApplicantService applicantService;
  private final ApplicantController applicantController;

  @Autowired
  public ProfileController(UserService userService, PasswordEncoder passwordEncoder,
                           ApplicantService applicantService, ApplicantController applicantController) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.applicantService = applicantService;
    this.applicantController = applicantController;
  }

  @GetMapping( value = "/profile" )
  public String userProfile(Model model, Principal principal) {
    User authUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    Applicant applicant = applicantService.findByEmail(authUser.getUsername());
    if ( applicant != null ) {
      return "redirect:/applicant/" + applicant.getId();
    }
    model.addAttribute("addStatus", true);
    model.addAttribute("employeeDetail", userService.findByUserName(principal.getName()).getEmployee());
    return "employee/employee-detail";
  }
  @GetMapping( value = "/profile/edit" )
  public String userProfileEdit(Model model, Principal principal) {
    User authUser = userService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName());
    Applicant applicant = applicantService.findByEmail(authUser.getUsername());
    System.out.println("profile came here");
    return applicantController.applicantEdit(model,applicant);
  }


  @GetMapping( value = "/passwordChange" )
  public String passwordChangeForm(Model model) {
    model.addAttribute("pswChange", new PasswordChange());
    return "login/passwordChange";
  }

  @PostMapping( value = "/passwordChange" )
  public String passwordChange(@Valid @ModelAttribute PasswordChange passwordChange,
                               BindingResult result, RedirectAttributes redirectAttributes) {
    User user =
        userService.findById(userService.findByUserIdByUserName(SecurityContextHolder.getContext().getAuthentication().getName()));

    if ( passwordEncoder.matches(passwordChange.getOldPassword(), user.getPassword()) && !result.hasErrors() && passwordChange.getNewPassword().equals(passwordChange.getNewPasswordConform()) ) {

      user.setPassword(passwordChange.getNewPassword());
      userService.persist(user);

      redirectAttributes.addFlashAttribute("message", "Congratulations .!! Success password is changed");
      redirectAttributes.addFlashAttribute("alertClass", "alert-success");
      return "redirect:/home";

    }
    redirectAttributes.addFlashAttribute("message", "Failed");
    redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
    return "redirect:/passwordChange";

  }
}
