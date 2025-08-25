package com.NovaMind.Project.NovaMind.Repositories;

import com.NovaMind.Project.NovaMind.Documents.Pricing;
import org.hibernate.sql.ast.tree.expression.JdbcParameter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PricingRepository extends JpaRepository<Pricing,Long> {
    List<Pricing> findByMemberShipId(Long membershipId);
    void deleteByMemberShipId(Long membershipId);
}
