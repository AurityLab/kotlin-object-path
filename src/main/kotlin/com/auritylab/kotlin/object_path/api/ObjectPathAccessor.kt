package com.auritylab.kotlin.object_path.api

import com.auritylab.kotlin.object_path.exception.ObjectPathAccessorException
import com.auritylab.kotlin.object_path.path.PathPart

/**
 * Describes the accessor for a given object.
 */
interface ObjectPathAccessor {
    /**
     * The root [ObjectPathAccessor], which is handles path part zero.
     */
    val root: ObjectPathAccessor
    /**
     * The parent [ObjectPathAccessor] of this [ObjectPathAccessor].
     */
    val parent: ObjectPathAccessor?
    /**
     * The value which is currently represented by this [ObjectPathAccessor].
     */
    val value: Any?

    /**
     * Will access the property or index (defined in the given [part]) on the current [value].
     * The result will be wrapped in another [ObjectPathAccessor].
     * If the [value] is null this will throw an exception, because it can not access anything.
     * If the [value] does not contain the expected property or is no list it will throw an exception.
     *
     * @throws ObjectPathAccessorException If [value] is null or the [value] does not contain the expected property
     * or is not iterable.
     */
    fun access(part: PathPart): ObjectPathAccessor

    /**
     * Will set the current value.
     */
    fun set (value: Any?): Any?
}
