package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.BotMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BotMessageRepository extends JpaRepository<BotMessage, Integer> {
    List<BotMessage> findAllByUserIdAndChatId(Long telegramId, Long chatId);
    List<BotMessage> findByCreatedAtAfter(LocalDateTime localDateTime);
}
