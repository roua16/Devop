package com.NovaMind.Project.NovaMind.service;

import org.springframework.stereotype.Service;
import com.NovaMind.Project.NovaMind.Documents.PredictionRequest;

@Service
public class PredictionService {

    public String predictTarifPlan(PredictionRequest request) {
        int score = 0;

        score += request.getNbCoursParMois();
        score += request.getHeuresParSemaine() * 2;

        if (request.getNiveau().equalsIgnoreCase("advanced")) {
            score += 10;
        } else if (request.getNiveau().equalsIgnoreCase("intermediate")) {
            score += 5;
        }

        if (score < 20) {
            return "Basic";
        } else if (score < 40) {
            return "Standard";
        } else {
            return "Premium";
        }
    }
}
