package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long>, JpaSpecificationExecutor<TelegramUser> {
    Optional<TelegramUser> findByIdAndUserName(Long id,String userName);
}
