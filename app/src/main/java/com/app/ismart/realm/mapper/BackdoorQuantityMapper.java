package com.app.ismart.realm.mapper;

import com.app.ismart.dto.QuantityDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableBackdoorQuantity;

/**
 * Created by Faheem-Abbas on 6/4/2017.
 */

public class BackdoorQuantityMapper implements Mapper<TableBackdoorQuantity, QuantityDto> {
    @Override
    public QuantityDto map(TableBackdoorQuantity tablesQuantity) {
        QuantityDto dto = new QuantityDto();
        dto.id = tablesQuantity.getId();
        dto.itemid = tablesQuantity.getItemId();
        dto.quantity = tablesQuantity.getQuantity();
        dto.shopid = tablesQuantity.getShopId();
        dto.date = tablesQuantity.getDate();
        dto.timestamp=tablesQuantity.getTimestamp();
        dto.location=tablesQuantity.getLocation();
        dto.visitid=tablesQuantity.getVisitId();
        return dto;
    }
}