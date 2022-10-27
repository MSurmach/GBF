package com.godeltech.gbf.model;

import com.godeltech.gbf.LocalMessageSource;
import lombok.Getter;

@Getter
public enum Delivery {
    XXS, XS, S, M, L;

    public String getLocalDescription(LocalMessageSource lms) {
        return lms.getLocaleMessage(this.name());
    }
}
