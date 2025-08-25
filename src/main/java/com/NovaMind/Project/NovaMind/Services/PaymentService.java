package com.NovaMind.Project.NovaMind.Services;

import com.NovaMind.Project.NovaMind.Documents.Course;
import com.NovaMind.Project.NovaMind.Documents.CourseModule;
import com.NovaMind.Project.NovaMind.Documents.Payment;
import com.NovaMind.Project.NovaMind.Repositories.CourseRepository;
import com.NovaMind.Project.NovaMind.Repositories.ModuleRepository;
import com.NovaMind.Project.NovaMind.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    @Autowired
    public PaymentService(PaymentRepository paymentRepository, CourseRepository courseRepository, ModuleRepository moduleRepository) {
        this.paymentRepository = paymentRepository;
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
    }


    public Payment addPayment(Payment payment, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + courseId));

        // Ne pas modifier directement le nombre d'inscriptions avant le calcul du prix
        int currentInscription = course.getInscription();
        // Vérification si un paiement existe déjà pour ce cours
        if (paymentRepository.existsByCourse(course)) {
            throw new RuntimeException("Un paiement existe déjà pour ce cours");
        }
        // Incrémentation des inscriptions après avoir calculé et appliqué le prix
        course.setInscription(currentInscription + 1);

        // Sauvegarde du cours avec le prix mis à jour
        courseRepository.save(course);
        // Calcul du prix avant d'incrémenter les inscriptions
        double prixInitial = course.getPrix();
        double prixFinal = prixInitial;

        // Appliquer la mise à jour du prix selon le nombre d'inscriptions avant le paiement
        if (currentInscription >= 9 && currentInscription <= 50) {
            prixFinal = prixInitial * 1.10; // +10%
        } else if (currentInscription > 50) {
            prixFinal = prixInitial * 1.20; // +20%
        }
        // Mise à jour du prix du cours
        course.setPrix((int)prixFinal);
        course.getModules().set(0,course.getModules().get(0).setPaid(true));
        course.setPaid(true);

        courseRepository.save(course);

        // Associer le paiement au cours et le sauvegarder
        payment.setCourse(course);
        return paymentRepository.save(payment);
    }
    public Payment addPaymentModule(Payment payment, Long moduleId) {
        CourseModule courseModule = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + moduleId));

        courseModule.setPaid(true);

        // Associer le paiement au module
        payment.setCourseModule(courseModule); // assuming Payment entity has a setModule method
        payment.setCourse(courseModule.getCourse());
        courseModule.getCourse().setInscription(courseModule.getCourse().getInscription() + 1);
        courseModule.getCourse().setPaid(true);
        // Sauvegarder le paiement dans la base de données
        paymentRepository.save(payment);

        // Sauvegarder le module mis à jour
        moduleRepository.save(courseModule);

        return payment;
    }

}
