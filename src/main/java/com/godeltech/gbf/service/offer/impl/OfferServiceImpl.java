package com.godeltech.gbf.service.offer.impl;

import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.OfferRepository;
import com.godeltech.gbf.service.offer.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;

    @Override
    public void save(UserData userData) {
        offerRepository.save(ModelUtils.createOffer(userData));
    }
}
