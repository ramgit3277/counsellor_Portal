package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.dto.DashboardResponseDto;
import in.ashokit.entities.Counsellor;
import in.ashokit.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CousellorController {
		@Autowired
		private CounsellorService counsellorService;
		
		@GetMapping("/")
		public String index(Model model) {
			Counsellor counsellor = new Counsellor();
			model.addAttribute("counsellor", counsellor);
			return "index";
		}
		
		@PostMapping("/login")
		public String login(Counsellor counsellor, Model model, HttpServletRequest req) {
			Counsellor login = counsellorService.login(counsellor.getEmail(), counsellor.getPassword());
			if(login==null) {
				model.addAttribute("errmsg", "Login Failed, Please check your email and password");
				return "index";
			}
			else {
				HttpSession s = req.getSession(true);
				s.setAttribute("CID", login.getCounsellorId());
				return "redirect:/dashboard";
			}
		}
		@GetMapping("/dashboard")
		public String dadhboard(Model model, HttpServletRequest req) {
			HttpSession s = req.getSession(false);
			Integer counsellor_Id = (Integer)s.getAttribute("CID");
			DashboardResponseDto dashboard = counsellorService.getDashboard(counsellor_Id);
			model.addAttribute("dashboard", dashboard);
			return "dashboard";
			
		}
		@GetMapping("/register")
		public String register(Model model) {
			Counsellor counsellor = new Counsellor();
			model.addAttribute("counsellor",counsellor);
			return "register";
		}
		
		@PostMapping("/register")
		public String handleregistration(Counsellor counsellor, Model model) {
			boolean saveCounsellor = counsellorService.saveCounsellor(counsellor);
			if(saveCounsellor) {
				model.addAttribute("smsg", "Register Successfully");
			}
			else {
				model.addAttribute("emsg", "Registration Failed");
			}
			return "register";
		}
		@GetMapping("/logout")
		public String logout(HttpServletRequest req) {
			HttpSession session = req.getSession(false);
			session.invalidate();
			return "redirect:/";
		}

}
