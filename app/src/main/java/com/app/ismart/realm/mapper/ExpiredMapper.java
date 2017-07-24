package com.app.ismart.realm.mapper;

import com.app.ismart.dto.ExpiredItemDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableExpiredItems;

/**
 * Created by Faheem-Abbas on 6/3/2017.
 */

public class ExpiredMapper implements Mapper<TableExpiredItems, ExpiredItemDto> {
    @Override
    public ExpiredItemDto map(TableExpiredItems tableExpiredItems) {
        ExpiredItemDto expiredItemDto=new ExpiredItemDto();
        expiredItemDto.shopid=tableExpiredItems.getShopId();
        expiredItemDto.itemid=tableExpiredItems.getItemId();
        expiredItemDto.expired=tableExpiredItems.getExpired();
        expiredItemDto.nearexpired=tableExpiredItems.getNearexpired();
        expiredItemDto.location=tableExpiredItems.getLocation();
        expiredItemDto.timestamp=tableExpiredItems.getTimestamp();
        expiredItemDto.date=tableExpiredItems.getDate();
        expiredItemDto.visitid=tableExpiredItems.getVisitId();
        return expiredItemDto;
    }
}
