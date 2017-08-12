package com.app.ismart.realm.mapper;

import com.app.ismart.dto.QuantityDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TablesQuantity;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class QuantityMapper  implements Mapper<TablesQuantity, QuantityDto> {
    @Override
    public QuantityDto map(TablesQuantity tablesQuantity) {
        QuantityDto dto=new QuantityDto();
        dto.id=tablesQuantity.getId();
        dto.itemid=tablesQuantity.getItemId();
        dto.quantity=tablesQuantity.getQuantity();
        dto.shopid=tablesQuantity.getShopId();
        dto.date=tablesQuantity.getDate();
        dto.display=tablesQuantity.getDisplay();
        dto.visitid=tablesQuantity.getVisitId();
        dto.facing=tablesQuantity.getFacing();
        return dto;
    }
}
