package com.godeltech.gbf.service.user.impl;

import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.TelegramUserRepository;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private TelegramUserRepository telegramUserRepository;

    @Override
    public Page<UserRecord> findCourierByUserDataAndRole(UserData userData, Role role, int pageNumber) {
//        int pageSize = 1;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Specification<UserRecord> searchSpecification = byCityToEquals(userData.getCityTo()).
//                and(byCityFromEquals(userData.getCityFrom())).
//                and(byRoleEquals(role));
//        LocalDate dateFrom = userData.getDateFrom();
//        if (dateFrom != null) searchSpecification = searchSpecification.and(byDateFromEquals(dateFrom));
//        LocalDate dateTo = userData.getDateTo();
//        if (dateTo != null) searchSpecification = searchSpecification.and(byDateToEquals(dateTo));
//        boolean documents = userData.isDocumentsExist();
//        if (documents) searchSpecification = searchSpecification.and(byDocumentsIsGreaterThanOrEquals(documents));
//        String packageSize = userData.getPackageSize();
//        if (packageSize != null) searchSpecification = searchSpecification.and(byPackageSizeEquals(packageSize));
//        searchSpecification = searchSpecification.and(byCompanionCountIsGreaterThanOrEqualTo(userData.getCompanionCount()));
//        return userRepository.findAll(searchSpecification, pageable);
        return null;
    }

    @Override
    public Page<UserRecord> findClientByUserDataAndRole(UserData userData, Role role, int pageNumber) {
//        int pageSize = 1;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        Specification<UserRecord> searchSpecification =
//                byCityToEquals(userData.getCityTo()).
//                        and(byCityFromEquals(userData.getCityFrom())).
//                        and(byRoleEquals(role)).
//                        and(byDateToEquals(userData.getDateTo()).or(byDateToIsNull())).
//                        and(byDateFromEquals(userData.getDateFrom()).or(byDateFromIsNull())).
//                        and(byDocumentsIsLessThanOrEquals(userData.isDocumentsExist()).
//                                or(byPackageSizeEquals(userData.getPackageSize()).or(byPackageSizeIsNull())).
//                                or(byCompanionCountIsLessThanOrEqualTo(userData.getCompanionCount())));
//        return userRepository.findAll(searchSpecification, pageable);
        return null;
    }


    @Override
    public Page<TelegramUser> findTelegramUsersByTelegramIdAndRole(Long telegramId, Role role, int pageNumber) {
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return telegramUserRepository.findTelegramUsersByTelegramIdAndRole(telegramId, role, pageable);
    }

    @Override
    public UserRecord findByTelegramUserIdAndRecordId(Long telegramUserId, Long recordId) {
        return userRepository.findByTelegramUserIdAndRecordId(telegramUserId, recordId);
    }

    @Override
    public void removeByDateToBefore(LocalDate date) {
        userRepository.removeByDateToBefore(date);
    }

    @Override
    public void removeByChangedAtAfter(LocalDate date) {
        userRepository.removeByChangedAtAfter(date);
    }

    @Override
    public void deleteById(Long userId) {
        telegramUserRepository.deleteById(userId);
    }

    @Override
    public void save(UserData userData) {
        TelegramUser forSave = ModelUtils.telegramUser(userData);
        telegramUserRepository.save(forSave);
    }
}
