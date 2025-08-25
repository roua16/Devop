package com.NovaMind.Project.NovaMind.Services;

import com.NovaMind.Project.NovaMind.Documents.Course;
import com.NovaMind.Project.NovaMind.Documents.CourseModule;
import com.NovaMind.Project.NovaMind.Documents.Quiz;
import com.NovaMind.Project.NovaMind.Repositories.CourseRepository;
import com.NovaMind.Project.NovaMind.Repositories.ModuleRepository;
import com.NovaMind.Project.NovaMind.Repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ModuleService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository courseModuleRepository;

    @Autowired
    private QuizRepository quizRepository;

    public CourseModule addModule(Long courseId, CourseModule module) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        module.setCourse(course);
        courseModuleRepository.save(module);
        return module;
    }
    public List<CourseModule> findByCourseId(Long courseId) {
        return courseModuleRepository.findByCourseId(courseId);
    }
    public CourseModule getByCourseId(Long courseId) {
        return courseModuleRepository.findById(courseId).orElseThrow();
    }


}
