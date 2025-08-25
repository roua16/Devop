package com.NovaMind.Project.NovaMind.Services;

import com.NovaMind.Project.NovaMind.Documents.Rapport;
import com.NovaMind.Project.NovaMind.Repositories.RapportsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RapportService {
    private final RapportsRepository rapportsRepository;

    public RapportService(RapportsRepository rapportsRepository) {
        this.rapportsRepository = rapportsRepository;
    }

    public List<Rapport> getAllRapports() {
        return rapportsRepository.findAll();
    }

    public Optional<Rapport> getRapportById(String id) {
        return rapportsRepository.findById(id);
    }

    public Rapport createRapport(Rapport rapport) {
        // uses annotation no need to set
        return rapportsRepository.save(rapport);
    }

    public Rapport updateRapport(String id, Rapport updatedRapport) {
        return rapportsRepository.findById(id)
                .map(rapport -> {
                    rapport.setTitle(updatedRapport.getTitle());
                    rapport.setContent(updatedRapport.getContent());
                    rapport.setRapportType(updatedRapport.getRapportType());
                    rapport.setStatus(updatedRapport.getStatus());
                    rapport.setUserId(updatedRapport.getUserId());
                    rapport.setTargetId(updatedRapport.getTargetId());
                    return rapportsRepository.save(rapport);
                })
                .orElseThrow(() -> new RuntimeException("Rapport not found with id: " + id));
    }

    public void deleteRapport(String id) {
        rapportsRepository.deleteById(id);
    }
}

