package com.app.ismart.realm.repository;

import com.app.ismart.dto.FeedbackSubmitDto;
import com.app.ismart.realm.interfaces.IRepository;
import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.interfaces.Specification;
import com.app.ismart.realm.mapper.FeedbackSubmitMapper;
import com.app.ismart.realm.tables.TableFeedbackSubmit;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Faheem-Abbas on 5/25/2017.
 */

public class FeedbackSubmitRepository implements IRepository<FeedbackSubmitDto> {
    Realm realm;
    int nextID = 0;

    public FeedbackSubmitRepository(Realm realm) {
        this.realm = realm;
    }

    @Override
    public long add(final FeedbackSubmitDto item) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                nextID = (int) (realm.where(TableFeedbackSubmit.class).maximumInt("id") + 1);
                final TableFeedbackSubmit newsRealm = realm.createObject(TableFeedbackSubmit.class);
                newsRealm.setId(nextID);
                newsRealm.setFeedback(""+item.response);
                newsRealm.setFeedbackid(item.feedbackId);
                newsRealm.setShopid(""+item.shopId);
                newsRealm.setLocation(item.location);
                newsRealm.setDate(item.date);
                newsRealm.setVisitid(item.visitId);

            }
        });

        return nextID;
    }

    @Override
    public void add(Iterable<FeedbackSubmitDto> items) {





    }

    @Override
    public void update(FeedbackSubmitDto item) {

        TableFeedbackSubmit toEdit = realm.where(TableFeedbackSubmit.class)
                .equalTo("feedbackid", item.feedbackId) .equalTo("shopid", item.shopId).equalTo("visitid", item.visitId).findFirst();
        realm.beginTransaction();
        toEdit.setFeedback(""+item.response);
        toEdit.setFeedbackid(item.feedbackId);
        toEdit.setShopid(""+item.shopId);
        toEdit.setLocation(item.location);
        toEdit.setDate(item.date);
        toEdit.setVisitid(item.visitId);
        realm.commitTransaction();
    }

    @Override
    public void remove(FeedbackSubmitDto item) {
        realm.beginTransaction();
        RealmResults<TableFeedbackSubmit> results = realm.where(TableFeedbackSubmit.class).equalTo("shopid", item.shopId).equalTo("feedbackid", item.feedbackId).findAll();
        results.clear();
        realm.commitTransaction();

    }

    @Override
    public void remove(Specification specification) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableFeedbackSubmit> realmResults = realmSpecification.toRealmFeedbackSubmitResults(realm);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public void removeAll() {
        RealmResults<TableFeedbackSubmit> results = realm.where(TableFeedbackSubmit.class).findAll();

// All changes to data must happen in a transaction
        realm.beginTransaction();

// Delete all matches
        results.clear();

        realm.commitTransaction();
    }

    public void removespecfic(Specification specification, String shopid,String feedbackid,String visitid) {
        realm.beginTransaction();
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableFeedbackSubmit> realmResults = realmSpecification.toRealmFeedbackSubmitResults(realm, shopid,feedbackid,visitid);
        realmResults.clear();
        realm.commitTransaction();
    }

    @Override
    public List<FeedbackSubmitDto> query(Specification specification) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableFeedbackSubmit> realmResults = realmSpecification.toRealmFeedbackSubmitResults(realm);

        final List<FeedbackSubmitDto> newses = new ArrayList<>();
        FeedbackSubmitMapper mapper = new FeedbackSubmitMapper();
        for (TableFeedbackSubmit item : realmResults) {
            FeedbackSubmitDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

    public List<FeedbackSubmitDto> queryforfeedback(Specification specification, String shopid,String feedbackid, String visitid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableFeedbackSubmit> realmResults = realmSpecification.toRealmFeedbackSubmitResults(realm, shopid,feedbackid,visitid);

        final List<FeedbackSubmitDto> newses = new ArrayList<>();
        FeedbackSubmitMapper mapper = new FeedbackSubmitMapper();
        for (TableFeedbackSubmit item : realmResults) {
            FeedbackSubmitDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }
    public List<FeedbackSubmitDto> queryforfeedbackbydate(Specification specification,String date, String shopid,String visitid) {
        final RealmSpecification realmSpecification = (RealmSpecification) specification;
        final RealmResults<TableFeedbackSubmit> realmResults = realmSpecification.toRealmFeedbackbydateSubmitResults(realm,date, shopid,visitid);

        final List<FeedbackSubmitDto> newses = new ArrayList<>();
        FeedbackSubmitMapper mapper = new FeedbackSubmitMapper();
        for (TableFeedbackSubmit item : realmResults) {
            FeedbackSubmitDto dto = mapper.map(item);
            newses.add(dto);
        }


        return newses;

    }

}