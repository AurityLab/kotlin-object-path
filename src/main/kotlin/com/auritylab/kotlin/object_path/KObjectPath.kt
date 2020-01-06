package com.auritylab.kotlin.object_path

interface KObjectPath<T : Any> {
    fun get(input: T, path: String, default: Any? = null): Any?
    fun get(input: T, vararg path: String, default: Any? = null): Any?

    fun set(input: T, path: String, value: Any?): Any?
    fun set(input: T, vararg path: String, value: Any?): Any?

    fun has(input: T, path: String): Boolean
    fun has(input: T, vararg path: String): Boolean
}
