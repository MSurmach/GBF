package com.godeltech.gbf.service.offer.impl;

import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.repository.OfferRepository;
import com.godeltech.gbf.repository.specification.OfferSpecs;
import com.godeltech.gbf.service.offer.OfferService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Override
    public void save(UserData userData) {
        offerRepository.save(ModelUtils.createOffer(userData));
    }

    @Override
    @Transactional
    public void deleteExpiredOffersByDate(LocalDate expiredDate) {
        log.info("delete expired offers by date : {}", expiredDate);
        List<Offer> expiredOffers = offerRepository.findAll(OfferSpecs.byDateAfter(expiredDate));
        offerRepository.deleteAll(expiredOffers);
    }
}
