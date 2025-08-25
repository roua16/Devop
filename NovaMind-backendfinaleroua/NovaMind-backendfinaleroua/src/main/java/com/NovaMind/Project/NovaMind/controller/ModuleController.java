package com.NovaMind.Project.NovaMind.controller;

import com.NovaMind.Project.NovaMind.Documents.Course;
import com.NovaMind.Project.NovaMind.Documents.CourseModule;
import com.NovaMind.Project.NovaMind.Services.CoursEService;
import com.NovaMind.Project.NovaMind.Services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/module")
@CrossOrigin("*")
public class ModuleController {

    private final ModuleService moduleService;
    private final CoursEService courseService;
    @Autowired
    public ModuleController(ModuleService moduleService, CoursEService courseService) {
        this.moduleService = moduleService;
        this.courseService = courseService;
    }
    @PostMapping("/{courseId}")
    public CourseModule addModule(@RequestBody CourseModule module, @RequestParam Long courseId) {
        return moduleService.addModule(courseId, module);
    }
    @GetMapping("/by-course/{courseId}")
    public ResponseEntity<?> getModulesByCourse(@PathVariable Long courseId) {
        Course course = courseService.getCourseById(courseId);
        return ResponseEntity.ok(moduleService.findByCourseId(courseId));
    }
    @GetMapping("/IspaideCourse/{courseId}")
    public Boolean isPaideCourse(@PathVariable Long courseId)  {
        Course course = courseService.getCourseById(courseId);
        return course.isPaid();
    }
    @GetMapping("/IspaideModile/{ModuleId}")
    public Boolean isPaiedModule(@PathVariable Long ModuleId)  {
        CourseModule courseModule = moduleService.getByCourseId(ModuleId);
        return courseModule.isPaid();
    }





}
