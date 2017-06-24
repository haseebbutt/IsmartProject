package com.app.ismart.realm.repository;

import com.app.ismart.dto.ShopStatusDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.ShopStatusMapper;
import com.app.ismart.realm.tables.TableShopStatus;
import com.app.ismart.realm.tables.TableShops;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 5/23/2017.
 */

public class ShopStatusRepository implements IRepository<ShopStatusDto> {
    Realm realm;
    int nextID = 0;

    public ShopStatusRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final ShopStatusDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableShopStatus.class).maximumInt("id") + 1);
                final TableShopStatus newsRealm = realm.createObject(TableShopStatus.class);
                newsRealm.setId(nextID);
                newsRealm.setShopId("" + item.shopId);
                newsRealm.setStatus(item.status);
                newsRealm.setDate(item.date);
                newsRealm.setReason(item.reason);
                newsRealm.setPhoto(item.photo);
                newsRealm.setLocation(item.location);

            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<ShopStatusDto> items) {


    }

    @Override
    public void update(ShopStatusDto item) {

        TableShopStatus toEdit = realm.where(TableShopStatus.class)
                .equalTo("shopId", item.shopId).equalTo("date",item.date).findFirst();
        realm.beginTransaction();
        toEdit.setShopId("" + item.shopId);
        toEdit.setStatus(item.status);
        toEdit.setDate(item.date);
        toEdit.setReason(item.reason);
        toEdit.setPhoto(item.photo);
        toEdit.setLocation(item.location);
        realm.commitTransaction();
    }

    @Override
    public void remove(ShopStatusDto item) {
        realm.beginTransaction();
        RealmResults<TableShopStatus> results = realm.where(TableShopStatus.class).equalTo("shopId", item.id).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopStatus> realmResults = realmSpecification.toRealmShopstatusResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableShopStatus> results = realm.where(TableShopStatus.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }
    public void removespecfic(Specification specification,String date,String shopid) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopStatus> realmResults = realmSpecification.toRealmShopstatusResults(realm,date,shopid);
        realmResults.clear();
        realm.commitTransaction();
    }
    @Override
    public List<ShopStatusDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopStatus> realmResults = realmSpecification.toRealmShopstatusResults(realm);

        final List<ShopStatusDto> newses = new ArrayList<>();
        ShopStatusMapper mapper = new ShopStatusMapper();
        for (TableShopStatus item : realmResults) {
            ShopStatusDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<ShopStatusDto> queryfordate(Specification specification, String date, String shopid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopStatus> realmResults = realmSpecification.toRealmShopstatusResults(realm,date,shopid);

        final List<ShopStatusDto> newses = new ArrayList<>();
        ShopStatusMapper mapper = new ShopStatusMapper();
        for (TableShopStatus item : realmResults) {
            ShopStatusDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<ShopStatusDto> queryforitem(Specification specification,String date,String shopid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopStatus> realmResults = realmSpecification.toRealmShopstatusResults(realm,date,shopid);

        final List<ShopStatusDto> newses = new ArrayList<>();
        ShopStatusMapper mapper = new ShopStatusMapper();
        for (TableShopStatus item : realmResults) {
            ShopStatusDto dto = mapper.map(item);
            newses.add(dto);
        }
        return newses;

    }
}