package com.godeltech.gbf.service.feedback;

import com.godeltech.gbf.model.db.FeedBack;
import org.springframework.data.domain.Page;

public interface FeedbackService {
    void save(FeedBack feedBack);

    Page<FeedBack> findAllFeedBacks(int pageNumber);

    void deleteFeedbackById(Long feedBackId);
}
