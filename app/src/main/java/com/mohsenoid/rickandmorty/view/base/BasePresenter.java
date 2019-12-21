package com.mohsenoid.rickandmorty.view.base;

public interface BasePresenter<T extends BaseView> {

    void bind(T view);

    void unbind();
}
