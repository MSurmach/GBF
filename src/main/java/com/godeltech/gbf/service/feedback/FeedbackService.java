package com.godeltech.gbf.service.feedback;

import com.godeltech.gbf.model.db.Feedback;
import org.springframework.data.domain.Page;

public interface FeedbackService {
    void save(Feedback feedBack);

    Page<Feedback> findAllFeedbacks(int pageNumber);

    void deleteFeedbackById(Long feedBackId);
}
