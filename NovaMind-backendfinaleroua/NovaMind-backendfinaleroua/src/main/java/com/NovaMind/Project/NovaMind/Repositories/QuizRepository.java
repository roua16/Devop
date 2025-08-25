package com.NovaMind.Project.NovaMind.Repositories;

import com.NovaMind.Project.NovaMind.Documents.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
