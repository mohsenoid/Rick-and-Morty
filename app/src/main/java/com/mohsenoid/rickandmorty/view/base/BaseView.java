package com.mohsenoid.rickandmorty.view.base;

public interface BaseView {

    void showMessage(String message);

    void showOfflineMessage(boolean isCritical);

    void showLoading();

    void hideLoading();
}
