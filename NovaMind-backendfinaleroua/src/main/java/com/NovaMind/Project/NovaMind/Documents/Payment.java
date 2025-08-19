package com.NovaMind.Project.NovaMind.Documents;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.plaf.IconUIResource;
import java.util.Date;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String paye;
    private String NumeroCarte;
    private Date dateExpirtation;
    private int CodeCvc;
    @ManyToOne(cascade = CascadeType.PERSIST)

    private Course  course;
    @OneToOne
    private CourseModule  courseModule;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPaye() {
        return paye;
    }

    public void setPaye(String paye) {
        this.paye = paye;
    }

    public String getNumeroCarte() {
        return NumeroCarte;
    }

    public void setNumeroCarte(String numeroCarte) {
        NumeroCarte = numeroCarte;
    }

    public Date getDateExpirtation() {
        return dateExpirtation;
    }

    public void setDateExpirtation(Date dateExpirtation) {
        this.dateExpirtation = dateExpirtation;
    }

    public int getCodeCvc() {
        return CodeCvc;
    }

    public void setCodeCvc(int codeCvc) {
        CodeCvc = codeCvc;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
