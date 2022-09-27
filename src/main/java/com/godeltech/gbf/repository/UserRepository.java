package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserData, Long> {
    List<UserData> findUsersByCityFromAndCityTo(String cityFrom, String cityTo);

    List<UserData> findUsersByTelegramId(Long telegramId);
}
