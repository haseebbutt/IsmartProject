package com.app.ismart.realm.interfaces;


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
 * Created by cielowigle on 21/02/2017.
 */

public interface RealmSpecification extends Specification {
    RealmResults<TableShops> toRealmShopsResults(Realm realm);
    RealmResults<TableShopOptions> toRealmShopOptionResults(Realm realm);
    RealmResults<TableCategory> toRealmCategoruResults(Realm realm);
    RealmResults<TableProducts> toRealmProductResults(Realm realm);
    RealmResults<TableComptitorProducts> toRealmComptitorProductResults(Realm realm);
    RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm);
    RealmResults<TableShopImages> toRealmShopImagesmResults(Realm realm);
    RealmResults<TableComptitorImages> toRealmComptitorImagesmResults(Realm realm);
    RealmResults<TableShopStatus> toRealmShopstatusResults(Realm realm);
    RealmResults<TableShopImages> toRealmShopImagesmResults(Realm realm, String date,String shopid);

    RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm, String date,String shopid);
    RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm, String date, String shopid, String itemid);
    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm);
    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm, String date, String shopid);
    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm, String date, String shopid, String itemid);
    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitydisplaymResults(Realm realm, String date, String shopid, String display);
    RealmResults<TableShopStatus> toRealmShopstatusResults(Realm realm, String date,String shopid);
    RealmResults<TableFeedback> toRealmFeedbackResults(Realm realm);
    RealmResults<TableFeedback> toRealmFeedbackResults(Realm realm, String feedbackid);
    RealmResults<TableFeedbackSubmit> toRealmFeedbackSubmitResults(Realm realm);
    RealmResults<TableFeedbackSubmit> toRealmFeedbackSubmitResults(Realm realm, String shopid,String feedbackid);
    RealmResults<TableFeedbackSubmit> toRealmFeedbackbydateSubmitResults(Realm realm, String shopid,String date);
    RealmResults<TablePops> toRealmPopResults(Realm realm);
    RealmResults<TablePopSubmit> toRealmPopSubmitResults(Realm realm);
    RealmResults<TablePopSubmit> toRealmPopSubmitResults(Realm realm, String popid, String shopid);
    RealmResults<TablePopSubmit> toRealmPopSubmitbydateResults(Realm realm, String date, String shopid);
    RealmResults<TableProducts> toRealmProductResults(Realm realm, String shopid);
    RealmResults<TableComptitorProducts> toRealmComptitorProductResults(Realm realm, String shopid);
    RealmResults<TableExpiredItems> toRealmExpiredmResults(Realm realm);
    RealmResults<TableExpiredItems> toRealmExpiredmResults(Realm realm, String date, String shopid, String itemid);

    RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm);
    RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm, String date,String shopid);
    RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm, String date, String shopid, String itemid);

    RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm, String shopid);

    RealmResults<TableComptitorImages> toRealmComptitorImagesmResults(Realm realm, String date, String shopid,String displayid);



    RealmResults<TableExpiredItems> toRealmExpiredmResults(Realm realm, String date, String shopid);

    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitydisplaymResults(Realm realm, String date, String shopid);

    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm, String date, String shopid, String itemid, String display);

    RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm, String date, String shopid, String itemid, String display);
}
