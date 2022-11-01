package com.godeltech.gbf.service.offer.impl;

import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.Offer;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.OfferRepository;
import com.godeltech.gbf.service.offer.OfferService;
import com.godeltech.gbf.service.user.TelegramUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private final TelegramUserService telegramUserService;


    @Override
    public void save(UserData userData) {
        Offer newOffer = ModelUtils.createOffer(userData);
        Optional<TelegramUser> telegramUserOptional = telegramUserService.getById(userData.getTelegramId());
        if (telegramUserOptional.isPresent())
            newOffer.setTelegramUser(telegramUserOptional.get());
        else
            newOffer.setTelegramUser(telegramUserService.save(userData));
        offerRepository.save(newOffer);
    }

    @Override
    public void removeByExpiredAtBefore(LocalDate localDate) {

    }

    @Override
    public void removeByChangedAtBefore(LocalDate localDate) {

    }
}
