package com.example.skillmanager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataUtil {
    public static <T> T observeAndReport(LiveData<T> liveData) {
        // Result holder
        final Object[] data = new Object[1];
        // Wait for observer result
        final CountDownLatch cdl = new CountDownLatch(1);
        // Observe changes
        Observer<T> observer = new Observer<T>() {
            @Override
            public void onChanged(T t) {
                data[0] = t;
                cdl.countDown();
                liveData.removeObserver(this);
            }
        };
        // Use observeForever since there is no lifecycle owner
        liveData.observeForever(observer);
        // Wait for results
        try {
            cdl.await(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) data[0];
    }
}
