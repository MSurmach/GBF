package com.godeltech.gbf.service.answer;

public class AnswerImpl {
//    private final DateTimeFormatter dateTimeFormatter;
//
//
//    public AnswerImpl(LocalMessageSource localMessageSource) {
//        super(localMessageSource);
//        dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy").withLocale(localMessageSource.getLocale());
//    }
//
//    @Override
//    public String getTextAnswer(UserData userData) {
//        State currentState = userData.getCurrentState();
//        StateFlow currentStateFlow = userData.getStateFlow();
//        return switch (currentState) {
//
//            case REGISTRATIONS -> constructRegistrationsMessage(userData, null);
//            case USERS_LIST -> constructUsersListMessage(null);
//            default -> null;
//        };
//    }
//
//    private String constructUsersListMessage(List<User> users) {
//
//        StringBuilder stringBuilder = new StringBuilder();
//        users.forEach(user -> {
//            String record = localMessageSource.getLocaleMessage("list.user.data",
//                    user.getUsername(),
//                    user.getCountryFrom(),
//                    user.getCityFrom(),
//                    user.getDateFrom().format(dateTimeFormatter),
//                    user.getCountryTo(),
//                    user.getCityTo(),
//                    user.getDateTo().format(dateTimeFormatter));
////                    user.getCARGO().name());
//            stringBuilder.append(record).append("\n\n");
//        });
//        return stringBuilder.toString();
//    }
//
//    private String constructConfirmationMessageForCourier(UserData userData) {
//        String username = userData.getUsername();
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(localMessageSource.getLocaleMessage("confirmation.courier", username));
//        stringBuilder.append("\n\n");
//        String confirmationData = localMessageSource.getLocaleMessage("courier.data",
//                localMessageSource.getLocaleMessage(userData.getCountryFrom()),
//                localMessageSource.getLocaleMessage(userData.getCityFrom()),
//                userData.getDateFrom().format(dateTimeFormatter),
//                localMessageSource.getLocaleMessage(userData.getCountryTo()),
//                localMessageSource.getLocaleMessage(userData.getCityTo()),
//                userData.getDateTo().format(dateTimeFormatter));
//        //userData.getLoadCategory().getLocalDescription(localMessageSource));
//        stringBuilder.append(confirmationData);
//        return stringBuilder.toString();
//    }
//
//    private String constructConfirmationMessageForCustomer(UserData userData) {
//        String username = userData.getUsername();
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(localMessageSource.getLocaleMessage("confirmation.customer", username));
//        stringBuilder.append("\n\n");
//        String confirmationData = localMessageSource.getLocaleMessage("customer.data",
//                userData.getCountryFrom(),
//                userData.getCityFrom(),
//                userData.getCountryTo(),
//                userData.getCityTo());
//        //userData.getLoadCategory().name());
//        stringBuilder.append(confirmationData);
//        return stringBuilder.toString();
//    }
//
//    private String constructRegistrationsMessage(UserData userData, List<User> registrations) {
//        String username = userData.getUsername();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
//        String mainMessage = registrations.isEmpty() ?
//                localMessageSource.getLocaleMessage("registrations.not_exist", username) :
//                localMessageSource.getLocaleMessage("registrations.exist", username);
//        StringBuilder stringBuilder = new StringBuilder(mainMessage).append("\n\n");
//        registrations.forEach(registration -> {
//            String record = localMessageSource.getLocaleMessage("registration.data",
//                    registration.getId().toString(),
//                    registration.getCountryFrom(),
//                    registration.getCityFrom(),
//                    registration.getDateFrom().format(dateTimeFormatter),
//                    registration.getCountryTo(),
//                    registration.getCityTo(),
//                    registration.getDateTo().format(dateTimeFormatter));
//            //registration.getCARGO().name());
//            stringBuilder.append(record).append("\n\n");
//        });
//        return stringBuilder.toString();
//    }

//    private String constructWrongInputMessage(UserData userData) {
//        String username = userData.getUsername();
//        StringBuilder stringBuilder = new StringBuilder();
//        String headerMessage = localMessageSource.getLocaleMessage("wrong_input", username);
//        stringBuilder.append(headerMessage).append(System.lineSeparator());
//        Arrays.asList(BotButton.Text.values()).
//                forEach(textCommand -> stringBuilder.append(textCommand.getText()).
//                        append(System.lineSeparator()));
//        return stringBuilder.toString();
//    }

}
