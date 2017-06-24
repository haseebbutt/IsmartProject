package com.app.ismart.realm.repository;

import com.app.ismart.dto.ItemDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.ProductMapper;
import com.app.ismart.realm.tables.TableProducts;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class ProductRepository  implements IRepository<ItemDto> {
    Realm realm;
    int nextID = 0;

    public ProductRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final ItemDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableProducts.class).maximumInt("id") + 1);
                final TableProducts newsRealm = realm.createObject(TableProducts.class);
                newsRealm.setId(nextID);
                newsRealm.setTitle(item.getTitle());
                newsRealm.setCategory(item.getCategory());
                newsRealm.setItemId(item.getId());
                newsRealm.setDisplay(item.getDisplay());
                newsRealm.setShopid(item.getShopid());
                newsRealm.setImageurl(item.getImageurl());
            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<ItemDto> items) {
        for (final ItemDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableProducts.class).maximumInt("id") + 1);
                    final TableProducts newsRealm = realm.createObject(TableProducts.class);
                    newsRealm.setId(nextID);
                    newsRealm.setTitle(item.getTitle());
                    newsRealm.setCategory(item.getCategory());
                    newsRealm.setItemId(item.getId());
                    newsRealm.setDisplay(item.getDisplay());
                    newsRealm.setShopid(item.getShopid());
                    newsRealm.setImageurl(item.getImageurl());
                }
            });

        }
    }

    @Override
    public void update(ItemDto item) {

        TableProducts toEdit = realm.where(TableProducts.class)
                .equalTo("itemId", item.getId()).findFirst();
        realm.beginTransaction();
        toEdit.setTitle(item.getTitle());
        toEdit.setCategory(item.getCategory());
        toEdit.setItemId(item.getId());
        toEdit.setDisplay(item.getDisplay());
        toEdit.setImageurl(item.getImageurl());
        toEdit.setShopid(item.getShopid());
        realm.commitTransaction();
    }

    @Override
    public void remove(ItemDto item) {
        realm.beginTransaction();
        RealmResults<TableProducts> results = realm.where(TableProducts.class).equalTo("itemId", item.getId()).findAll();
        results.clear();
        realm.commitTransaction();

    }
    public void removeshopproduct(String shopid) {
        realm.beginTransaction();
        RealmResults<TableProducts> results = realm.where(TableProducts.class).equalTo("shopid", shopid).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableProducts> realmResults = realmSpecification.toRealmProductResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableProducts> results = realm.where(TableProducts.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }


    public List<ItemDto> queryforshop(Specification specification,String shopid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableProducts> realmResults = realmSpecification.toRealmProductResults(realm,shopid);

        final List<ItemDto> newses = new ArrayList<>();
        ProductMapper mapper = new ProductMapper();
        for (TableProducts item : realmResults) {
            ItemDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    @Override
    public List<ItemDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableProducts> realmResults = realmSpecification.toRealmProductResults(realm);

        final List<ItemDto> newses = new ArrayList<>();
        ProductMapper mapper = new ProductMapper();
        for (TableProducts item : realmResults) {
            ItemDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

}
