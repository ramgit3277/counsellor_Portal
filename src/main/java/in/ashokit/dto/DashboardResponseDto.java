package in.ashokit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DashboardResponseDto {
	private int totalEnquiries;
	private int openEnquiries;
	private int entrolledEnquiries;
	private int lostEnquiries;
}
