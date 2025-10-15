package in.ashokit.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnqFilterRequestDto {
	private String classMode;
	private Integer courseId;
	private String status;
}
