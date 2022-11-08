package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.db.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfferRepository extends JpaRepository<Offer,Long>, JpaSpecificationExecutor<Offer> {

    Page<Offer> findOffersByTelegramUserIdAndRoleOrderByIdDesc(Long telegramUserId, Role role, Pageable pageable);

    Page<Offer> findAllByRole(Role role,Pageable pageable);
}
