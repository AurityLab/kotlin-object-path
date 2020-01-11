package com.auritylab.kotlin.object_path

class KObjectPathOperations(private val accessor: KObjectPathAccessor) {
    fun get(): Any? {
        return accessor.value
    }

    fun set(value: Any?): Any? {
        val currentValue = accessor.value

        accessor.set(value)

        return currentValue
    }

    fun has(): Boolean {
        return accessor.value != null
    }
}
