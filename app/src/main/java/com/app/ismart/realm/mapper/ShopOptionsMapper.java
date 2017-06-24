package com.app.ismart.realm.mapper;

import com.app.ismart.dto.ShopOptionDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableShopOptions;

/**
 * Created by Faheem-Abbas on 6/6/2017.
 */

public class ShopOptionsMapper   implements Mapper<TableShopOptions, ShopOptionDto> {
    @Override
    public ShopOptionDto map(TableShopOptions tableShopOptions) {
        ShopOptionDto shopOptionDto=new ShopOptionDto();
        shopOptionDto.setId(tableShopOptions.getComponentId());
        shopOptionDto.setName(tableShopOptions.getName());
        return shopOptionDto;
    }
}
