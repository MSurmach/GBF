package com.godeltech.gbf.service.bot_message;

import com.godeltech.gbf.model.db.BotMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.List;

public interface BotMessageService {
    void save(Long userId, Message message);

    void delete(BotMessage botMessage);

    List<BotMessage> findAllByTelegramIdAndChatId(Long telegramId, Long chatId);

    List<BotMessage> findExpiredMessageByDate(LocalDateTime expiredDate);

    void checkBotMessage(Integer messageId, Long userId, Long chatId);
}
