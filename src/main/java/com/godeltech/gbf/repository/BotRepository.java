package com.godeltech.gbf.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BotRepository extends JpaRepository<User, Long> {
}
