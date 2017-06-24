package com.app.ismart.realm.repository;

import com.app.ismart.dto.QuantityDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.BackdoorQuantityMapper;
import com.app.ismart.realm.tables.TableBackdoorQuantity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 6/4/2017.
 */

public class BackdoorQuantityRepository implements IRepository<QuantityDto> {
    Realm realm;
    int nextID;

    public BackdoorQuantityRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final QuantityDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableBackdoorQuantity.class).maximumInt("id") + 1);
                final TableBackdoorQuantity newsRealm = realm.createObject(TableBackdoorQuantity.class);
                newsRealm.setId(nextID);
                newsRealm.setItemId(item.itemid);
                newsRealm.setQuantity(item.quantity);
                newsRealm.setShopId(item.shopid);
                newsRealm.setDate(item.date);
                newsRealm.setTimestamp(item.timestamp);
                newsRealm.setLocation(item.location);

            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<QuantityDto> items) {
        for (final QuantityDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableBackdoorQuantity.class).maximumInt("id") + 1);
                    final TableBackdoorQuantity newsRealm = realm.createObject(TableBackdoorQuantity.class);
                    newsRealm.setId(nextID);
                    newsRealm.setItemId(item.itemid);
                    newsRealm.setQuantity(item.quantity);
                    newsRealm.setShopId(item.shopid);
                    newsRealm.setDate(item.date);
                    newsRealm.setTimestamp(item.timestamp);
                    newsRealm.setLocation(item.location);


                }
            });
        }
    }

    @Override
    public void update(QuantityDto item) {

        TableBackdoorQuantity toEdit = realm.where(TableBackdoorQuantity.class)
                .equalTo("itemId", item.itemid).equalTo("date", item.date).equalTo("shopId", item.shopid).findFirst();
        realm.beginTransaction();
        toEdit.setItemId(item.itemid);
        toEdit.setQuantity(item.quantity);
        toEdit.setShopId(item.shopid);
        toEdit.setDate(item.date);
        toEdit.setTimestamp(item.timestamp);
        toEdit.setLocation(item.location);
        realm.commitTransaction();
    }

    @Override
    public void remove(QuantityDto item) {
        realm.beginTransaction();
        RealmResults<TableBackdoorQuantity> results = realm.where(TableBackdoorQuantity.class).equalTo("id", item.id).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableBackdoorQuantity> realmResults = realmSpecification.toRealmBackdoorQuantitymResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    public void removespecfic(Specification specification, String date, String shopid) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableBackdoorQuantity> realmResults = realmSpecification.toRealmBackdoorQuantitymResults(realm, date, shopid);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableBackdoorQuantity> results = realm.where(TableBackdoorQuantity.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    public void removeshopAll( String shopid) {
        RealmResults<TableBackdoorQuantity> results = realm.where(TableBackdoorQuantity.class).equalTo("shopId", shopid).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }
    @Override
    public List<QuantityDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableBackdoorQuantity> realmResults = realmSpecification.toRealmBackdoorQuantitymResults(realm);

        final List<QuantityDto> newses = new ArrayList<>();
        BackdoorQuantityMapper mapper = new BackdoorQuantityMapper();
        for (TableBackdoorQuantity item : realmResults) {
            QuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

    public List<QuantityDto> queryfordate(Specification specification, String date, String shopid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableBackdoorQuantity> realmResults = realmSpecification.toRealmBackdoorQuantitymResults(realm, date, shopid);

        final List<QuantityDto> newses = new ArrayList<>();
        BackdoorQuantityMapper mapper = new BackdoorQuantityMapper();
        for (TableBackdoorQuantity item : realmResults) {
            QuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<QuantityDto> queryforshop(Specification specification, String shopid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableBackdoorQuantity> realmResults = realmSpecification.toRealmBackdoorQuantitymResults(realm, shopid);

        final List<QuantityDto> newses = new ArrayList<>();
        BackdoorQuantityMapper mapper = new BackdoorQuantityMapper();
        for (TableBackdoorQuantity item : realmResults) {
            QuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<QuantityDto> queryforitem(Specification specification, String date, String shopid, String itemid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableBackdoorQuantity> realmResults = realmSpecification.toRealmBackdoorQuantitymResults(realm, date, shopid, itemid);

        final List<QuantityDto> newses = new ArrayList<>();
        BackdoorQuantityMapper mapper = new BackdoorQuantityMapper();
        for (TableBackdoorQuantity item : realmResults) {
            QuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
}
