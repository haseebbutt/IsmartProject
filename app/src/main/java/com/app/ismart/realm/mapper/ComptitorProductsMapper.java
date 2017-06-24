package com.app.ismart.realm.mapper;

import com.app.ismart.dto.CompetitorProductsDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableComptitorProducts;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class ComptitorProductsMapper implements Mapper<TableComptitorProducts, CompetitorProductsDto> {
    @Override
    public CompetitorProductsDto map(TableComptitorProducts tableComptitorProducts) {
        CompetitorProductsDto productsDto=new CompetitorProductsDto();
        productsDto.setId(Integer.parseInt(tableComptitorProducts.getProductid()));
        productsDto.setCategory(tableComptitorProducts.getCategory());
        productsDto.setTitle(tableComptitorProducts.getTitle());
        productsDto.setCategoryId(Integer.parseInt(tableComptitorProducts.getCategoryId()));
        productsDto.setDisplay(tableComptitorProducts.getDisplay());
        productsDto.setDisplayId(Integer.parseInt(tableComptitorProducts.getDisplayId()));
        return productsDto;
    }
}
