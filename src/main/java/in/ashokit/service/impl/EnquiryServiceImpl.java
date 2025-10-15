package in.ashokit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.dto.EnqFilterRequestDto;
import in.ashokit.dto.EnquiryDto;
import in.ashokit.entities.Counsellor;
import in.ashokit.entities.Course;
import in.ashokit.entities.Enquiry;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.EnquiryRepo;
import in.ashokit.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private EnquiryRepo enquiryRepo;
	@Autowired
	private CounsellorRepo counsellorRepo;
	@Autowired
	private CourseRepo courseRepo;
	@Override
	public boolean addEnquiry(EnquiryDto enquiry, Integer counsellor_Id) {
		Counsellor counsellor = counsellorRepo.findById(counsellor_Id).orElseThrow();
		Course course = courseRepo.findById(enquiry.getCourseId()).orElseThrow();
		Enquiry enq = new Enquiry();
		BeanUtils.copyProperties(enquiry, enq);
		enq.setCounsellor(counsellor);
		enq.setCourse(course);
		return enquiryRepo.save(enq).getEnquiryId()!=null;
	}

	@Override
	public boolean updateEnquiry(Enquiry e) {
		 enquiryRepo.save(e);
		 return true;
	}

	@Override
	public List<Enquiry> getAll(Integer counsellorId) {
		return enquiryRepo.findByCounsellorCounsellorId(counsellorId);
	}

	@Override
	public Enquiry getEnquiry(Integer enquiry_Id) {
		return enquiryRepo.findById(enquiry_Id).orElseThrow();
	}

	@Override
	public List<Enquiry> filterEnquiry(EnqFilterRequestDto enquiryFilter, Integer counsellor_Id) {
		Enquiry enq = new Enquiry();
		Counsellor c = counsellorRepo.findById(counsellor_Id).orElseThrow();
		enq.setCounsellor(c);
		if(enquiryFilter.getClassMode()!=null && !"".equals(enquiryFilter.getClassMode())) {
			enq.setClassMode(enquiryFilter.getClassMode());
		}
		if(enquiryFilter.getCourseId()!=null && enquiryFilter.getCourseId()>0) {
			Course course = courseRepo.findById(enquiryFilter.getCourseId()).orElseThrow();
			enq.setCourse(course);
		}
		if(enquiryFilter.getStatus()!=null && !"".equals(enquiryFilter.getStatus())) {
			enq.setStatus(enquiryFilter.getStatus());
		}
		List<Enquiry> res = enquiryRepo.findAll(Example.of(enq));
		return res;
	}

	
}
