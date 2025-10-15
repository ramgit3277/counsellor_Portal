package in.ashokit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.dto.DashboardResponseDto;
import in.ashokit.entities.Counsellor;
import in.ashokit.entities.Enquiry;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.EnquiryRepo;
import in.ashokit.service.CounsellorService;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	@Autowired
	private CounsellorRepo counsellorRepo;
	@Autowired
	private EnquiryRepo enquiryRepo;
	@Override
	public Counsellor login(String email, String password) {
		return counsellorRepo.findByEmailAndPassword(email, password);
	}

	@Override
	public boolean saveCounsellor(Counsellor c) {
		return counsellorRepo.save(c).getCounsellorId()!=null;
	}

	@Override
	public DashboardResponseDto getDashboard(Integer Counsellor_Id) {
		List<Enquiry> enquiries = enquiryRepo.findByCounsellorCounsellorId(Counsellor_Id);
		Map<String, Long> collect = enquiries.stream().collect(Collectors.groupingBy(Enquiry::getStatus, Collectors.counting()));
		int total = collect.size();
		int opened = collect.getOrDefault("OPEN",0l).intValue();
		int entrolled = collect.getOrDefault("ENTROLLED",0l).intValue();
		int closed = collect.getOrDefault("LOST",0l).intValue();
		
		DashboardResponseDto dt = DashboardResponseDto.builder()
													  .totalEnquiries(total)
													  .openEnquiries(opened)
													  .entrolledEnquiries(entrolled)
													  .lostEnquiries(closed)
													  .build();
		return dt;
		
		
	}


}
