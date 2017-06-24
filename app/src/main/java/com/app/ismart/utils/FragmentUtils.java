package com.app.ismart.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by OmerKiani on 7/31/2016.
 */
public class FragmentUtils {
    private final AppCompatActivity activity;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public FragmentUtils(Activity act, Fragment fragment, int fragment_container) {
        this.activity = (AppCompatActivity) act;



        try {
            if (activity!=null) {
                fragmentManager = activity.getSupportFragmentManager();
                if(fragmentManager.getBackStackEntryCount()>0){
                  Fragment frag =   getCurrentFragment();


                    if( (fragment!=null)){
                        if(((frag!=null))&&(frag.getClass().equals(fragment.getClass()))){
//                            Toast.makeText(act, "Already Open", Toast.LENGTH_SHORT).show();

                        }else{
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(fragment_container, fragment , Integer.toString(getFragmentCount()));
                            fragmentTransaction.addToBackStack(Integer.toString(getFragmentCount()));
                            fragmentTransaction.commitAllowingStateLoss();
                        }
                    }

                }else{
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(fragment_container, fragment , Integer.toString(getFragmentCount()));
                    fragmentTransaction.addToBackStack(Integer.toString(getFragmentCount()));
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public FragmentUtils(Activity act, Fragment fragment, int fragment_container , boolean addTobackStack) {
        this.activity = (AppCompatActivity) act;



        try {
            if (activity!=null) {
                fragmentManager = activity.getSupportFragmentManager();
                if(fragmentManager.getBackStackEntryCount()>0){
                    Fragment frag =   getCurrentFragment();


                    if( (fragment!=null)){
                        if(((frag!=null))&&(frag.getClass().equals(fragment.getClass()))){
//                            Toast.makeText(act, "Already Open", Toast.LENGTH_SHORT).show();

                        }else{
                            fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(fragment_container, fragment , Integer.toString(getFragmentCount()));
                            if(addTobackStack){
                                fragmentTransaction.addToBackStack(Integer.toString(getFragmentCount()));
                            }
                            fragmentTransaction.commitAllowingStateLoss();
                        }
                    }

                }else{
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(fragment_container, fragment , Integer.toString(getFragmentCount()));
                    if(addTobackStack){
                        fragmentTransaction.addToBackStack(Integer.toString(getFragmentCount()));
                    }
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public FragmentUtils(AppCompatActivity activity){
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
    }

    public Fragment getFragmentAt(int index) {
        return fragmentManager.getBackStackEntryCount() > 0 ? fragmentManager.findFragmentByTag(Integer.toString(index)) : null;
    }

    protected Fragment getCurrentFragment() {
        return getFragmentAt(getFragmentCount() - 1);
    }


    protected int getFragmentCount() {
        return activity.getSupportFragmentManager().getBackStackEntryCount();
    }
}
