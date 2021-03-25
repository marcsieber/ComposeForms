package model.util

import androidx.compose.runtime.mutableStateOf

/**
 * This class provides a value which can be observed. The value is stored as a MutableState and will invoke the UI
 * to rerender.
 * Changes are propagated to the listeners only if the value is different to the previous value
 *
 * TODO: MAYBE REMOVE AND USE Delegates.observable("")
 */
class Observable<T>(initValue: T) {

    private var value     = mutableStateOf(initValue)
    private val listeners = mutableListOf<(T) -> Unit>()

    /**
     * Returns the value
     * @return value
     */
    fun getValue(): T{
        return value.value
    }

    /**
     * Setting the value if the new value is different to the old one.
     * All the added listeners get notified.
     */
    fun setValue(newVal: T){
        if(value.value != newVal) {
            value.value = newVal
            listeners.forEach { it(newVal) }
        }
    }

    /**
     * Adds a function that gets called after an update of the value
     */
    fun addListener(obs: (T) -> Unit){
        listeners.add(obs)
    }
}