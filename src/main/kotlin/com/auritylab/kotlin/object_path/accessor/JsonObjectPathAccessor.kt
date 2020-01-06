package com.auritylab.kotlin.object_path.accessor

import com.auritylab.kotlin.object_path.api.ObjectPathAccessor

class JsonObjectPathAccessor(
        root: ObjectPathAccessor?,
        override val parent: JsonObjectPathAccessor?,
        override val value: Any?
) : ObjectPathAccessor {
    override val root: ObjectPathAccessor = root ?: this

    override fun access(part: com.auritylab.kotlin.object_path.path.PathPart): com.auritylab.kotlin.object_path.api.ObjectPathAccessor {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun set(value: Any?): Any? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
