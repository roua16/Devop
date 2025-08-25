package com.NovaMind.Project.NovaMind.controller;

import com.NovaMind.Project.NovaMind.Documents.PredictionRequest;
import com.NovaMind.Project.NovaMind.service.PredictionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prediction")
@CrossOrigin(origins = "http://localhost:4200") // adapte selon ton front
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping
    public String predict(@RequestBody PredictionRequest request) {
        return predictionService.predictTarifPlan(request);
    }
}
