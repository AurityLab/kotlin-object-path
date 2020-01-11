package com.auritylab.kotlin.object_path.part

abstract class PathPart(
        val partString: String,
        val partIndex: Int,
        val partType: Type
) {
    enum class Type {
        INDEX,
        PROPERTY
    }
}
