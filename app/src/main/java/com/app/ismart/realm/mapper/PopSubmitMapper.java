package com.app.ismart.realm.mapper;

import com.app.ismart.dto.PopSubmitDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TablePopSubmit;

/**
 * Created by Faheem-Abbas on 5/29/2017.
 */

public class PopSubmitMapper  implements Mapper<TablePopSubmit, PopSubmitDto> {
    @Override
    public PopSubmitDto map(TablePopSubmit tablePopSubmit) {
        PopSubmitDto popSubmitDto=new PopSubmitDto();
        popSubmitDto.popid=tablePopSubmit.getPopid();
        popSubmitDto.date=tablePopSubmit.getDate();
        popSubmitDto.photo=tablePopSubmit.getPhoto();
        popSubmitDto.quantity=tablePopSubmit.getQuantity();
        popSubmitDto.timestamp=tablePopSubmit.getTimestamp();
        popSubmitDto.shopid=tablePopSubmit.getShopid();
        popSubmitDto.location=tablePopSubmit.getLocation();
        popSubmitDto.visitid=tablePopSubmit.getVisitid();
        return popSubmitDto;
    }
}
