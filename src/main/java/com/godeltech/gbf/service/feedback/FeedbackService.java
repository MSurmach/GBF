package com.godeltech.gbf.service.feedback;

import com.godeltech.gbf.model.db.Feedback;

import java.util.List;

public interface FeedbackService {
    void save(Feedback feedBack);

    List<Feedback> findAllFeedbacks();

    void deleteFeedbackById(Long feedBackId);
}
