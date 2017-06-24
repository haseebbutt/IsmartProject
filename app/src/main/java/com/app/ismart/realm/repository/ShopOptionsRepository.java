package com.app.ismart.realm.repository;

import com.app.ismart.dto.ShopDto;
import com.app.ismart.dto.ShopOptionDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.ShopOptionsMapper;
import com.app.ismart.realm.mapper.ShopsMapper;
import com.app.ismart.realm.tables.TableShopOptions;
import com.app.ismart.realm.tables.TableShops;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 6/6/2017.
 */

public class ShopOptionsRepository implements IRepository<ShopOptionDto> {
    Realm realm;
    int nextID = 0;

    public ShopOptionsRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final ShopOptionDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableShopOptions.class).maximumInt("id") + 1);
                final TableShopOptions newsRealm = realm.createObject(TableShopOptions.class);
                newsRealm.setId(nextID);
                newsRealm.setComponentId(item.getId());
                newsRealm.setName(item.getName());



            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<ShopOptionDto> items) {
        for (final ShopOptionDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableShopOptions.class).maximumInt("id") + 1);
                    final TableShopOptions newsRealm = realm.createObject(TableShopOptions.class);
                    newsRealm.setId(nextID);
                    newsRealm.setComponentId(item.getId());
                    newsRealm.setName(item.getName());


                }
            });
        }

    }

    @Override
    public void update(ShopOptionDto item) {


    }

    @Override
    public void remove(ShopOptionDto item) {
        realm.beginTransaction();
        RealmResults<TableShopOptions> results = realm.where(TableShopOptions.class).equalTo("componentid", item.getId()).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopOptions> realmResults = realmSpecification.toRealmShopOptionResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableShopOptions> results = realm.where(TableShopOptions.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    @Override
    public List<ShopOptionDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopOptions> realmResults = realmSpecification.toRealmShopOptionResults(realm);

        final List<ShopOptionDto> newses = new ArrayList<>();
        ShopOptionsMapper mapper = new ShopOptionsMapper();
        for (TableShopOptions item : realmResults) {
            ShopOptionDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
}