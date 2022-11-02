package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.BotMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface BotMessageRepository extends JpaRepository<BotMessage, Long> {
    List<BotMessage> findAllByUserIdAndChatId(Long telegramId, Long chatId);
    List<BotMessage> findByCreatedAtBefore(Timestamp localDateTime);
}
