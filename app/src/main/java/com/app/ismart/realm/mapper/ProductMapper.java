package com.app.ismart.realm.mapper;

import com.app.ismart.dto.ItemDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableProducts;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class ProductMapper implements Mapper<TableProducts, ItemDto> {
    @Override
    public ItemDto map(TableProducts tableProducts) {
        ItemDto dto=new ItemDto();
        dto.setId(tableProducts.getItemId());
        dto.setCategory(tableProducts.getCategory());
        dto.setTitle(tableProducts.getTitle());
        dto.setDisplay(tableProducts.getDisplay());
        dto.setShopid(tableProducts.getShopid());
        dto.setImageurl(tableProducts.getImageurl());
        return dto;
    }
}
