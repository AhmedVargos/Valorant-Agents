package com.ahmedvargos.uicomponents.utils

/* ktlint-disable no-wildcard-imports */
import androidx.lifecycle.*

/**
    Could have multiple observers and emits the new items after observing
 */
class SingleLiveData<T> : MutableLiveData<T>() {

    private val liveDataToObserve: LiveData<T>
    private val pendingMap: MutableMap<Int, Boolean>

    init {
        val outputLiveData = MediatorLiveData<T>()
        outputLiveData.addSource(this) { currentValue ->
            outputLiveData.value = currentValue
        }
        liveDataToObserve = outputLiveData
        pendingMap = HashMap()
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        pendingMap[observer.hashCode()] = false

        // Observe the internal MutableLiveData
        liveDataToObserve.observe(owner, { t ->
            // don't trigger if the observer wasn't registered
            if (pendingMap[observer.hashCode()] == true) {
                observer.onChanged(t)
                pendingMap[observer.hashCode()] = false
            }
        })
    }

    override fun setValue(t: T?) {
        pendingMap.forEach { pendingMap[it.key] = true }
        super.setValue(t)
    }
}
