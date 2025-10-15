package in.ashokit.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.dto.EnqFilterRequestDto;
import in.ashokit.dto.EnquiryDto;
import in.ashokit.entities.Course;
import in.ashokit.entities.Enquiry;
import in.ashokit.service.CourseService;
import in.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {
	@Autowired
	private EnquiryService enquiryService;
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/enquiry")
	public String enquiry(Model model) {
		EnquiryDto enquiryDto = new EnquiryDto();
		model.addAttribute("enquiryDto", enquiryDto);
		model.addAttribute("courses", courseService.getCourses());
		return "add-enq";
	}
	
	@GetMapping("/editEnq")
	public String editEnquiry(@RequestParam("enquiryId") Integer enquiryId, Model model) {
		Enquiry enquiry = enquiryService.getEnquiry(enquiryId);
		
		EnquiryDto dto = new EnquiryDto();
		BeanUtils.copyProperties(enquiry, dto);
		dto.setCourseId(enquiry.getCourse().getCourseId());
		
		model.addAttribute("enquiryDto", dto);
		model.addAttribute("courses", courseService.getCourses());
		return "add-enq";
	}
	
	@PostMapping("/enquiry")
	public String addEnquiry(@ModelAttribute("enquiryDto")EnquiryDto enquiryDto, Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("CID");
		model.addAttribute("courses", courseService.getCourses());
		boolean enquiry = enquiryService.addEnquiry(enquiryDto, counsellorId);
		if(enquiry) {
			model.addAttribute("smsg", "Enquiry Added Successfully");
		}
		else {
			model.addAttribute("emsg", "Failed to add Enquiry");
		}
		return "add-enq";
	}
	@GetMapping("/view-enquiries")
	public String viewEnquiries(Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("CID");
		model.addAttribute("filter", new EnqFilterRequestDto());
		model.addAttribute("courses", courseService.getCourses());
		List<Enquiry> all = enquiryService.getAll(counsellorId);
		model.addAttribute("enqs", all);
		return "view-enq";
	}
	@PostMapping("/filter-enqs")
	public String filterEnquiries(@ModelAttribute("filter") EnqFilterRequestDto filter, Model model, HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		Integer counsellorId = (Integer)session.getAttribute("CID");
		List<Enquiry> filterEnquiry = enquiryService.filterEnquiry(filter, counsellorId);
		model.addAttribute("enqs", filterEnquiry);
		model.addAttribute("courses", courseService.getCourses());
		return "view-enq";
	}

}
