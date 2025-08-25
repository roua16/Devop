package com.NovaMind.Project.NovaMind.Documents;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CourseModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int duration;
    private int prix ;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private boolean isPaid;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;  // Si ce module est un quiz, associe-le à un quiz spécifique


    public boolean isPaid() {
        return isPaid;
    }

    public CourseModule setPaid(boolean paid) {
        isPaid = paid;
        return null;
    }

    public Long getId() {
        return id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}

