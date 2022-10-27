package com.godeltech.gbf.model.db;

import com.godeltech.gbf.LocalMessageSource;

public enum Delivery {
    XXS, XS, S, M, L;

    public String getLocalDescription(LocalMessageSource lms) {
        return lms.getLocaleMessage(this.name());
    }
}
