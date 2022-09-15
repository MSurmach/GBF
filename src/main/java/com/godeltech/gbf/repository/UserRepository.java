package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findUsersByCityFromAndCityTo(String cityFrom, String cityTo);

    List<User> findUsersByTelegramId(Long telegramId);
}
