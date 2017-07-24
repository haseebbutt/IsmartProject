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
        int shopid = Integer.parseInt(tableShops.getShopId());
        shopDto.setId(shopid);
        shopDto.setName(tableShops.getName());
        shopDto.setAddress(tableShops.getAddress());

        int visitid = Integer.parseInt(tableShops.getVisitId());
        shopDto.setVisitId(visitid);
        shopDto.setDay(tableShops.getDay());

        return shopDto;
    }
}
