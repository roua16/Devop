package com.NovaMind.Project.NovaMind.controller;

import com.NovaMind.Project.NovaMind.Documents.MemberShip;
import com.NovaMind.Project.NovaMind.Documents.Pricing;
import com.NovaMind.Project.NovaMind.Repositories.MemberShipRepository;
import com.NovaMind.Project.NovaMind.Services.MembershipService;
import com.NovaMind.Project.NovaMind.Services.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin("*")
public class MembershipController {
    private final MembershipService membershipService;
    private final PricingService pricingService;

    @Autowired
    public MembershipController(MembershipService membershipService,PricingService pricingService) {
        this.membershipService = membershipService;
        this.pricingService=pricingService;
    }

    @GetMapping
    public List<MemberShip> getAllMemberships() {
        return membershipService.getAllMemberships();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberShip> getMembershipById(@PathVariable Long id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }

    @PostMapping
    public ResponseEntity<MemberShip> createMembership(@RequestBody MemberShip membership) {
        return ResponseEntity.status(HttpStatus.CREATED).body(membershipService.createMembership(membership));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MemberShip> updateMembership(@PathVariable("id") Long id, @RequestBody MemberShip membershipDetails) {
        return ResponseEntity.ok(membershipService.updateMembership(id, membershipDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable("id") Long id) {
        membershipService.deleteMembership(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/archive")
    public ResponseEntity<Map<String, String>> archiveMembership(@PathVariable Long id) {
        membershipService.archiveMembership(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Membership archivé avec succès.");
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}/restore")
    public ResponseEntity<Map<String, String>> restoreMembership(@PathVariable Long id) {
        membershipService.restoreMembership(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Membership restauré avec succès.");

        return ResponseEntity.ok(response);  
    }



    @GetMapping("/active")
    public List<MemberShip> getActiveMemberships() {
        List<MemberShip> activeMemberships = membershipService.getActiveMemberships();
        return activeMemberships;
    }

    @GetMapping("/archived")
    public List<MemberShip> getArchivedMemberships() {
        return membershipService.getArchivedMemberships();
    }
    @PutMapping("/auto-archive")
    public ResponseEntity<String> autoArchiveExpiredMemberships() {
        membershipService.autoArchiveExpiredMemberships();
        return ResponseEntity.ok("Archivage automatique exécuté avec succès.");
    }

}
