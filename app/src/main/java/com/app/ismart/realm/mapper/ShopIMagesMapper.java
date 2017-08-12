package com.app.ismart.realm.mapper;

import com.app.ismart.dto.ShopImagesDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableShopImages;

/**
 * Created by Faheem-Abbas on 5/19/2017.
 */

public class ShopIMagesMapper implements Mapper<TableShopImages, ShopImagesDto> {
    @Override
    public ShopImagesDto map(TableShopImages tableShopImages) {
        ShopImagesDto shopImagesDto=new ShopImagesDto();
        shopImagesDto.id=tableShopImages.getId();
        shopImagesDto.beforeImage=tableShopImages.getBeforeimage();
        shopImagesDto.afterImage=tableShopImages.getAfterImage();
        shopImagesDto.shopid=tableShopImages.getShopid();
        shopImagesDto.date=tableShopImages.getDate();
        shopImagesDto.visitid=tableShopImages.getVisitid();
        shopImagesDto.categoryName=tableShopImages.getCategoryName();
        return shopImagesDto;
    }
}
