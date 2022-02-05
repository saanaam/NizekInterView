package com.sanam.nizekinterview.common

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

import androidx.collection.ArraySet
import androidx.lifecycle.MediatorLiveData

open class SingleLiveEvent<T> : MediatorLiveData<T>() {

    private val observers = ArraySet<ObserverWrapper<in T>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observe(owner, wrapper)
    }

    @MainThread
    override fun observeForever(observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        observers.add(wrapper)
        super.observeForever(wrapper)
    }

    @MainThread
    override fun removeObserver(observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        if (observers.remove(wrapper)) {
            super.removeObserver(wrapper)
            return
        }
        val iterator = observers.iterator()
        while (iterator.hasNext()) {
            val wrapperNext = iterator.next()
            if (wrapperNext.observer == observer) {
                iterator.remove()
                super.removeObserver(wrapperNext)
                break
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.newValue() }
        super.setValue(t)
    }

    override fun postValue(value: T?) {
        observers.forEach { it.newValue() }
        super.postValue(value)
    }

    private class ObserverWrapper<T>(val observer: Observer<T>) : Observer<T> {

        private var pending = false

        override fun onChanged(t: T?) {
            if (pending) {
                pending = false
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending = true
        }
    }
}