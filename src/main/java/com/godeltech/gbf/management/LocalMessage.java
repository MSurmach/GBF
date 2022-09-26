package com.godeltech.gbf.management;

import com.godeltech.gbf.LocalMessageSource;

public interface LocalMessage {
    String getLocalMessage(LocalMessageSource localMessageSource, String... args);
}
