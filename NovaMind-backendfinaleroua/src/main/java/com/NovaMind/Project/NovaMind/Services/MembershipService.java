package com.NovaMind.Project.NovaMind.Services;

import com.NovaMind.Project.NovaMind.Documents.MemberShip;
import com.NovaMind.Project.NovaMind.Repositories.MemberShipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MembershipService {
    private final MemberShipRepository membershipRepository;
    private final PricingService pricingService;

    @Autowired
    public MembershipService(MemberShipRepository membershipRepository,PricingService pricingService) {
        this.membershipRepository = membershipRepository;
        this.pricingService=pricingService;
    }

    public List<MemberShip> getAllMemberships() {
        return membershipRepository.findAll();
    }

    public MemberShip getMembershipById(Long id) {
        return membershipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Membership not found"));
    }

    public MemberShip createMembership(MemberShip membership) {
        return membershipRepository.save(membership);
    }

    public MemberShip updateMembership(Long id, MemberShip membershipDetails) {
        MemberShip membership = getMembershipById(id);
        membership.setFees(membershipDetails.getFees());
        membership.setPaymentMethod(membershipDetails.getPaymentMethod());
        membership.setStatus(membershipDetails.getStatus());
        membership.setStart_date(membershipDetails.getStart_date());
        membership.setEnd_date(membershipDetails.getEnd_date());
        membership.setPricing(membershipDetails.getPricing());
        membership.setMembershipType(membershipDetails.getMembershipType());
        return membershipRepository.save(membership);
    }

    public void deleteMembership(Long id) {
        pricingService.deleteByMembershipId(id);

        membershipRepository.deleteById(id);
    }

    public void archiveMembership(Long id) {
        Optional<MemberShip> membershipOpt = membershipRepository.findById(id);
        if (membershipOpt.isPresent()) {
            MemberShip membership = membershipOpt.get();
            membership.setArchived(true);
            membership.setStatus("INACTIVE");
            membershipRepository.save(membership);
        }
    }
    public void restoreMembership(Long id) {
        Optional<MemberShip> membershipOpt = membershipRepository.findById(id);
        if (membershipOpt.isPresent()) {
            MemberShip membership = membershipOpt.get();
            membership.setArchived(false);
            membership.setStatus("ACTIVE");
            membershipRepository.save(membership);
        }
    }
    public List<MemberShip> getActiveMemberships() {
        return membershipRepository.findByArchivedFalse();
    }

    public List<MemberShip> getArchivedMemberships() {
        return membershipRepository.findByArchivedTrue();
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoArchiveExpiredMemberships() {
        List<MemberShip> expiredMemberships = membershipRepository.findByArchivedFalse()
                .stream()
                .filter(m -> m.getEnd_date().before(new Date()))
                .collect(Collectors.toList());

        for (MemberShip membership : expiredMemberships) {
            membership.setArchived(true);
            membership.setStatus("INACTIVE");
            membershipRepository.save(membership);
        }
    }


}

