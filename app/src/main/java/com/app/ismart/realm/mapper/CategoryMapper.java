package com.app.ismart.realm.mapper;

import com.app.ismart.dto.CategoryDto;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TableCategory;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class CategoryMapper  implements Mapper<TableCategory, CategoryDto> {
    @Override
    public CategoryDto map(TableCategory tableCategory) {
        CategoryDto dto=new CategoryDto();
        dto.setmName(tableCategory.getCategoryName());
        dto.setId(tableCategory.getId());
        dto.setDisplay(tableCategory.getDisplay());
        return dto;
    }
}
