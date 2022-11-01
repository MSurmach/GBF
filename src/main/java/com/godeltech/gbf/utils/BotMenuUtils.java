package com.godeltech.gbf.utils;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

public class BotMenuUtils {
    public static List<BotCommand> getCommands() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(createBotCommand(START, START_DESCRIPTION));
//        listOfCommands.add(createBotCommand(HELP, HELP_DESCRIPTION));
        return listOfCommands;
    }
    private static BotCommand createBotCommand(java.lang.String command, java.lang.String description) {
        return BotCommand.builder()
                .command(command)
                .description(description)
                .build();
    }
    public static final String START = "/start";
    public static final String HELP = "/help";
    public static final String START_DESCRIPTION = "Info to start using this bot";
    public static final String HELP_DESCRIPTION = "What is this bot for";
}
