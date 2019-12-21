package com.mohsenoid.rickandmorty.view.base

interface BasePresenter<V : BaseView> {

    fun bind(view: V)

    fun unbind()
}
