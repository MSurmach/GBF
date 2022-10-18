package com.godeltech.gbf.service.bot_message.impl;

import com.godeltech.gbf.model.db.BotMessage;
import com.godeltech.gbf.repository.BotMessageRepository;
import com.godeltech.gbf.service.bot_message.BotMessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Service
@AllArgsConstructor
public class BotMessageServiceImpl implements BotMessageService {
    private final BotMessageRepository botMessageRepository;

    @Override
    public void save(Long telegramId, Message message) {
        BotMessage botMessage = new BotMessage();
        botMessage.setTelegramId(telegramId);
        botMessage.setChatId(message.getChatId());
        botMessage.setMessageId(message.getMessageId());
        botMessageRepository.save(botMessage);
    }

    @Override
    public void delete(BotMessage botMessage) {
        botMessageRepository.delete(botMessage);
    }

    @Override
    public List<BotMessage> findAllByTelegramIdAndChatId(Long telegramId, Long chatId) {
        return botMessageRepository.findAllByTelegramIdAndChatId(telegramId, chatId);
    }
}
