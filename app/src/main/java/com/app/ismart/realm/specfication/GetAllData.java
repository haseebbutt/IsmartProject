package com.app.ismart.realm.specfication;


import com.app.ismart.realm.interfaces.RealmSpecification;
import com.app.ismart.realm.tables.TableBackdoorQuantity;
import com.app.ismart.realm.tables.TableCategory;
import com.app.ismart.realm.tables.TableComptitorImages;
import com.app.ismart.realm.tables.TableComptitorProducts;
import com.app.ismart.realm.tables.TableComptitorQuantity;
import com.app.ismart.realm.tables.TableExpiredItems;
import com.app.ismart.realm.tables.TableFeedback;
import com.app.ismart.realm.tables.TableFeedbackSubmit;
import com.app.ismart.realm.tables.TablePopSubmit;
import com.app.ismart.realm.tables.TablePops;
import com.app.ismart.realm.tables.TableProducts;
import com.app.ismart.realm.tables.TableShopImages;
import com.app.ismart.realm.tables.TableShopOptions;
import com.app.ismart.realm.tables.TableShopStatus;
import com.app.ismart.realm.tables.TableShops;
import com.app.ismart.realm.tables.TablesQuantity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by cielowigle on 24/03/2017.
 */

public class GetAllData implements RealmSpecification {


