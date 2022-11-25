package com.godeltech.gbf.gui.keyboard;

import com.godeltech.gbf.model.State;
import com.godeltech.gbf.model.Session;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface KeyboardType {
    State getState();
    InlineKeyboardMarkup getKeyboardMarkup(Session session);
}
