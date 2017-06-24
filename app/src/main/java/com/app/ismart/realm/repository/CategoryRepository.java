package com.app.ismart.realm.repository;

import com.app.ismart.dto.CategoryDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.CategoryMapper;
import com.app.ismart.realm.tables.TableCategory;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 5/14/2017.
 */

public class CategoryRepository implements IRepository<CategoryDto> {
    Realm realm;
    int nextID = 0;

    public CategoryRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final CategoryDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableCategory.class).maximumInt("id") + 1);
                final TableCategory newsRealm = realm.createObject(TableCategory.class);
                newsRealm.setId(nextID);
                newsRealm.setCategoryName(item.getName());
                newsRealm.setDisplay(item.getDisplay());



            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<CategoryDto> items) {
        for (final CategoryDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableCategory.class).maximumInt("id") + 1);
                    final TableCategory newsRealm = realm.createObject(TableCategory.class);
                    newsRealm.setId(nextID);
                    newsRealm.setCategoryName(item.getName());
                    newsRealm.setDisplay(item.getDisplay());



                }
            });
        }
    }

    @Override
    public void update(CategoryDto item) {

        TableCategory toEdit = realm.where(TableCategory.class)
                .equalTo("CategoryName", item.getName()).findFirst();
        realm.beginTransaction();
        toEdit.setCategoryName(item.getName());
        toEdit.setId(item.getId());
        toEdit.setDisplay(item.getDisplay());
        realm.commitTransaction();
    }

    @Override
    public void remove(CategoryDto item) {
        realm.beginTransaction();
        RealmResults<TableCategory> results = realm.where(TableCategory.class).equalTo("CategoryName", item.getName()).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableCategory> realmResults = realmSpecification.toRealmCategoruResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableCategory> results = realm.where(TableCategory.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    @Override
    public List<CategoryDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableCategory> realmResults = realmSpecification.toRealmCategoruResults(realm);

        final List<CategoryDto> newses = new ArrayList<>();
        CategoryMapper mapper = new CategoryMapper();
        for (TableCategory item : realmResults) {
            CategoryDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

}
