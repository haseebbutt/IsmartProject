package com.app.ismart.realm.repository;

import com.app.ismart.dto.ExpiredItemDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.ExpiredMapper;
import com.app.ismart.realm.tables.TableExpiredItems;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 6/3/2017.
 */

public class ExpiredItemRepository implements IRepository<ExpiredItemDto> {
    Realm realm;
    int nextID;

    public ExpiredItemRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final ExpiredItemDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableExpiredItems.class).maximumInt("id") + 1);
                final TableExpiredItems newsRealm = realm.createObject(TableExpiredItems.class);
                newsRealm.setId(nextID);
                newsRealm.setItemId(item.itemid);
                newsRealm.setExpired(item.expired);
                newsRealm.setShopId(item.shopid);
                newsRealm.setDate(item.date);
                newsRealm.setNearexpired(item.nearexpired);
                newsRealm.setLocation(item.location);
                newsRealm.setTimestamp(item.timestamp);
                newsRealm.setVisitId(item.visitid);


            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<ExpiredItemDto> items) {

    }

    @Override
    public void update(ExpiredItemDto item) {

        TableExpiredItems toEdit = realm.where(TableExpiredItems.class)
                .equalTo("itemId", item.itemid).equalTo("date",item.date).equalTo("shopId",item.shopid).equalTo("visitId",item.visitid).findFirst();
        realm.beginTransaction();
        toEdit.setItemId(item.itemid);
        toEdit.setExpired(item.expired);
        toEdit.setShopId(item.shopid);
        toEdit.setDate(item.date);
        toEdit.setNearexpired(item.nearexpired);
        toEdit.setLocation(item.location);
        toEdit.setTimestamp(item.timestamp);
        toEdit.setVisitId(item.visitid);
        realm.commitTransaction();
    }

    @Override
    public void remove(ExpiredItemDto item) {
        realm.beginTransaction();
        RealmResults<TableExpiredItems> results = realm.where(TableExpiredItems.class).equalTo("shopId", item.shopid).equalTo("date", item.date).equalTo("itemId", item.itemid).equalTo("visitId", item.visitid).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableExpiredItems> realmResults = realmSpecification.toRealmExpiredmResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }


    @Override
    public void removeAll() {
        RealmResults<TableExpiredItems> results = realm.where(TableExpiredItems.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    @Override
    public List<ExpiredItemDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableExpiredItems> realmResults = realmSpecification.toRealmExpiredmResults(realm);

        final List<ExpiredItemDto> newses = new ArrayList<>();
        ExpiredMapper mapper = new ExpiredMapper();
        for (TableExpiredItems item : realmResults) {
            ExpiredItemDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

    public List<ExpiredItemDto> queryforitem(Specification specification,String date,String shopid,String itemid,String visitid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableExpiredItems> realmResults = realmSpecification.toRealmExpiredmResults(realm,date,shopid,itemid,visitid);

        final List<ExpiredItemDto> newses = new ArrayList<>();
        ExpiredMapper mapper = new ExpiredMapper();
        for (TableExpiredItems item : realmResults) {
            ExpiredItemDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<ExpiredItemDto> queryforitem(Specification specification,String date,String shopid,String visitid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableExpiredItems> realmResults = realmSpecification.toRealmExpiredmResults(realm,date,shopid,visitid);

        final List<ExpiredItemDto> newses = new ArrayList<>();
        ExpiredMapper mapper = new ExpiredMapper();
        for (TableExpiredItems item : realmResults) {
            ExpiredItemDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
}
