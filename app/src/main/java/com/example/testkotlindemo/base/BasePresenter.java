package com.example.testkotlindemo.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.lang.ref.WeakReference;

public class BasePresenter<V extends IBaseView> {
    private WeakReference<V> baseViewWeakReference;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @SuppressWarnings("unchecked")
    public BasePresenter(V baseView) {
        baseViewWeakReference = new WeakReference<>(baseView);
        baseViewWeakReference.get().setPresenter(this);
    }

    protected V getView() {
        return baseViewWeakReference.get();
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public void detachView() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        if (baseViewWeakReference != null) {
            baseViewWeakReference.clear();
            baseViewWeakReference = null;
        }
    }
}
