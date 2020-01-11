package com.auritylab.kotlin.object_path.part

class IndexPathPart(
        val index: Int,
        partString: String,
        partIndex: Int
) : PathPart(partString, partIndex, Type.INDEX)
