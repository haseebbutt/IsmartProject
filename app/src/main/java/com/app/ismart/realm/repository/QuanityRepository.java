package com.app.ismart.realm.repository;

import com.app.ismart.dto.QuantityDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.QuantityMapper;
import com.app.ismart.realm.tables.TablesQuantity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class QuanityRepository implements IRepository<QuantityDto> {
    Realm realm;
    int nextID;

    public QuanityRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final QuantityDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TablesQuantity.class).maximumInt("id") + 1);
                final TablesQuantity newsRealm = realm.createObject(TablesQuantity.class);
                newsRealm.setId(nextID);
                newsRealm.setItemId(item.itemid);
                newsRealm.setQuantity(item.quantity);
                newsRealm.setShopId(item.shopid);
                newsRealm.setDate(item.date);
                newsRealm.setdisplay(item.display);

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
                    nextID = (int) (realm.where(TablesQuantity.class).maximumInt("id") + 1);
                    final TablesQuantity newsRealm = realm.createObject(TablesQuantity.class);
                    newsRealm.setId(nextID);
                    newsRealm.setItemId(item.itemid);
                    newsRealm.setQuantity(item.quantity);
                    newsRealm.setShopId(item.shopid);
                    newsRealm.setDate(item.date);
                    newsRealm.setdisplay(item.display);

                }
            });
        }
    }

    @Override
    public void update(QuantityDto item) {

        TablesQuantity toEdit = realm.where(TablesQuantity.class)
                .equalTo("itemId", item.itemid).equalTo("date",item.date).equalTo("shopId",item.shopid).equalTo("display",item.display).findFirst();
        realm.beginTransaction();
        toEdit.setItemId(item.itemid);
        toEdit.setQuantity(item.quantity);
        toEdit.setShopId(item.shopid);
        toEdit.setDate(item.date);
        toEdit.setdisplay(item.display);
        realm.commitTransaction();
    }

    @Override
    public void remove(QuantityDto item) {
        realm.beginTransaction();
        RealmResults<TablesQuantity> results = realm.where(TablesQuantity.class).equalTo("id", item.id).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablesQuantity> realmResults = realmSpecification.toRealmQuantitymResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    public void removespecfic(Specification specification,String date,String shopid) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablesQuantity> realmResults = realmSpecification.toRealmQuantitymResults(realm,date,shopid);
        realmResults.clear();
        realm.commitTransaction();
    }
    @Override
    public void removeAll() {
        RealmResults<TablesQuantity> results = realm.where(TablesQuantity.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    @Override
    public List<QuantityDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablesQuantity> realmResults = realmSpecification.toRealmQuantitymResults(realm);

        final List<QuantityDto> newses = new ArrayList<>();
        QuantityMapper mapper = new QuantityMapper();
        for (TablesQuantity item : realmResults) {
            QuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<QuantityDto> queryfordate(Specification specification,String date,String shopid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablesQuantity> realmResults = realmSpecification.toRealmQuantitymResults(realm,date,shopid);

        final List<QuantityDto> newses = new ArrayList<>();
        QuantityMapper mapper = new QuantityMapper();
        for (TablesQuantity item : realmResults) {
            QuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<QuantityDto> queryforitem(Specification specification,String date,String shopid,String itemid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablesQuantity> realmResults = realmSpecification.toRealmQuantitymResults(realm,date,shopid,itemid);

        final List<QuantityDto> newses = new ArrayList<>();
        QuantityMapper mapper = new QuantityMapper();
        for (TablesQuantity item : realmResults) {
            QuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<QuantityDto> queryforitem(Specification specification,String date,String shopid,String itemid,String display) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablesQuantity> realmResults = realmSpecification.toRealmQuantitymResults(realm,date,shopid,itemid,display);

        final List<QuantityDto> newses = new ArrayList<>();
        QuantityMapper mapper = new QuantityMapper();
        for (TablesQuantity item : realmResults) {
            QuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
}
