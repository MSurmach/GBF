package com.godeltech.gbf.service.authorization;

import com.godeltech.gbf.exception.MembershipException;
import com.godeltech.gbf.exception.MessageFromGroupException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberBanned;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberLeft;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberRestricted;

@Service
@Slf4j
public class AuthorizationService {
    @Value("${bot.chmokiId}")
    private String chmokiId;

    public boolean isAuthorizedMessageFromChatMember(Message message, ChatMember chatMember) {
        log.info("Check membership of user with id: {} and with username : {}",
                message.getFrom().getId(), message.getFrom().getUserName());
        String fromChatId = message.getChat().getId().toString();
        return isMessageNotFromChmokiGroup(fromChatId, message.getFrom()) && checkMembership(chatMember);
    }

    private boolean isMessageNotFromChmokiGroup(String fromChatId, User user) {
        log.debug("Is message from chmoki group ? with chat id : {}", fromChatId);
        if (fromChatId.equals(chmokiId))
            throw new MessageFromGroupException("The user with username: " + user.getUserName() +
                    " and id: " + user.getId() + " tries to execute commands inside the Chmoki group. This command is ignored");
        return true;
    }

    private boolean checkMembership(ChatMember chatMember) {
        log.debug("Check if user with username :{} a member of Chmoki group", chatMember.getUser().getUserName());
        if (chatMember instanceof ChatMemberBanned ||
                chatMember instanceof ChatMemberLeft ||
                chatMember instanceof ChatMemberRestricted)
            throw new MembershipException("User with username: " + chatMember.getUser().getUserName() +
                    " and id: " + chatMember.getUser().getId()
                    + " doesn't have access to the Chmoki group. All requests were ignored");
        return true;
    }
}
