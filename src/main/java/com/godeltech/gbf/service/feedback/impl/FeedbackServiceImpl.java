package com.godeltech.gbf.service.feedback.impl;

import com.godeltech.gbf.model.db.Feedback;
import com.godeltech.gbf.repository.FeedbackRepository;
import com.godeltech.gbf.service.feedback.FeedbackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Feedback> findAllFeedbacks(int pageNumber) {
        log.info("Find all feedbacks");
        Pageable pageable = PageRequest.of(pageNumber, 1);
        return feedbackRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void deleteFeedbackById(Long feedBackId) {
        log.info("Delete feedback wi id : {}", feedBackId);
        feedbackRepository.deleteById(feedBackId);
    }
}
