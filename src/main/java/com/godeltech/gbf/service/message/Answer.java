package com.godeltech.gbf.service.message;

import com.godeltech.gbf.model.UserData;

import java.util.List;

public interface Answer {
    String getBotMessage(UserData userData, List<UserData>...users);
}
