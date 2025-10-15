package in.ashokit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.ashokit.dto.EnqFilterRequestDto;
import in.ashokit.dto.EnquiryDto;
import in.ashokit.entities.Enquiry;

public interface EnquiryService {
	public boolean addEnquiry(EnquiryDto enquiry, Integer counsellor_Id);
	
	public boolean updateEnquiry(Enquiry e);
	
	public List<Enquiry> getAll(Integer counsellorId);
	
	public Enquiry getEnquiry(Integer enquiry_Id);
	
	public List<Enquiry> filterEnquiry(EnqFilterRequestDto enquiryFilter, Integer cousellor_Id);

}
