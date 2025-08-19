package com.NovaMind.Project.NovaMind.Services;

import com.NovaMind.Project.NovaMind.Documents.MemberShip;
import com.NovaMind.Project.NovaMind.Documents.Pricing;
import com.NovaMind.Project.NovaMind.Exception.ResourceNotFoundException;
import com.NovaMind.Project.NovaMind.Repositories.MemberShipRepository;
import com.NovaMind.Project.NovaMind.Repositories.PricingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {
    private final PricingRepository pricingRepository;
    private final MemberShipRepository membershipRepository;

    public PricingService(PricingRepository pricingRepository, MemberShipRepository membershipRepository) {
        this.pricingRepository = pricingRepository;
        this.membershipRepository = membershipRepository;
    }

    public Pricing createPricingForMembership(Long membershipId, Pricing pricing) {
        MemberShip membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new ResourceNotFoundException("Membership non trouvé avec l'id: " + membershipId));

        pricing.setMemberShip(membership);

        return pricingRepository.save(pricing);
    }
    public List<Pricing> getAllPricings() {
        return pricingRepository.findAll();
    }

    public List<Pricing> getPricingsByMembershipId(Long membershipId) {
        return pricingRepository.findByMemberShipId(membershipId);
    }
    public void delete(Pricing pricing) {
        if (pricing != null) {
            pricingRepository.delete(pricing);
        } else {
            throw new IllegalArgumentException("Pricing cannot be null");
        }
    }
    @Transactional
    public void deleteByMembershipId(Long membershipId) {
        pricingRepository.deleteByMemberShipId(membershipId);
    }
    public Pricing updatePricing(Long id, Pricing newPricing) {
        return pricingRepository.findById(id)
                .map(pricing -> {
                    pricing.setPrice(newPricing.getPrice());
                    pricing.setTypepricing(newPricing.getTypepricing());
                    pricing.setStart_date(newPricing.getStart_date());
                    pricing.setEnd_date(newPricing.getEnd_date());
                    pricing.setReduction(newPricing.getReduction());
                    return pricingRepository.save(pricing);
                }).orElseThrow(() -> new ResourceNotFoundException("Pricing not found with id " + id));
    }




}
