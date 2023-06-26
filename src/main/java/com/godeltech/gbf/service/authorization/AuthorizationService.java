package com.godeltech.gbf.service.authorization;

import com.godeltech.gbf.exception.MembershipException;
import com.godeltech.gbf.exception.MessageFromGroupException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberBanned;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberLeft;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberRestricted;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationService {

    @Value("${bot.chmokiId}")
    private String chmokiId;
    private final TelegramLongPollingBot telegramLongPollingBot;
//
//    public AuthorizationService(SpringWebhookBot springWebhookBot) {
//        this.springWebhookBot = springWebhookBot;
//    }

    public boolean isValid(Update update) {
        if (update.hasMessage()) {
            log.info("Check membership of user with id: {} and with username : {}",
                    update.getMessage().getFrom().getId(), update.getMessage().getFrom().getUserName());
            Message message = update.getMessage();
            String fromChatId = message.getChat().getId().toString();
            return isMessageNotFromChmokiGroup(fromChatId) && checkMembership(message);
        }
        return true;
    }

    private boolean isMessageNotFromChmokiGroup(String fromChatId) {
        log.debug("Is message from chmoki group ? with chat id : {}", fromChatId);
        if (fromChatId.equals(chmokiId))
            throw new MessageFromGroupException();
        return true;
    }

    private boolean checkMembership(Message message) {
        try {
            log.debug("Is user a member of group ? : {}", message.getFrom().getUserName());
            ChatMember chatMember = telegramLongPollingBot.execute(GetChatMember.builder()
                    .chatId(chmokiId)
                    .userId(message.getFrom().getId())
                    .build());
            if (chatMember instanceof ChatMemberBanned ||
                    chatMember instanceof ChatMemberLeft ||
                    chatMember instanceof ChatMemberRestricted)
                throw new MembershipException(message);
        } catch (TelegramApiException e) {
            throw new MembershipException(message);
        }
        return true;
    }
}
