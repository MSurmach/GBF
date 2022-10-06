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
    public Page<UserRecord> findByUserDataAndRole(UserData userData, Role role, int pageNumber) {
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<UserRecord> searchSpecification = byCityTo(userData.getCityTo()).
                and(byCityFrom(userData.getCityFrom())).
                and(byRole(role));
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
