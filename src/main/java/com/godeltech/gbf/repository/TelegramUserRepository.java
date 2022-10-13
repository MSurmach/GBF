package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long>, JpaSpecificationExecutor<TelegramUser> {

}
