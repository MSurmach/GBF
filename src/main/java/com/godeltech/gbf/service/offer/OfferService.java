package com.godeltech.gbf.service.offer;

import java.time.LocalDate;

public interface OfferService {
    void deleteExpiredOffersByDate(LocalDate expiredDate);
}
