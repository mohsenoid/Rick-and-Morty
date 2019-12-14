package com.mohsenoid.rickandmorty.ui.base;

public interface BaseView {

    void showMessage(String message);

    void showOfflineMessage(boolean isCritical);

    void showLoading();

    void hideLoading();
}
