package in.ashokit.service;

import in.ashokit.dto.DashboardResponseDto;
import in.ashokit.entities.Counsellor;

public interface CounsellorService {
	
	public Counsellor login(String email, String password);
	
	public boolean saveCounsellor(Counsellor c);
	
	public DashboardResponseDto getDashboard(Integer Counsellor_Id);
	
	
}
