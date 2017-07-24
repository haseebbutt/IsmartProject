package com.app.ismart.realm.interfaces;


import com.app.ismart.realm.tables.TableBackdoorQuantity;
import com.app.ismart.realm.tables.TableCategory;
import com.app.ismart.realm.tables.TableCheckedComponents;
import com.app.ismart.realm.tables.TableComptitorImages;
import com.app.ismart.realm.tables.TableComptitorProducts;
import com.app.ismart.realm.tables.TableComptitorQuantity;
import com.app.ismart.realm.tables.TableExpiredItems;
import com.app.ismart.realm.tables.TableFeedback;
import com.app.ismart.realm.tables.TableFeedbackAnswers;
import com.app.ismart.realm.tables.TableFeedbackSubmit;
import com.app.ismart.realm.tables.TableImei;
import com.app.ismart.realm.tables.TablePopSubmit;
import com.app.ismart.realm.tables.TablePops;
import com.app.ismart.realm.tables.TableProducts;
import com.app.ismart.realm.tables.TableShopImages;
import com.app.ismart.realm.tables.TableShopOptions;
import com.app.ismart.realm.tables.TableShopStatus;
import com.app.ismart.realm.tables.TableShops;
import com.app.ismart.realm.tables.TableVisits;
import com.app.ismart.realm.tables.TablesQuantity;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by cielowigle on 21/02/2017.
 */

public interface RealmSpecification extends Specification {
    RealmResults<TableShops> toRealmShopsResults(Realm realm);
    RealmResults<TableShopOptions> toRealmShopOptionResults(Realm realm);
    RealmResults<TableCheckedComponents> toRealmCheckedComponentsResults(Realm realm);
    RealmResults<TableVisits> toRealmVisitsResultsDate(Realm realm,int schedid,int complete);
    RealmResults<TableVisits> toRealmVisitsResults(Realm realm);
    RealmResults<TableVisits> toRealmVisitsQuery(Realm realm,int sched);
    RealmResults<TableVisits> toRealmVisitsQueryTwo(Realm realm,int schedid);
    RealmResults<TableCategory> toRealmCategoruResults(Realm realm);
    RealmResults<TableProducts> toRealmProductResults(Realm realm);
    RealmResults<TableComptitorProducts> toRealmComptitorProductResults(Realm realm);
    RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm);
    RealmResults<TableShopImages> toRealmShopImagesmResults(Realm realm);
    RealmResults<TableComptitorImages> toRealmComptitorImagesmResults(Realm realm);
    RealmResults<TableShopStatus> toRealmShopstatusResults(Realm realm);
    RealmResults<TableShopImages> toRealmShopImagesmResults(Realm realm, String date,String shopid,String visitid,String dispName);
    RealmResults<TableShops>  toRealmShopidResults(Realm realm,String shopid);
    RealmResults<TableImei> toRealmImeiResults(Realm realm);
    RealmResults<TableShops> toRealmDateResults(Realm realm,String date);
    RealmResults<TableFeedbackAnswers> toRealmFeedbackResultsAnswers(Realm realm, String feedbackid);
    RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm, String date,String shopid);
    RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm, String date, String shopid, String itemid);
    RealmResults<TablesQuantity> toRealmQuantitymResultsQuantityVisits(Realm realm, String date, String shopid, String item, String display,String visitid);
    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm);
    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm, String date, String shopid);
    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm, String date, String shopid, String itemid);
    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitydisplaymResults(Realm realm, String date, String shopid, String display,String visitid);
    RealmResults<TableShopStatus> toRealmShopstatusResults(Realm realm, String date,String shopid,String visitid);
    RealmResults<TableFeedback> toRealmFeedbackResults(Realm realm);
    RealmResults<TableFeedback> toRealmFeedbackResults(Realm realm, String feedbackid);
    RealmResults<TableFeedbackSubmit> toRealmFeedbackSubmitResults(Realm realm);
    RealmResults<TableFeedbackSubmit> toRealmFeedbackSubmitResults(Realm realm, String shopid,String feedbackid,String visitid);
    RealmResults<TableFeedbackSubmit> toRealmFeedbackbydateSubmitResults(Realm realm, String date,String shopid,String visitid);
    RealmResults<TablesQuantity> toRealmQuantitymResultsVisits(Realm realm, String date, String shopid, String visitid);
    RealmResults<TablePops> toRealmPopResults(Realm realm);
    RealmResults<TablePopSubmit> toRealmPopSubmitResults(Realm realm);
    RealmResults<TablePopSubmit> toRealmPopSubmitResults(Realm realm, String popid, String shopid,String visitid);
    RealmResults<TablePopSubmit> toRealmPopSubmitbydateResults(Realm realm, String date, String shopid,String visitid);
    RealmResults<TableProducts> toRealmProductResults(Realm realm, String shopid);
    RealmResults<TableComptitorProducts> toRealmComptitorProductResults(Realm realm, String shopid);
    RealmResults<TableExpiredItems> toRealmExpiredmResults(Realm realm);
    RealmResults<TableExpiredItems> toRealmExpiredmResults(Realm realm, String date, String shopid, String itemid,String visitid);

    RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm);
    RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm, String date,String shopid,String visitid);
    RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm, String date, String shopid, String itemid,String visitid);

    RealmResults<TableBackdoorQuantity> toRealmBackdoorQuantitymResults(Realm realm, String shopid,String visitid);

    RealmResults<TableComptitorImages> toRealmComptitorImagesmResults(Realm realm, String date, String shopid,String displayid,String visitid);



    RealmResults<TableExpiredItems> toRealmExpiredmResults(Realm realm, String date, String shopid,String visitid);

    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitydisplaymResults(Realm realm, String date, String shopid);
    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitydisplaymResults1(Realm realm,String date, String shopid,String visitid);

    RealmResults<TableComptitorQuantity> toRealmComptitorQuantitymResults(Realm realm, String date, String shopid, String itemid, String display,String visitid);

    RealmResults<TablesQuantity> toRealmQuantitymResults(Realm realm, String date, String shopid, String itemid, String display);
    RealmResults<TablesQuantity> toRealmQuantitymVisits(Realm realm,String date,String shopid, String visitid);
}
