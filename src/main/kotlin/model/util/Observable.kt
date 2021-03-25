package model.util

import androidx.compose.runtime.mutableStateOf


class Observable<T>(initValue: T) {

    private var value = mutableStateOf(initValue)

    val listeners = mutableListOf<(T) -> Unit>()

    fun getValue(): T{
        return value.value
    }

    fun setValue(newVal: T){
        if(value.value != newVal) {
            value.value = newVal
            listeners.forEach { it(newVal) }
        }
    }

    fun addListener(obs: (T) -> Unit){
        listeners.add(obs)
    }
}