package com.NovaMind.Project.NovaMind.Services;

import com.NovaMind.Project.NovaMind.Documents.Course;
import com.NovaMind.Project.NovaMind.Repositories.CourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CoursEService {

    private final CourseRepository courseRepository;
    @Autowired
    public CoursEService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    private String secretKey;
    public boolean deleteCourseById(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course AddCourse(Course course) {
        return courseRepository.save(course);
    }
    public Course updateCourse(Long id, Course updatedCourse) {
        return courseRepository.findById(id).map(course -> {
            course.setTitle(updatedCourse.getTitle());
            course.setDescription(updatedCourse.getDescription());
            course.setImageUrl(updatedCourse.getImageUrl());
            return courseRepository.save(course);
        }).orElse(null);
    }
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }





}
