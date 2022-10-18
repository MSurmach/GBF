package com.godeltech.gbf.service.bot_message;

import com.godeltech.gbf.model.db.BotMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface BotMessageService {
    void save(Long telegramId, Message message);

    void delete(BotMessage botMessage);

    List<BotMessage> findAllByTelegramIdAndChatId(Long telegramId, Long chatId);
}
