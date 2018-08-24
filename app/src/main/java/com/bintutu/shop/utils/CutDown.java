package com.bintutu.shop.utils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class CutDown {
    private int time;
    private OnCuDownListener mCuDownListener;
    private Disposable disposable;

    public CutDown(int mTime, OnCuDownListener mCuDownListener) {
        time = mTime;
        this.mCuDownListener = mCuDownListener;
    }

    private Observable<Integer> RxCutDown() {
        if (time < 0) time = 0;
        final int cuntTime = time;
        return Observable.interval(0, 1, TimeUnit.SECONDS).take(cuntTime + 1).map(new Function<Long, Integer>() {
            @Override
            public Integer apply(Long mLong) throws Exception {
                return cuntTime - mLong.intValue();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void subscribeCutDown() {

        RxCutDown().doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable mDisposable) throws Exception {
                disposable = mDisposable;
            }
        }).subscribe(new CutDownObservable<Integer>() {
            @Override
            protected void onTimer(Integer mInteger) {
                if (null != mCuDownListener) mCuDownListener.onNext(mInteger);
            }

            @Override
            protected void onFinish() {
                if (null != mCuDownListener) mCuDownListener.onFinish();
                if (null != disposable) disposable.dispose();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (null != mCuDownListener) mCuDownListener.onError(e);
                if (null != disposable) disposable.dispose();
            }
        });
    }

    public interface OnCuDownListener {
        void onNext(int time);

        void onFinish();

        void onError(Throwable mThrowable);

    }
}
