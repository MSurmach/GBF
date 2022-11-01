package com.godeltech.gbf.service.offer;

import com.godeltech.gbf.model.UserData;

import java.time.LocalDate;

public interface OfferService {
    void save(UserData userData);

    void removeByExpiredAtBefore(LocalDate localDate);

    void removeByChangedAtBefore(LocalDate localDate);

    void deleteExpiredOffersByDate(LocalDate expiredDate);
}
