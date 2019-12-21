package com.mohsenoid.rickandmorty.view.base

interface BaseView {

    fun showMessage(message: String)

    fun showOfflineMessage(isCritical: Boolean)

    fun showLoading()

    fun hideLoading()
}
