package com.app.ismart.realm.mapper;

import com.app.ismart.dto.FeedbackSubmitDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableFeedbackSubmit;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class FeedbackSubmitMapper implements Mapper<TableFeedbackSubmit, FeedbackSubmitDto> {
    @Override
    public FeedbackSubmitDto map(TableFeedbackSubmit tableFeedbackSubmit) {
        FeedbackSubmitDto feedbackSubmitDto=new FeedbackSubmitDto();
        feedbackSubmitDto.feedbackId=tableFeedbackSubmit.getFeedbackid();
        feedbackSubmitDto.location=tableFeedbackSubmit.getLocation();
        feedbackSubmitDto.shopId=tableFeedbackSubmit.getShopid();
        feedbackSubmitDto.response=tableFeedbackSubmit.getFeedback();
        feedbackSubmitDto.date=tableFeedbackSubmit.getDate();
        feedbackSubmitDto.visitId=tableFeedbackSubmit.getVisitid();
        return feedbackSubmitDto;
    }
}
