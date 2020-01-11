package com.auritylab.kotlin.object_path.part

class PropertyPathPart(
        val property: String,
        partString: String,
        partIndex: Int
) : PathPart(partString, partIndex, Type.PROPERTY)
