package com.godeltech.gbf.management.button;

import com.godeltech.gbf.LocalMessageSource;

public interface BotButton {
    String localLabel(LocalMessageSource localMessageSource);

    String name();
}
