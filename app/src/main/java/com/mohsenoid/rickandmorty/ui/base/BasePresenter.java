package com.mohsenoid.rickandmorty.ui.base;

public interface BasePresenter<T extends BaseView> {

    void bind(T view);

    void unbind();
}
