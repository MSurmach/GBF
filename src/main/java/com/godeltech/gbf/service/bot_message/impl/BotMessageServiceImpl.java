package com.godeltech.gbf.service.bot_message.impl;

import com.godeltech.gbf.model.db.BotMessage;
import com.godeltech.gbf.repository.BotMessageRepository;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class BotMessageServiceImpl implements BotMessageService {
    private final BotMessageRepository botMessageRepository;

    @Override
    public void save(Long userId, Message message) {
        log.info("Save message with user id : {}", userId);
        botMessageRepository.save(BotMessage.builder()
                .messageId(message.getMessageId())
                .userId(userId)
                .chatId(message.getChatId())
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build());
    }

    @Override
    public void delete(BotMessage botMessage) {
        botMessageRepository.delete(botMessage);
    }

    @Override
    public List<BotMessage> findAllByTelegramIdAndChatId(Long telegramId, Long chatId) {
        return botMessageRepository.findAllByUserIdAndChatId(telegramId, chatId);
    }
}
