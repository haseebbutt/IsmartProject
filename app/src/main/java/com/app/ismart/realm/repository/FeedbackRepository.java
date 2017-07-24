package com.app.ismart.realm.repository;

import com.app.ismart.dto.FeedBackDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.FeedbackMapper;
import com.app.ismart.realm.tables.TableFeedback;
import com.app.ismart.realm.tables.TableShopStatus;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class FeedbackRepository implements IRepository<FeedBackDto> {
    Realm realm;
    int nextID = 0;

    public FeedbackRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final FeedBackDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableFeedback.class).maximumInt("id") + 1);
                final TableFeedback newsRealm = realm.createObject(TableFeedback.class);
                newsRealm.setId(nextID);
                newsRealm.setCategory(""+item.getCategory());
                newsRealm.setCategoryid(""+item.getCategory_id());
                newsRealm.setFeedback(item.getFeedback());
                newsRealm.setFeedbackid(""+item.getId());

            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<FeedBackDto> items) {
        for (final FeedBackDto item : items) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    nextID = (int) (realm.where(TableFeedback.class).maximumInt("id") + 1);
                    final TableFeedback newsRealm = realm.createObject(TableFeedback.class);
                    newsRealm.setId(nextID);
                    newsRealm.setCategory(""+item.getCategory());
                    newsRealm.setCategoryid(""+item.getCategory_id());
                    newsRealm.setFeedback(item.getFeedback());
                    newsRealm.setFeedbackid(""+item.getId());
                    newsRealm.setFeedback(item.getFeedback());
                    newsRealm.setFeedbackid(""+item.getId());

                }
            });
        }



    }

    @Override
    public void update(FeedBackDto item) {

        TableFeedback toEdit = realm.where(TableFeedback.class)
                .equalTo("feedbackid", item.getId()).findFirst();
        realm.beginTransaction();
        toEdit.setCategory(""+item.getCategory());
        toEdit.setCategoryid(""+item.getCategory_id());
        toEdit.setFeedback(item.getFeedback());
        toEdit.setFeedbackid(""+item.getId());
        toEdit.setFeedback(item.getFeedback());
        toEdit.setFeedbackid(""+item.getId());

        realm.commitTransaction();
    }

    @Override
    public void remove(FeedBackDto item) {
        realm.beginTransaction();
        RealmResults<TableFeedback> results = realm.where(TableFeedback.class).equalTo("feedbackid", item.getId()).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableFeedback> realmResults = realmSpecification.toRealmFeedbackResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableFeedback> results = realm.where(TableFeedback.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    public void removespecfic(Specification specification, String date, String shopid,String visitid) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableShopStatus> realmResults = realmSpecification.toRealmShopstatusResults(realm, date, shopid,visitid);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public List<FeedBackDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableFeedback> realmResults = realmSpecification.toRealmFeedbackResults(realm);

        final List<FeedBackDto> newses = new ArrayList<>();
        FeedbackMapper mapper = new FeedbackMapper();
        for (TableFeedback item : realmResults) {
            FeedBackDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

    public List<FeedBackDto> queryforfeedback(Specification specification, String feedbackid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableFeedback> realmResults = realmSpecification.toRealmFeedbackResults(realm, feedbackid);

        final List<FeedBackDto> newses = new ArrayList<>();
        FeedbackMapper mapper = new FeedbackMapper();
        for (TableFeedback item : realmResults) {
            FeedBackDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }


}