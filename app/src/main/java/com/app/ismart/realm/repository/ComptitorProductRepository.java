package com.app.ismart.realm.repository;

import com.app.ismart.dto.CompetitorProductsDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.ComptitorProductsMapper;
import com.app.ismart.realm.tables.TableComptitorProducts;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 6/9/2017.
 */

public class ComptitorProductRepository implements IRepository<CompetitorProductsDto> {
    Realm realm;
    int nextID = 0;

    public ComptitorProductRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final CompetitorProductsDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableComptitorProducts.class).maximumInt("id") + 1);
                final TableComptitorProducts newsRealm = realm.createObject(TableComptitorProducts.class);
                newsRealm.setId(nextID);
                newsRealm.setTitle(item.getTitle());
                newsRealm.setCategory(item.getCategory());
                newsRealm.setProductid(""+item.getId());
                newsRealm.setDisplay(item.getDisplay());
                newsRealm.setCategoryId(item.getCategoryId()+"");
                newsRealm.setDisplayId(item.getDisplayId()+"");
            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<CompetitorProductsDto> items) {
        for (final CompetitorProductsDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableComptitorProducts.class).maximumInt("id") + 1);
                    final TableComptitorProducts newsRealm = realm.createObject(TableComptitorProducts.class);
                    newsRealm.setId(nextID);
                    newsRealm.setTitle(item.getTitle());
                    newsRealm.setCategory(item.getCategory());
                    newsRealm.setProductid(""+item.getId());
                    newsRealm.setDisplay(item.getDisplay());
                    newsRealm.setCategoryId(item.getCategoryId()+"");
                    newsRealm.setDisplayId(item.getDisplayId()+"");
                }
            });

        }
    }

    @Override
    public void update(CompetitorProductsDto item) {

        TableComptitorProducts toEdit = realm.where(TableComptitorProducts.class)
                .equalTo("productid", item.getId()).findFirst();
        realm.beginTransaction();
        toEdit.setTitle(item.getTitle());
        toEdit.setCategory(item.getCategory());
        toEdit.setProductid(""+item.getId());
        toEdit.setDisplay(item.getDisplay());
        toEdit.setCategoryId(item.getCategoryId()+"");
        toEdit.setDisplayId(item.getDisplayId()+"");
        realm.commitTransaction();
    }

    @Override
    public void remove(CompetitorProductsDto item) {
        realm.beginTransaction();
        RealmResults<TableComptitorProducts> results = realm.where(TableComptitorProducts.class).equalTo("productid", item.getId()).findAll();
        results.clear();
        realm.commitTransaction();

    }
    public void removeshopproduct(String shopid) {
        realm.beginTransaction();
        RealmResults<TableComptitorProducts> results = realm.where(TableComptitorProducts.class).equalTo("shopid", shopid).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorProducts> realmResults = realmSpecification.toRealmComptitorProductResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableComptitorProducts> results = realm.where(TableComptitorProducts.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }


    public List<CompetitorProductsDto> queryforshop(Specification specification, String shopid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorProducts> realmResults = realmSpecification.toRealmComptitorProductResults(realm,shopid);

        final List<CompetitorProductsDto> newses = new ArrayList<>();
        ComptitorProductsMapper mapper = new ComptitorProductsMapper();
        for (TableComptitorProducts item : realmResults) {
            CompetitorProductsDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    @Override
    public List<CompetitorProductsDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableComptitorProducts> realmResults = realmSpecification.toRealmComptitorProductResults(realm);
        final List<CompetitorProductsDto> newses = new ArrayList<>();
        ComptitorProductsMapper mapper = new ComptitorProductsMapper();
        for (TableComptitorProducts item : realmResults) {
            CompetitorProductsDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

}

