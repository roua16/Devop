package com.NovaMind.Project.NovaMind.controller;

import com.NovaMind.Project.NovaMind.Documents.Course;
import com.NovaMind.Project.NovaMind.Documents.CourseModule;
import com.NovaMind.Project.NovaMind.Services.CoursEService;
import com.NovaMind.Project.NovaMind.Services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin("*")
public class CourseController {
@Autowired
    private  CoursEService courseService;
@Autowired
    private  ModuleService moduleService;


    public CourseController(CoursEService courseService, ModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        for (Course course : courses) {
            String imageUrl = "http://localhost:8081/images/" + course.getImageUrl().trim();
            course.setImageUrl(imageUrl);
        }
        System.out.println("Courses: " + courses);
        return ResponseEntity.ok(courses);
    }




    @PostMapping("/add")
    public ResponseEntity<Course> addCourse(@RequestPart("course") Course course, @RequestPart("image") MultipartFile image) {
        try {
            // Vérifier si le fichier a été bien reçu
            System.out.println("🖼️ Nom de l'image reçue : " + image.getOriginalFilename());
            System.out.println("📦 Taille de l'image : " + image.getSize() + " octets");
            System.out.println("📂 Type MIME : " + image.getContentType());

            if (image.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            // Construire le bon chemin
            String uploadDir = System.getProperty("user.dir") + "/Pibackend-main/src/main/resources/static/images/";
            String imagePath = uploadDir + image.getOriginalFilename();

            // Vérifier si le dossier existe, sinon le créer
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                if (dir.mkdirs()) {
                    System.out.println("✅ Dossier créé : " + uploadDir);
                } else {
                    System.out.println("❌ Erreur : Impossible de créer le dossier " + uploadDir);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
            }

            // Sauvegarde de l'image
            File imageFile = new File(imagePath);
            image.transferTo(imageFile);

            // Vérification de l'enregistrement
            System.out.println("📸 Image enregistrée à : " + imagePath);
            System.out.println("🔍 Fichier existe ? " + imageFile.exists());
            System.out.println("👀 Fichier lisible ? " + imageFile.canRead());

            if (!imageFile.exists() || !imageFile.canRead()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            // Assigner l'URL de l'image à l'entité Course
            course.setImageUrl(image.getOriginalFilename());

            // Sauvegarder la course
            Course savedCourse = courseService.AddCourse(course);
            if (savedCourse != null) {
                return ResponseEntity.ok(savedCourse);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        boolean deleted = courseService.deleteCourseById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Course> updateCourse(
            @PathVariable Long id,
            @RequestPart("course") Course updatedCourse,  // Données du cours
            @RequestPart(value = "image", required = false) MultipartFile image  // Image (optionnelle)
    ) {
        try {
            // Vérifier si le cours existe
            Course existingCourse = courseService.getCourseById(id);
            if (existingCourse == null) {
                return ResponseEntity.notFound().build();
            }

            // Mise à jour des champs
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setDescription(updatedCourse.getDescription());

            // Si une nouvelle image est envoyée, l'enregistrer
            if (image != null && !image.isEmpty()) {
                String imageName = image.getOriginalFilename();
                String uploadDir = System.getProperty("user.dir") + "/Pibackend-main/src/main/resources/static/images/";
                String imagePath = uploadDir + imageName;

                // Création du dossier s'il n'existe pas
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Sauvegarde de la nouvelle image
                File imageFile = new File(imagePath);
                image.transferTo(imageFile);

                // Mise à jour du champ image
                existingCourse.setImageUrl(imageName);
            }

            // Sauvegarde du cours modifié
            Course updated = courseService.updateCourse(existingCourse);
            return ResponseEntity.ok(updated);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/getcours")
  public List<Course> getAllcour(){
        return courseService.getAllCourses();
  }



}

