package com.auritylab.kotlin.object_path

class KObjectPathOperations(private val accessor: KObjectPathAccessor) {
    /**
     * Will return the value of the [accessor].
     */
    fun get(): Any? {
        return accessor.value
    }

    /**
     * Will set the given [value] on the [accessor]. This will return the previous value.
     */
    fun set(value: Any?): Any? {
        val currentValue = accessor.value

        accessor.set(value)

        return currentValue
    }
}
