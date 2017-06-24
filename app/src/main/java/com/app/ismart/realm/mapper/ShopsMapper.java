package com.app.ismart.realm.mapper;

import com.app.ismart.dto.ShopDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableShops;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class ShopsMapper implements Mapper<TableShops, ShopDto> {
    @Override
    public ShopDto map(TableShops tableShops) {
        ShopDto shopDto=new ShopDto();
        shopDto.setAddress(tableShops.getAddress());
        shopDto.setId(tableShops.getId());
        shopDto.setName(tableShops.getName());
        return shopDto;
    }
}
