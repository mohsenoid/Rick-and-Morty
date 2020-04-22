package com.mohsenoid.rickandmorty.view.base

import androidx.lifecycle.ViewModel

abstract class BasePresenter<V : BaseView> : ViewModel() {

    abstract fun bind(view: V)

    abstract fun unbind()
}
