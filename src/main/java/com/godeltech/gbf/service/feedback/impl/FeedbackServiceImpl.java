package com.godeltech.gbf.service.feedback.impl;

import com.godeltech.gbf.model.db.Feedback;
import com.godeltech.gbf.repository.FeedbackRepository;
import com.godeltech.gbf.service.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Override
    @Transactional
    public void save(Feedback feedback) {
        log.info("Save new feedback with user id : {} ", feedback.getUserId());
        feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findAllFeedbacks() {
        log.info("Find all feedbacks");
        return feedbackRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteFeedbackById(Long feedBackId) {
        log.info("Delete feedback wi id : {}", feedBackId);
        feedbackRepository.deleteById(feedBackId);
    }
}
