package com.godeltech.gbf.service.answer;

import com.godeltech.gbf.model.UserData;

import java.util.List;

public interface Answer {
    String getAnswer(UserData userData, List<UserData>...users);
}
