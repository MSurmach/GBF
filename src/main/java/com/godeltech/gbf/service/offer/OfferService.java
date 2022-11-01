package com.godeltech.gbf.service.offer;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.Offer;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface OfferService {
    void save(UserData userData);

    void deleteExpiredOffersByDate(LocalDate expiredDate);

    Page<Offer> findAllOffersByUserId(Long userId, int pageNumber);
}
