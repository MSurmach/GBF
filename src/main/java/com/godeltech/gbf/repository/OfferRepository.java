package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OfferRepository extends JpaRepository<Offer,Long>, JpaSpecificationExecutor<Offer> {
}
