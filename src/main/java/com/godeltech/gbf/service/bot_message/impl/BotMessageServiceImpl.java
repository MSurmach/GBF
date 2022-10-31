package com.godeltech.gbf.service.bot_message.impl;

import com.godeltech.gbf.model.db.BotMessage;
import com.godeltech.gbf.repository.BotMessageRepository;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BotMessageServiceImpl implements BotMessageService {
    private final BotMessageRepository botMessageRepository;

    @Override
    @Transactional
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
    @Transactional
    public void delete(BotMessage botMessage) {
        log.info("Delete message : {}", botMessage);
        botMessageRepository.delete(botMessage);
    }

    @Override
    public List<BotMessage> findAllByTelegramIdAndChatId(Long telegramId, Long chatId) {
        log.info("Find message by telegram id : {} and chat id: {}",telegramId,chatId);
        return botMessageRepository.findAllByUserIdAndChatId(telegramId, chatId);
    }

    @Override
    public List<BotMessage> findExpiredMessageByDate(LocalDateTime expiredDate) {
        log.info("Find messages by expired date : {}",expiredDate);
        return botMessageRepository.findByCreatedAtAfter(expiredDate);
    }

}
