package com.app.ismart.realm.repository;

import com.app.ismart.dto.PopSubmitDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.PopSubmitMapper;
import com.app.ismart.realm.tables.TablePopSubmit;
import com.app.ismart.realm.tables.TablePops;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 5/29/2017.
 */

public class PopSubmitRepository implements IRepository<PopSubmitDto> {
    Realm realm;
    int nextID = 0;

    public PopSubmitRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final PopSubmitDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TablePopSubmit.class).maximumInt("id") + 1);
                final TablePopSubmit newsRealm = realm.createObject(TablePopSubmit.class);
                newsRealm.setId(nextID);
                newsRealm.setPopid(item.popid);
                newsRealm.setQuantity(item.quantity);
                newsRealm.setDate(item.date);
                newsRealm.setPhoto(item.photo);
                newsRealm.setTimestamp(item.timestamp);
                newsRealm.setShopid(item.shopid);
                newsRealm.setLocation(item.location);


            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<PopSubmitDto> items) {



    }

    @Override
    public void update(PopSubmitDto item) {

        TablePopSubmit toEdit = realm.where(TablePopSubmit.class)
                .equalTo("popid", item.popid) .equalTo("shopid", item.shopid).findFirst();
        realm.beginTransaction();
        toEdit.setId(nextID);
        toEdit.setPopid(item.popid);
        toEdit.setQuantity(item.quantity);
        toEdit.setDate(item.date);
        toEdit.setPhoto(item.photo);
        toEdit.setTimestamp(item.timestamp);
        toEdit.setShopid(item.shopid);
        toEdit.setLocation(item.location);
        realm.commitTransaction();
    }

    @Override
    public void remove(PopSubmitDto item) {
        realm.beginTransaction();
        RealmResults<TablePopSubmit> results = realm.where(TablePopSubmit.class).equalTo("popid", item.popid) .equalTo("shopid", item.shopid).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablePops> realmResults = realmSpecification.toRealmPopResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TablePopSubmit> results = realm.where(TablePopSubmit.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }


    public List<PopSubmitDto> query(Specification specification,String popid,String shopid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablePopSubmit> realmResults = realmSpecification.toRealmPopSubmitResults(realm,popid,shopid);

        final List<PopSubmitDto> newses = new ArrayList<>();
        PopSubmitMapper mapper = new PopSubmitMapper();
        for (TablePopSubmit item : realmResults) {
            PopSubmitDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<PopSubmitDto> queryfordate(Specification specification,String date,String shopid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablePopSubmit> realmResults = realmSpecification.toRealmPopSubmitbydateResults(realm,date,shopid);

        final List<PopSubmitDto> newses = new ArrayList<>();
        PopSubmitMapper mapper = new PopSubmitMapper();
        for (TablePopSubmit item : realmResults) {
            PopSubmitDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    @Override
    public List<PopSubmitDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablePopSubmit> realmResults = realmSpecification.toRealmPopSubmitResults(realm);

        final List<PopSubmitDto> newses = new ArrayList<>();
        PopSubmitMapper mapper = new PopSubmitMapper();
        for (TablePopSubmit item : realmResults) {
            PopSubmitDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

}
