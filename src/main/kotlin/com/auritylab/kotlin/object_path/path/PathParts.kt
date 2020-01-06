package com.auritylab.kotlin.object_path.path

/**
 * Describes a single entry within a path.
 */
interface PathPart {
    val partString: String
    val partIndex: Int
    val partType: Type

    enum class Type {
        PROPERTY,
        INDEX
    }
}

/**
 * Describes a property accessor within a path.
 */
interface PropertyPathPart : PathPart {
    val property: String
    override val partType: PathPart.Type
        get() = PathPart.Type.PROPERTY
}

/**
 * Describes a index accessor within a path.
 */
interface IndexPathPart : PathPart {
    val index: Int
    override val partType: PathPart.Type
        get() = PathPart.Type.INDEX
}
