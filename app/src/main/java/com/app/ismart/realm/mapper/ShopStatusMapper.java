package com.app.ismart.realm.mapper;

import com.app.ismart.dto.ShopStatusDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableShopStatus;

/**
 * Created by Faheem-Abbas on 5/23/2017.
 */

public class ShopStatusMapper implements Mapper<TableShopStatus, ShopStatusDto> {
    @Override
    public ShopStatusDto map(TableShopStatus tableShopStatus) {
        ShopStatusDto shopStatusDto=new ShopStatusDto();
        shopStatusDto.id=tableShopStatus.getId();
        shopStatusDto.shopId=tableShopStatus.getShopId();
        shopStatusDto.status=tableShopStatus.getStatus();
        shopStatusDto.reason=tableShopStatus.getReason();
        shopStatusDto.photo=tableShopStatus.getPhoto();
        shopStatusDto.date=tableShopStatus.getDate();
        shopStatusDto.location=tableShopStatus.getLocation();
        shopStatusDto.visitId=tableShopStatus.getVisitId();
        shopStatusDto.startTime=tableShopStatus.getStartTime();
        shopStatusDto.endTime=tableShopStatus.getEndTime();
        return shopStatusDto;
    }
}
