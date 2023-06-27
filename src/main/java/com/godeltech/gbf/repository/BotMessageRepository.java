package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.BotMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BotMessageRepository extends JpaRepository<BotMessage, Integer> {
    List<BotMessage> findAllByUserIdAndChatId(Long telegramId, Long chatId);
    List<BotMessage> findByCreatedAtBefore(Timestamp localDateTime);
}
