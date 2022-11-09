package com.godeltech.gbf.service.feedback.impl;

import com.godeltech.gbf.model.db.FeedBack;
import com.godeltech.gbf.service.feedback.FeedbackService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class FeedBackServiceImpl implements FeedbackService {
    @Override
    public void save(FeedBack feedBack) {

    }

    @Override
    public Page<FeedBack> findAllFeedBacks(int pageNumber) {
        return null;
    }

    @Override
    public void deleteFeedbackById(Long feedBackId) {

    }
}
