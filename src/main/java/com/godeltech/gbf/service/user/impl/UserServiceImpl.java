package com.godeltech.gbf.service.user.impl;

import com.godeltech.gbf.model.ModelUtils;
import com.godeltech.gbf.model.Role;
import com.godeltech.gbf.model.UserData;
import com.godeltech.gbf.model.db.TelegramUser;
import com.godeltech.gbf.repository.TelegramUserRepository;
import com.godeltech.gbf.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.godeltech.gbf.repository.specification.UserSpecs.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private TelegramUserRepository telegramUserRepository;

    @Override
    public Page<TelegramUser> findCourierByUserDataAndRole(UserData userData, Role role, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 1);
        Specification<TelegramUser> searchSpecification = byRoleEquals(role);
        boolean documents = userData.isDocumentsExist();
        if (documents)
            searchSpecification = searchSpecification.and(byDocumentsIsGreaterThanOrEquals(documents));
        String packageSize = userData.getPackageSize();
        if (packageSize != null) searchSpecification = searchSpecification.and(byPackageSizeEquals(packageSize));
        searchSpecification = searchSpecification.and(byCompanionCountIsGreaterThanOrEqualTo(userData.getCompanionCount()));
        return telegramUserRepository.findAll(searchSpecification, pageable);
    }

    @Override
    public Page<TelegramUser> findClientByUserDataAndRole(UserData userData, Role role, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 1);
        Specification<TelegramUser> searchSpecification =
                byRoleEquals(role).
                        and(byDocumentsIsLessThanOrEquals(userData.isDocumentsExist()).
                                or(byPackageSizeEquals(userData.getPackageSize()).
                                        or(byPackageSizeIsNull())).
                                or(byCompanionCountIsLessThanOrEqualTo(userData.getCompanionCount())));
        return telegramUserRepository.findAll(searchSpecification, pageable);
    }


    @Override
    public Page<TelegramUser> findUsersByTelegramIdAndRole(Long telegramId, Role role, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 1);
        return telegramUserRepository.findUsersByTelegramIdAndRole(telegramId, role, pageable);
    }

    @Override
    public TelegramUser findByTelegramIdAndId(Long telegramId, Long id) {
        return telegramUserRepository.findUserByTelegramIdAndId(telegramId, id);
    }

    @Override
    public void removeByExpiredAtBefore(LocalDate date) {
        telegramUserRepository.removeByExpiredAtBefore(date);
    }

    @Override
    public void removeByChangedAtAfter(LocalDate date) {
        telegramUserRepository.removeByChangedAtAfter(date);
    }

    @Override
    public void deleteById(Long userId) {
        telegramUserRepository.deleteById(userId);
    }

    @Override
    public void save(UserData userData) {
        switch (userData.getRole()) {
            case REGISTRATIONS_VIEWER -> userData.setRole(Role.COURIER);
            case REQUESTS_VIEWER -> userData.setRole(Role.CLIENT);
        }
        com.godeltech.gbf.model.db.TelegramUser forSave = ModelUtils.telegramUser(userData);
        telegramUserRepository.save(forSave);
    }
}
