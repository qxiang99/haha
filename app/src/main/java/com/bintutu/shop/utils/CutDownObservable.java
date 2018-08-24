package com.bintutu.shop.utils;


import io.reactivex.disposables.Disposable;



public abstract   class CutDownObservable<T> implements io.reactivex.Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T mT) {
        onTimer(mT);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {
        onFinish();
    }

    protected abstract void onTimer(T mT);
    protected abstract void onFinish();
}
