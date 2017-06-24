package com.app.ismart.realm.repository;

import com.app.ismart.dto.CompetotorImagesDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.CompetitorImagesMapper;
import com.app.ismart.realm.tables.TableComptitorImages;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class ComptitorImagesRepository implements IRepository<CompetotorImagesDto> {
    Realm realm;
    int nextID;

    public ComptitorImagesRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final CompetotorImagesDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableComptitorImages.class).maximumInt("id") + 1);
                final TableComptitorImages newsRealm = realm.createObject(TableComptitorImages.class);
                newsRealm.setId(nextID);
                newsRealm.setShopid(item.shopid);
                newsRealm.setBeforeImage(item.beforeImage);
                newsRealm.setAfterImage(item.afterImage);
                newsRealm.setDate(item.date);
                newsRealm.setDisplayid(item.displayid);


            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<CompetotorImagesDto> items) {
        for (final CompetotorImagesDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableComptitorImages.class).maximumInt("id") + 1);
                    final TableComptitorImages newsRealm = realm.createObject(TableComptitorImages.class);
                    newsRealm.setId(nextID);
                    newsRealm.setShopid(item.shopid);
                    newsRealm.setBeforeImage(item.beforeImage);
                    newsRealm.setAfterImage(item.afterImage);
                    newsRealm.setDate(item.date);
                    newsRealm.setDisplayid(item.displayid);

                }
            });
        }
    }

    @Override
    public void update(CompetotorImagesDto item) {

        TableComptitorImages toEdit = realm.where(TableComptitorImages.class)
                .equalTo("date", item.date).findFirst();
        realm.beginTransaction();
        toEdit.setShopid(item.shopid);
        toEdit.setBeforeImage(item.beforeImage);
        toEdit.setAfterImage(item.afterImage);
        toEdit.setDate(item.date);
        toEdit.setDisplayid(item.displayid);
        realm.commitTransaction();
    }

    @Override
    public void remove(CompetotorImagesDto item) {
        realm.beginTransaction();
        RealmResults<TableComptitorImages> results = realm.where(TableComptitorImages.class).equalTo("id", item.id).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorImages> realmResults = realmSpecification.toRealmComptitorImagesmResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }
    public void removespecfic(Specification specification,String date,String shopid,String displayid) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorImages> realmResults = realmSpecification.toRealmComptitorImagesmResults(realm,date,shopid,displayid);
        realmResults.clear();
        realm.commitTransaction();
    }
    @Override
    public void removeAll() {
        RealmResults<TableComptitorImages> results = realm.where(TableComptitorImages.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    @Override
    public List<CompetotorImagesDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorImages> realmResults = realmSpecification.toRealmComptitorImagesmResults(realm);

        final List<CompetotorImagesDto> newses = new ArrayList<>();
        CompetitorImagesMapper mapper = new CompetitorImagesMapper();
        for (TableComptitorImages item : realmResults) {
            CompetotorImagesDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<CompetotorImagesDto> queryForSpecficDate(Specification specification,String date,String shopid,String displayid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorImages> realmResults = realmSpecification.toRealmComptitorImagesmResults(realm,date,shopid,displayid);

        final List<CompetotorImagesDto> newses = new ArrayList<>();
        CompetitorImagesMapper mapper = new CompetitorImagesMapper();
        for (TableComptitorImages item : realmResults) {
            CompetotorImagesDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

}
