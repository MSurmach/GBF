package com.godeltech.gbf.gui.keyboard;

import com.godeltech.gbf.model.UserData;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public interface Keyboard {
    InlineKeyboardMarkup getKeyboardMarkup(UserData userData);
}
