package com.godeltech.gbf.autorization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberBanned;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberLeft;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberRestricted;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Component
public class UpdateAutorization {

    @Value("${bot.chmokiId}")
    private String chmokiId;
    private final SpringWebhookBot springWebhookBot;

    public UpdateAutorization(SpringWebhookBot springWebhookBot) {
        this.springWebhookBot = springWebhookBot;
    }

    public boolean isValid(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            User user = message.getFrom();
            String fromChatId = message.getChat().getId().toString();
            return isMessageNotFromChmokiGroup(fromChatId) && isChmokiMember(user);
        }
        return true;
    }

    private boolean isMessageNotFromChmokiGroup(String fromChatId) {
        return !fromChatId.equals(chmokiId);
    }

    private boolean isChmokiMember(User user) {
        try {
            ChatMember chatMember = springWebhookBot.execute(GetChatMember.builder()
                    .chatId(chmokiId)
                    .userId(user.getId())
                    .build());
            if (chatMember instanceof ChatMemberBanned ||
                    chatMember instanceof ChatMemberLeft ||
                    chatMember instanceof ChatMemberRestricted)
                return false;
        } catch (TelegramApiException e) {
            return false;
        }
        return true;
    }
}
