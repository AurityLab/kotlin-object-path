package com.auritylab.kotlin.object_path.api

interface ObjectPathWalker {
    fun walkValue(rootAccessor: ObjectPathAccessor, path: ObjectPath): Any?

    fun walkAccessor(rootAccessor: ObjectPathAccessor, path: ObjectPath): ObjectPathAccessor
}
