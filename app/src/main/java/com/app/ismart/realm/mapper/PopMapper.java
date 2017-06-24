package com.app.ismart.realm.mapper;

import com.app.ismart.dto.Pop;
import com.app.ismart.realm.interfaces.Mapper;
import com.app.ismart.realm.tables.TablePops;

/**
 * Created by Faheem-Abbas on 5/28/2017.
 */

public class PopMapper implements Mapper<TablePops, Pop> {
    @Override
    public Pop map(TablePops tablePops) {
        Pop pop=new Pop();
        pop.setId(tablePops.getPopId());
        pop.setName(tablePops.getName());
        pop.setCreatedAt(tablePops.getCreatedAt());
        pop.setUpdatedAt(tablePops.getUpdatedAt());
        return pop;
    }
}
