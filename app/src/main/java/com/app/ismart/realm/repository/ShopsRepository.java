package com.app.ismart.realm.repository;


import com.app.ismart.dto.ShopDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.ShopsMapper;
import com.app.ismart.realm.tables.TableShops;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by cielowigle on 24/03/2017.
 */

public class ShopsRepository implements IRepository<ShopDto> {
    Realm realm;
    int nextID = 0;

    public ShopsRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final ShopDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableShops.class).maximumInt("id") + 1);
                final TableShops newsRealm = realm.createObject(TableShops.class);
                newsRealm.setId(nextID);
                newsRealm.setShopId(""+item.getId());
                newsRealm.setName(item.getName());
                newsRealm.setAddress(item.getAddress());



            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<ShopDto> items) {
        for (final ShopDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableShops.class).maximumInt("id") + 1);
                    final TableShops newsRealm = realm.createObject(TableShops.class);
                    newsRealm.setId(nextID);
                    newsRealm.setShopId(""+item.getId());
                    newsRealm.setName(item.getName());
                    newsRealm.setAddress(item.getAddress());



                }
            });
        }

    }

    @Override
    public void update(ShopDto item) {

        TableShops toEdit = realm.where(TableShops.class)
                .equalTo("shopId", item.getId()).findFirst();
        realm.beginTransaction();
        toEdit.setShopId(""+item.getId());
        toEdit.setName(item.getName());
        toEdit.setAddress(item.getAddress());
        realm.commitTransaction();
    }

    @Override
    public void remove(ShopDto item) {
        realm.beginTransaction();
        RealmResults<TableShops> results = realm.where(TableShops.class).equalTo("shopId", item.getId()).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShops> realmResults = realmSpecification.toRealmShopsResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableShops> results = realm.where(TableShops.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    @Override
    public List<ShopDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShops> realmResults = realmSpecification.toRealmShopsResults(realm);

        final List<ShopDto> newses = new ArrayList<>();
        ShopsMapper mapper = new ShopsMapper();
        for (TableShops item : realmResults) {
            ShopDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

}
