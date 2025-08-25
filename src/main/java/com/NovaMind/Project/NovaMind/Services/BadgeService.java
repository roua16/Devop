package com.NovaMind.Project.NovaMind.Services;

import com.NovaMind.Project.NovaMind.Documents.Badge;
import com.NovaMind.Project.NovaMind.Repositories.BadgesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BadgeService {
    private final BadgesRepository badgeRepository;

    public BadgeService(BadgesRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public Optional<Badge> getBadgeById(String id) {
        return badgeRepository.findById(id);
    }

    public Badge createBadge(Badge badge) {
        return badgeRepository.save(badge);
    }

    public Badge updateBadge(String id, Badge updatedBadge) {
        return badgeRepository.findById(id)
                .map(badge -> {
                    badge.setName(updatedBadge.getName());
                    badge.setDescription(updatedBadge.getDescription());
                    badge.setImageUrl(updatedBadge.getImageUrl());
                    badge.setCriteria(updatedBadge.getCriteria());
                    return badgeRepository.save(badge);
                })
                .orElseThrow(() -> new RuntimeException("Badge not found with id: " + id));
    }

    public void deleteBadge(String id) {
        badgeRepository.deleteById(id);
    }
}
