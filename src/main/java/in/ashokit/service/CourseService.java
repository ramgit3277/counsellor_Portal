package in.ashokit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.ashokit.entities.Course;

public interface CourseService {
	public List<Course> getCourses();
}
