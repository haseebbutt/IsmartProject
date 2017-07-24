package com.app.ismart.realm.mapper;

import com.app.ismart.dto.CompetotorImagesDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableComptitorImages;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class CompetitorImagesMapper  implements Mapper<TableComptitorImages, CompetotorImagesDto> {
    @Override
    public CompetotorImagesDto map(TableComptitorImages tableComptitorImages) {
        CompetotorImagesDto imagesDto=new CompetotorImagesDto();
        imagesDto.afterImage=tableComptitorImages.getAfterImage();
        imagesDto.beforeImage=tableComptitorImages.getBeforeImage();
        imagesDto.date=tableComptitorImages.getDate();
        imagesDto.displayid=tableComptitorImages.getDisplayid();
        imagesDto.id=tableComptitorImages.getId();
        imagesDto.shopid=tableComptitorImages.getShopid();
        imagesDto.visitid=tableComptitorImages.getVisitid();
        return imagesDto;
    }
}
