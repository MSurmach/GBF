package com.godeltech.gbf.service.user.impl;

import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.UserRecord;
import com.godeltech.gbf.repository.UserRepository;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.repository.specification.UserRecordSpecs.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Page<UserRecord> findCourierByUserDataAndRole(UserData userData, Role role, int pageNumber) {
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<UserRecord> searchSpecification = byCityToEquals(userData.getCityTo()).
                and(byCityFromEquals(userData.getCityFrom())).
                and(byRoleEquals(role));
        LocalDate dateFrom = userData.getDateFrom();
        if (dateFrom != null) searchSpecification = searchSpecification.and(byDateFromEquals(dateFrom));
        LocalDate dateTo = userData.getDateTo();
        if (dateTo != null) searchSpecification = searchSpecification.and(byDateToEquals(dateTo));
        boolean documents = userData.isDocuments();
        if (documents) searchSpecification = searchSpecification.and(byDocumentsExist(documents));
        String packageSize = userData.getPackageSize();
        if (packageSize != null) searchSpecification = searchSpecification.and(byPackageSizeEquals(packageSize));
        int companionCount = userData.getCompanionCount();
        if (companionCount != 0) searchSpecification = searchSpecification.and(byCompanionCountEquals(companionCount));
        return userRepository.findAll(searchSpecification, pageable);
    }

    @Override
    public Page<UserRecord> findClientByUserDataAndRole(UserData userData, Role role, int pageNumber) {
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<UserRecord> searchSpecification =
                byCityToEquals(userData.getCityTo()).
                        and(byCityFromEquals(userData.getCityFrom())).
                        and(byRoleEquals(role)).
                        and(byDateToEquals(userData.getDateTo()).or(byDateToIsNull())).
                        and(byDateFromEquals(userData.getDateFrom()).or(byDateFromIsNull())).
                        and(byDocumentsExist(userData.isDocuments())).
                        and(byPackageSizeEquals(userData.getPackageSize()).or(byPackageSizeIsNull())).
                        and(byCompanionCountEquals(userData.getCompanionCount()).or(byCompanionCountIsZero()));
        return userRepository.findAll(searchSpecification, pageable);
    }


    @Override
    public Page<UserRecord> findByTelegramUserIdAndRole(Long telegramUserId, Role role, int pageNumber) {
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findByTelegramUserIdAndRole(telegramUserId, role, pageable);
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
    public void deleteById(Long record_id) {
        userRepository.deleteById(record_id);
    }

    @Override
    public void save(UserData userData) {
        UserRecord newRecord = new UserRecord(userData);
        userRepository.save(newRecord);
    }
}