package com.NovaMind.Project.NovaMind.Repositories;

import com.NovaMind.Project.NovaMind.Documents.MemberShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberShipRepository extends JpaRepository<MemberShip,Long> {

    List<MemberShip> findByArchivedFalse();
    List<MemberShip> findByArchivedTrue();
}
