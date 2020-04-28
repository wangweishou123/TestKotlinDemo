package com.example.testkotlindemo.base;

public interface IBaseView<T> {
    void setPresenter(T presenter);

    void showDialog();

    void dismissDialog();
}
