package com.auritylab.kotlin.object_path

import com.auritylab.kotlin.object_path.accessor.ReflectionObjectPathAccessor
import com.auritylab.kotlin.object_path.path.PathParser
import com.auritylab.kotlin.object_path.walk.ObjectPathWalkerImpl

object KReflectionObjectPath : AbstractKObjectPath<Any>(
        ObjectPathWalkerImpl(),
        PathParser(),
        { ReflectionObjectPathAccessor(null, null, it) })
