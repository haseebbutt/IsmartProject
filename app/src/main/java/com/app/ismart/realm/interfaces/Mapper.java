package com.app.ismart.realm.interfaces;

/**
 * Created by cielowigle on 21/02/2017.
 */

public interface Mapper<From, To> {
    To map(From from);
}
