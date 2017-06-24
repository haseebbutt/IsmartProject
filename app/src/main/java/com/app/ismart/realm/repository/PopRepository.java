package com.app.ismart.realm.repository;

import com.app.ismart.dto.ItemDto;
import com.app.ismart.dto.Pop;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.PopMapper;
import com.app.ismart.realm.mapper.ProductMapper;
import com.app.ismart.realm.tables.TablePops;
import com.app.ismart.realm.tables.TableProducts;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 5/28/2017.
 */

public class PopRepository implements IRepository<Pop> {
    Realm realm;
    int nextID = 0;

    public PopRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final Pop item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TablePops.class).maximumInt("id") + 1);
                final TablePops newsRealm = realm.createObject(TablePops.class);
                newsRealm.setId(nextID);
                newsRealm.setPopId(item.getId());
                newsRealm.setName(item.getName());
                newsRealm.setCreatedAt(item.getCreatedAt());
                newsRealm.setUpdatedAt(item.getUpdatedAt());



            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<Pop> items) {
        for (final Pop item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TablePops.class).maximumInt("id") + 1);
                    final TablePops newsRealm = realm.createObject(TablePops.class);
                    newsRealm.setId(nextID);
                    newsRealm.setPopId(item.getId());
                    newsRealm.setName(item.getName());
                    newsRealm.setCreatedAt(item.getCreatedAt());
                    newsRealm.setUpdatedAt(item.getUpdatedAt());



                }
            });

        }
    }

    @Override
    public void update(Pop item) {

        TablePops toEdit = realm.where(TablePops.class)
                .equalTo("popId", item.getId()).findFirst();
        realm.beginTransaction();
        toEdit.setPopId(item.getId());
        toEdit.setName(item.getName());
        toEdit.setCreatedAt(item.getCreatedAt());
        toEdit.setUpdatedAt(item.getUpdatedAt());
        realm.commitTransaction();
    }

    @Override
    public void remove(Pop item) {
        realm.beginTransaction();
        RealmResults<TablePops> results = realm.where(TablePops.class).equalTo("popId", item.getId()).findAll();
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
        RealmResults<TablePops> results = realm.where(TablePops.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    @Override
    public List<Pop> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TablePops> realmResults = realmSpecification.toRealmPopResults(realm);

        final List<Pop> newses = new ArrayList<>();
        PopMapper mapper = new PopMapper();
        for (TablePops item : realmResults) {
            Pop dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

}
