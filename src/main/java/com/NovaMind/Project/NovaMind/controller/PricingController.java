package com.NovaMind.Project.NovaMind.controller;

import com.NovaMind.Project.NovaMind.Documents.Pricing;
import com.NovaMind.Project.NovaMind.Services.PricingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricings")
@CrossOrigin("*")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }
    @PostMapping("/{id}/pricing")
    public ResponseEntity<Pricing> addPricingForMembership(@PathVariable("id") Long membershipId,
                                                           @RequestBody Pricing pricing) {
        Pricing createdPricing = pricingService.createPricingForMembership(membershipId, pricing);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPricing);
    }

    @GetMapping
    public ResponseEntity<List<Pricing>> getAllPricings() {
        List<Pricing> pricings = pricingService.getAllPricings();
        return ResponseEntity.ok(pricings);
    }

    @GetMapping("/by-membership/{membershipId}")
    public ResponseEntity<List<Pricing>> getPricingsByMembership(@PathVariable("membershipId") Long membershipId) {
        List<Pricing> pricings = pricingService.getPricingsByMembershipId(membershipId);
        if (pricings.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(pricings);
        }
        return ResponseEntity.ok(pricings);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Pricing> updatePricing(@PathVariable Long id, @RequestBody Pricing pricing) {
        Pricing updatedPricing = pricingService.updatePricing(id, pricing);
        return ResponseEntity.ok(updatedPricing);
    }

}
