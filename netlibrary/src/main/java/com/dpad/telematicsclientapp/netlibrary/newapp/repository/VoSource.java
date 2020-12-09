package com.dpad.telematicsclientapp.netlibrary.newapp.repository;



import com.dpad.telematicsclientapp.netlibrary.entity.CuscResult;

import java.util.SortedMap;

import rx.Observable;

/**
 * Created by vigss on 2018/3/26.
 */

public interface VoSource<T> {

    Observable<CuscResult<T>> TService(SortedMap<String, String> sortedMap);



}
