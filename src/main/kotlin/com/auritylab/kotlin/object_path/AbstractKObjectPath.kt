package com.auritylab.kotlin.object_path

import com.auritylab.kotlin.object_path.api.ObjectPathAccessor
import com.auritylab.kotlin.object_path.api.ObjectPathParser
import com.auritylab.kotlin.object_path.api.ObjectPathWalker

abstract class AbstractKObjectPath<T : Any>(
        private val walker: ObjectPathWalker,
        private val parser: ObjectPathParser,
        private val accessorFactory: (input: T) -> ObjectPathAccessor) : KObjectPath<T> {
    override fun get(input: T, path: String, default: Any?): Any? {
        return walker.walkValue(accessorFactory(input), parser.parsePath(path)) ?: default
    }

    override fun get(input: T, vararg path: String, default: Any?): Any? {
        return walker.walkValue(accessorFactory(input), parser.parsePath(path as Array<String>)) ?: default
    }

    override fun set(input: T, path: String, value: Any?): Any? {
        return walker.walkAccessor(accessorFactory(input), parser.parsePath(path)).set(value)
    }

    override fun set(input: T, vararg path: String, value: Any?): Any? {
        return walker.walkAccessor(accessorFactory(input), parser.parsePath(path as Array<String>)).set(value)
    }

    override fun has(input: T, path: String): Boolean {
        try {
            walker.walkAccessor(accessorFactory(input), parser.parsePath(path))
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun has(input: T, vararg path: String): Boolean {
        try {
            walker.walkAccessor(accessorFactory(input), parser.parsePath(path as Array<String>))
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
