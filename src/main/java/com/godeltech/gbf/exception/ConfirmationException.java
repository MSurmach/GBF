package com.godeltech.gbf.exception;

import com.godeltech.gbf.model.State;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfirmationException extends RuntimeException {
    private final State state;
    private final String callbackQueryId;
}
