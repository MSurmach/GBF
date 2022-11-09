package com.godeltech.gbf.service.offer;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.SessionData;
import com.godeltech.gbf.model.db.Offer;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface OfferService {
    void save(SessionData sessionData);

    void deleteExpiredOffersByDate(LocalDate expiredDate);

    Page<Offer> findAllOffersByUserIdAndRole(Long userId, Role role, int pageNumber);

    void deleteOfferById(Long offerId);

    Page<Offer> findSuitableOffersByGivenOffer(Offer offer, int pageNumber);

    Page<Offer> findAllByRole(Role role, int pageNumber);

    List<Offer> findSuitableOffersListByGivenOffer(Offer offer);
}
