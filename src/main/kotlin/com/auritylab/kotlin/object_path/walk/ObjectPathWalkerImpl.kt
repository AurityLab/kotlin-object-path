package com.auritylab.kotlin.object_path.walk

import com.auritylab.kotlin.object_path.api.ObjectPath
import com.auritylab.kotlin.object_path.api.ObjectPathAccessor
import com.auritylab.kotlin.object_path.api.ObjectPathWalker

class ObjectPathWalkerImpl : ObjectPathWalker {
    override fun walkValue(rootAccessor: ObjectPathAccessor, path: ObjectPath): Any? {
        return walkAccessor(rootAccessor, path).value
    }

    override fun walkAccessor(rootAccessor: ObjectPathAccessor, path: ObjectPath): ObjectPathAccessor {
        var currentAccessor = rootAccessor

        return path.parts.fold(rootAccessor) { acc, pathPart ->
            acc.access(pathPart)
        }
    }
}
