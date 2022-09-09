package com.godeltech.gbf.bot_messages;

import com.godeltech.gbf.state.BotState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface BotMessage {
    SendMessage getMessageBotState ();
}
