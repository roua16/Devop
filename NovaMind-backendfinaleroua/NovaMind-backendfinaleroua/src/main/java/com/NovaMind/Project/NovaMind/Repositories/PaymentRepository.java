package com.NovaMind.Project.NovaMind.Repositories;

import com.NovaMind.Project.NovaMind.Documents.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    boolean existsByCourse(com.NovaMind.Project.NovaMind.Documents.Course course);
    boolean existsByCourse_Modules(com.NovaMind.Project.NovaMind.Documents.CourseModule module);
}