    @Override
    public RealmResults<TableShops> toRealmShopsResults(Realm realm) {
        return realm.where(TableShops.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TableShopOptions> toRealmShopOptionResults(Realm realm) {
        return realm.where(TableShopOptions.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TableCategory> toRealmCategoruResults(Realm realm) {
        return realm.where(TableCategory.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TableProducts> toRealmProductResults(Realm realm) {
        return realm.where(TableProducts.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TableComptitorProducts> toRealmComptitorProductResults(Realm realm) {
        return realm.where(TableComptitorProducts.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm) {
        return realm.where(TablesQuantity.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TableShopImages> toRealmShopImagesmResults(Realm realm) {
        return realm.where(TableShopImages.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TableComptitorImages> toRealmComptitorImagesmResults(Realm realm) {
        return realm.where(TableComptitorImages.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TableShopStatus> toRealmShopstatusResults(Realm realm) {
        return realm.where(TableShopStatus.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TableShopImages> toRealmShopImagesmResults(Realm realm, String date, String shopid) {

        return realm.where(TableShopImages.class)
                .equalTo("date", date)
                .equalTo("shopid", shopid)
                .findAll();


    }

    @Override
    public RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm, String date, String shopid) {
        return realm.where(TablesQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .findAll();
    }

    @Override
    public RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm, String date, String shopid, String itemid) {
        return realm.where(TablesQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .equalTo("itemId", itemid)
                .findAll();
    }

    @Override
    public RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm) {
        return realm.where(TableComptitorQuantity.class)

                .findAll();
    }

    @Override
    public RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm, String date, String shopid) {
        return realm.where(TableComptitorQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .findAll();
    }

    @Override
    public RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm, String date, String shopid, String itemid) {
        return realm.where(TableComptitorQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .equalTo("products", itemid)
                .findAll();
    }

    @Override
    public RealmResults<TableComptitorQuantity> toRealmComptitorQuantitydisplaymResults(Realm realm, String date, String shopid, String display) {
        return realm.where(TableComptitorQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .equalTo("displayId", display)
                .findAll();
    }

    @Override
    public RealmResults<TableShopStatus> toRealmShopstatusResults(Realm realm, String date, String shopid) {
        return realm.where(TableShopStatus.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .findAll();
    }

    @Override
    public RealmResults<TableFeedback> toRealmFeedbackResults(Realm realm) {
        return realm.where(TableFeedback.class)

                .findAll();
    }

    @Override
    public RealmResults<TableFeedback> toRealmFeedbackResults(Realm realm, String feedbackid) {
        return realm.where(TableFeedback.class)
                .equalTo("feedbackid", feedbackid)

                .findAll();
    }

    @Override
    public RealmResults<TableFeedbackSubmit> toRealmFeedbackSubmitResults(Realm realm) {
        return realm.where(TableFeedbackSubmit.class)


                .findAll();
    }

    @Override
    public RealmResults<TableFeedbackSubmit> toRealmFeedbackSubmitResults(Realm realm, String shopid, String feedbackid) {
        return realm.where(TableFeedbackSubmit.class)
                .equalTo("shopid", shopid)
                .equalTo("feedbackid", feedbackid)
                .findAll();
    }

    @Override
    public RealmResults<TableFeedbackSubmit> toRealmFeedbackbydateSubmitResults(Realm realm, String shopid, String date) {
        return realm.where(TableFeedbackSubmit.class)
                .equalTo("shopid", shopid)
                .equalTo("date", date)
                .findAll();
    }

    @Override
    public RealmResults<TablePops> toRealmPopResults(Realm realm) {
        return realm.where(TablePops.class)


                .findAll();
    }

    @Override
    public RealmResults<TablePopSubmit> toRealmPopSubmitResults(Realm realm) {
        return realm.where(TablePopSubmit.class)


                .findAll();
    }

    @Override
    public RealmResults<TablePopSubmit> toRealmPopSubmitResults(Realm realm, String popid, String shopid) {
        return realm.where(TablePopSubmit.class)
                .equalTo("popid", popid)
                .equalTo("shopid", shopid)
                .findAll();
    }

    @Override
    public RealmResults<TablePopSubmit> toRealmPopSubmitbydateResults(Realm realm, String date, String shopid) {
        return realm.where(TablePopSubmit.class)
                .equalTo("date", date)
                .equalTo("shopid", shopid)
                .findAll();
    }

    @Override
    public RealmResults<TableProducts> toRealmProductResults(Realm realm, String shopid) {
        return realm.where(TableProducts.class)
                .equalTo("shopid", shopid)
                .findAll();
    }

    @Override
    public RealmResults<TableComptitorProducts> toRealmComptitorProductResults(Realm realm, String shopid) {
        return realm.where(TableComptitorProducts.class)
                .equalTo("shopid", shopid)
                .findAll();
    }

    @Override
    public RealmResults<TableExpiredItems> toRealmExpiredmResults(Realm realm) {
        return realm.where(TableExpiredItems.class)
                .findAll();
    }

    @Override
    public RealmResults<TableExpiredItems> toRealmExpiredmResults(Realm realm, String date, String shopid, String itemid) {
        return realm.where(TableExpiredItems.class)
                .equalTo("shopId", shopid)
                .equalTo("date", date)
                .equalTo("itemId", itemid)
                .findAll();
    }

    @Override
    public RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm) {
        return realm.where(TableBackdoorQuantity.class)
                // .equalTo("userId",userId)
                .findAll();
    }

    @Override
    public RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm, String date, String shopid) {
        return realm.where(TableBackdoorQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .findAll();
    }

    @Override
    public RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm, String date, String shopid, String itemid) {
        return realm.where(TableBackdoorQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .equalTo("itemId", itemid)
                .findAll();
    }

    @Override
    public RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm, String shopid) {
        return realm.where(TableBackdoorQuantity.class)

                .equalTo("shopId", shopid)

                .findAll();
    }

    @Override
    public RealmResults<TableComptitorImages> toRealmComptitorImagesmResults(Realm realm, String date, String shopid,String displayid) {
        return realm.where(TableComptitorImages.class)
                .equalTo("date", date)
                .equalTo("shopid", shopid)
                .equalTo("displayid", displayid)
                .findAll();
    }



    @Override
    public RealmResults<TableExpiredItems> toRealmExpiredmResults(Realm realm, String date, String shopid) {
        return realm.where(TableExpiredItems.class)
                .equalTo("shopId", shopid)
                .equalTo("date", date)
                .findAll();
    }

    @Override
    public RealmResults<TableComptitorQuantity> toRealmComptitorQuantitydisplaymResults(Realm realm, String date, String shopid) {
        return realm.where(TableComptitorQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .findAll();
    }

    @Override
    public RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm, String date, String shopid, String itemid, String display) {
        return realm.where(TableComptitorQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .equalTo("products", itemid)
                .equalTo("display", display)
                .findAll();
    }

    @Override
    public RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm, String date, String shopid, String itemid, String display) {
        return realm.where(TablesQuantity.class)
                .equalTo("date", date)
                .equalTo("shopId", shopid)
                .equalTo("itemId", itemid)
                .equalTo("display", display)
                .findAll();
    }


}
