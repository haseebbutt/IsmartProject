package com.app.ismart.realm.mapper;

import com.app.ismart.dto.FeedBackDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableFeedback;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class FeedbackMapper implements Mapper<TableFeedback, FeedBackDto> {
    @Override
    public FeedBackDto map(TableFeedback tableFeedback) {
        FeedBackDto feedBackDto = new FeedBackDto();
        feedBackDto.setId(tableFeedback.getId());
        feedBackDto.setCategory(tableFeedback.getCategory());
        feedBackDto.setFeedback(tableFeedback.getFeedback());
        feedBackDto.setCategory_id(Integer.parseInt(tableFeedback.getCategoryid()));
        feedBackDto.setFeedbackid(tableFeedback.getFeedbackid());
        return feedBackDto;
    }
}