package com.godeltech.gbf.service.offer;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.Session;
import com.godeltech.gbf.model.db.Offer;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface OfferService {
    void save(Session session);

    void deleteExpiredOffersByDate(LocalDate expiredDate);

    Page<Offer> findAllOffersByUserIdAndRole(Long userId, Role role, int pageNumber);

    void deleteOfferById(Long offerId);

    Page<Offer> findSuitableOffersByGivenOffer(Offer offer, int pageNumber);

    Page<Offer> findAllByRole(Role role, int pageNumber);

    List<Offer> findSuitableOffersListByGivenOffer(Offer offer);

    int getOrderedNumberOfOfferWithId(Long id, Role role, Long offerId);

    long countByRole(Role role);
}
