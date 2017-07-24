package com.app.ismart.realm.mapper;

import com.app.ismart.dto.CompetitorQuantityDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableComptitorQuantity;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class ComptitorQuantityMapper  implements Mapper<TableComptitorQuantity, CompetitorQuantityDto> {
    @Override
    public CompetitorQuantityDto map(TableComptitorQuantity tableComptitorQuantity) {
        CompetitorQuantityDto quantityDto=new CompetitorQuantityDto();
        quantityDto.timestamp=tableComptitorQuantity.getTimestamp();
        quantityDto.date=tableComptitorQuantity.getDate();
        quantityDto.location=tableComptitorQuantity.getLocation();
        quantityDto.displayId=tableComptitorQuantity.getDisplayId();
        quantityDto.products=tableComptitorQuantity.getProducts();
        quantityDto.shopId=tableComptitorQuantity.getShopId();
        quantityDto.quantities=tableComptitorQuantity.getQuantities();
        quantityDto.id=tableComptitorQuantity.getId();
        quantityDto.display=tableComptitorQuantity.getDisplay();
        quantityDto.visitId=tableComptitorQuantity.getVisitId();
        return quantityDto;
    }
}
