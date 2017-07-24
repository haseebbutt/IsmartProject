package com.app.ismart.realm.repository;

import com.app.ismart.dto.CompetitorQuantityDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.ComptitorQuantityMapper;
import com.app.ismart.realm.tables.TableComptitorQuantity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class ComptitorQuantityRepository implements IRepository<CompetitorQuantityDto> {
    Realm realm;
    int nextID;

    public ComptitorQuantityRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final CompetitorQuantityDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableComptitorQuantity.class).maximumInt("id") + 1);
                final TableComptitorQuantity newsRealm = realm.createObject(TableComptitorQuantity.class);
                newsRealm.setId(nextID);
                newsRealm.setProducts(item.products);
                newsRealm.setQuantities(item.quantities);
                newsRealm.setShopId(item.shopId);
                newsRealm.setDate(item.date);
                newsRealm.setDisplayId(item.displayId);
                newsRealm.setTimestamp(item.timestamp);
                newsRealm.setLocation(item.location);
                newsRealm.setDisplay(item.display);
                newsRealm.setVisitId(item.visitId);
            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<CompetitorQuantityDto> items) {
        for (final CompetitorQuantityDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableComptitorQuantity.class).maximumInt("id") + 1);
                    final TableComptitorQuantity newsRealm = realm.createObject(TableComptitorQuantity.class);
                    newsRealm.setId(nextID);
                    newsRealm.setProducts(item.products);
                    newsRealm.setQuantities(item.quantities);
                    newsRealm.setShopId(item.shopId);
                    newsRealm.setDate(item.date);
                    newsRealm.setDisplayId(item.displayId);
                    newsRealm.setTimestamp(item.timestamp);
                    newsRealm.setLocation(item.location);
                    newsRealm.setDisplay(item.display);
                    newsRealm.setVisitId(item.visitId);

                }
            });
        }
    }

    @Override
    public void update(CompetitorQuantityDto item) {

        TableComptitorQuantity toEdit = realm.where(TableComptitorQuantity.class)
                .equalTo("products", item.products).equalTo("date", item.date).equalTo("shopId", item.shopId).equalTo("visitId", item.visitId).equalTo("display", item.display).findFirst();
        realm.beginTransaction();
        toEdit.setProducts(item.products);
        toEdit.setQuantities(item.quantities);
        toEdit.setShopId(item.shopId);
        toEdit.setDate(item.date);
        toEdit.setDisplayId(item.displayId);
        toEdit.setTimestamp(item.timestamp);
        toEdit.setLocation(item.location);
        toEdit.setDisplay(item.display);
        toEdit.setVisitId(item.visitId);
        realm.commitTransaction();
    }

    @Override
    public void remove(CompetitorQuantityDto item) {
        realm.beginTransaction();
        RealmResults<TableComptitorQuantity> results = realm.where(TableComptitorQuantity.class).equalTo("products", item.products).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorQuantity> realmResults = realmSpecification.toRealmComptitorQuantitymResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    public void removespecfic(Specification specification, String date, String displayid,String shopid,String visitid) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorQuantity> realmResults = realmSpecification.toRealmComptitorQuantitydisplaymResults(realm, date, shopid,displayid,visitid);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableComptitorQuantity> results = realm.where(TableComptitorQuantity.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    @Override
    public List<CompetitorQuantityDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorQuantity> realmResults = realmSpecification.toRealmComptitorQuantitymResults(realm);

        final List<CompetitorQuantityDto> newses = new ArrayList<>();
        ComptitorQuantityMapper mapper = new ComptitorQuantityMapper();
        for (TableComptitorQuantity item : realmResults) {
            CompetitorQuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

    public List<CompetitorQuantityDto> queryfordate(Specification specification, String date, String shopid,String display,String visitid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorQuantity> realmResults = realmSpecification.toRealmComptitorQuantitydisplaymResults(realm, date, shopid,display,visitid);

        final List<CompetitorQuantityDto> newses = new ArrayList<>();
        ComptitorQuantityMapper mapper = new ComptitorQuantityMapper();
        for (TableComptitorQuantity item : realmResults) {
            CompetitorQuantityDto dto = mapper.map(item);
            newses.add(dto);
        }



        return newses;

    }
    public List<CompetitorQuantityDto> queryfordate1(Specification specification, String date, String shopid,String visitid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorQuantity> realmResults = realmSpecification.toRealmComptitorQuantitydisplaymResults1(realm, date, shopid,visitid);

        final List<CompetitorQuantityDto> newses = new ArrayList<>();
        ComptitorQuantityMapper mapper = new ComptitorQuantityMapper();
        for (TableComptitorQuantity item : realmResults) {
            CompetitorQuantityDto dto = mapper.map(item);
            newses.add(dto);
        }



        return newses;

    }

    public List<CompetitorQuantityDto> queryforitem(Specification specification, String date, String shopid, String itemid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorQuantity> realmResults = realmSpecification.toRealmComptitorQuantitymResults(realm, date, shopid, itemid);

        final List<CompetitorQuantityDto> newses = new ArrayList<>();
        ComptitorQuantityMapper mapper = new ComptitorQuantityMapper();
        for (TableComptitorQuantity item : realmResults) {
            CompetitorQuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<CompetitorQuantityDto> queryforitem(Specification specification, String date, String shopid, String itemid,String display,String visitid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorQuantity> realmResults = realmSpecification.toRealmComptitorQuantitymResults(realm, date, shopid, itemid,display,visitid);

        final List<CompetitorQuantityDto> newses = new ArrayList<>();
        ComptitorQuantityMapper mapper = new ComptitorQuantityMapper();
        for (TableComptitorQuantity item : realmResults) {
            CompetitorQuantityDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
}

