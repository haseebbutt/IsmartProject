package com.app.ismart.realm.repository;

import com.app.ismart.dto.ShopImagesDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.ShopIMagesMapper;
import com.app.ismart.realm.tables.TableShopImages;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 5/19/2017.
 */

public class ShopImagesRepository implements IRepository<ShopImagesDto> {
    Realm realm;
    int nextID;

    public ShopImagesRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final ShopImagesDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableShopImages.class).maximumInt("id") + 1);
                final TableShopImages newsRealm = realm.createObject(TableShopImages.class);
                newsRealm.setId(nextID);
                newsRealm.setShopid(item.shopid);
                newsRealm.setBeforeimage(item.beforeImage);
                newsRealm.setAfterImage(item.afterImage);
                newsRealm.setDate(item.date);
                newsRealm.setVisitid(item.visitid);
                newsRealm.setCategoryName(item.categoryName);

            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<ShopImagesDto> items) {
        for (final ShopImagesDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableShopImages.class).maximumInt("id") + 1);
                    final TableShopImages newsRealm = realm.createObject(TableShopImages.class);
                    newsRealm.setId(nextID);
                    newsRealm.setShopid(item.shopid);
                    newsRealm.setBeforeimage(item.beforeImage);
                    newsRealm.setAfterImage(item.afterImage);
                    newsRealm.setDate(item.date);
                    newsRealm.setVisitid(item.visitid);
                    newsRealm.setCategoryName(item.categoryName);

                }
            });
        }
    }

    @Override
    public void update(ShopImagesDto item) {

        TableShopImages toEdit = realm.where(TableShopImages.class)
                .equalTo("shopid", item.shopid).equalTo("visitid", item.visitid).equalTo("categoryName", item.categoryName).findFirst();
        realm.beginTransaction();
        toEdit.setShopid(item.shopid);
        toEdit.setBeforeimage(item.beforeImage);
        toEdit.setAfterImage(item.afterImage);
        toEdit.setDate(item.date);
        toEdit.setVisitid(item.visitid);
        toEdit.setCategoryName(item.categoryName);
        realm.commitTransaction();
    }

    @Override
    public void remove(ShopImagesDto item) {
        realm.beginTransaction();
        RealmResults<TableShopImages> results = realm.where(TableShopImages.class).equalTo("id", item.id).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopImages> realmResults = realmSpecification.toRealmShopImagesmResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }
    public void removespecfic(Specification specification,String date,String shopid,String visitid,String dispName) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopImages> realmResults = realmSpecification.toRealmShopImagesmResults(realm,date,shopid,visitid,dispName);
        realmResults.clear();
        realm.commitTransaction();
    }
    @Override
    public void removeAll() {
        RealmResults<TableShopImages> results = realm.where(TableShopImages.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    @Override
    public List<ShopImagesDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopImages> realmResults = realmSpecification.toRealmShopImagesmResults(realm);

        final List<ShopImagesDto> newses = new ArrayList<>();
        ShopIMagesMapper mapper = new ShopIMagesMapper();
        for (TableShopImages item : realmResults) {
            ShopImagesDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<ShopImagesDto> queryForSpecficDate(Specification specification,String date,String shopid,String visitid,String dispName) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopImages> realmResults = realmSpecification.toRealmShopImagesmResults(realm,date,shopid,visitid,dispName);

        final List<ShopImagesDto> newses = new ArrayList<>();
        ShopIMagesMapper mapper = new ShopIMagesMapper();
        for (TableShopImages item : realmResults) {
            ShopImagesDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

}
