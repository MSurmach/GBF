package com.godeltech.gbf.service.user.impl;

import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.repository.UserDataRepository;
import com.godeltech.gbf.service.user.UserDataService;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.godeltech.gbf.repository.specification.UserDataSpecs.*;

@Service
@AllArgsConstructor
public class UserDataServiceImpl implements UserDataService {

    private UserDataRepository userDataRepository;

    @Override
    public List<UserData> findByUserData(UserData userData) {
        Specification<UserData> searchSpecification = byCityTo(userData.getCityTo()).
                and(byCityFrom(userData.getCityFrom()));
        LocalDate dateFrom = userData.getDateFrom();
        if (dateFrom != null) searchSpecification = searchSpecification.and(byDateFrom(dateFrom));
        LocalDate dateTo = userData.getDateTo();
        if (dateTo != null) searchSpecification = searchSpecification.and(byDateTo(dateTo));
        boolean documents = userData.isDocuments();
        if (documents) searchSpecification = searchSpecification.and(byDocumentsExist(documents));
        String packageSize = userData.getPackageSize();
        if (packageSize != null) searchSpecification = searchSpecification.and(byPackageSize(packageSize));
        int companionCount = userData.getCompanionCount();
        if (companionCount != 0) searchSpecification = searchSpecification.and(byCompanionCount(companionCount));
        return userDataRepository.findAll(searchSpecification);
    }

    @Override
    public List<UserData> findUserDataByTelegramUserId(Long telegramUserId) {
        return userDataRepository.findUserDataByTelegramUserId(telegramUserId);
    }

    @Override
    public UserData findUserDataByTelegramUserIdAndId(Long telegramUserId, Long recordId) {
        return userDataRepository.findUserDataByTelegramUserIdAndId(telegramUserId, recordId);
    }

    @Override
    public void removeUserDataByDateToBefore(LocalDate date) {
        userDataRepository.removeUserDataByDateToBefore(date);
    }

    @Override
    public void deleteById(Long id) {
        userDataRepository.deleteById(id);
    }

    @Override
    public void save(UserData userData) {
        userDataRepository.save(userData);
    }
}
