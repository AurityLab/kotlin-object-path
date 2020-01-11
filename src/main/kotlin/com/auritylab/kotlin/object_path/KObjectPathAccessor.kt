package com.auritylab.kotlin.object_path

import com.auritylab.kotlin.object_path.exception.ObjectPathAccessorException
import com.auritylab.kotlin.object_path.part.PathPart

/**
 * Describes the accessor for a given object.
 */
interface KObjectPathAccessor {
    /**
     * The root [KObjectPathAccessor], which is handles path part zero.
     */
    val root: KObjectPathAccessor
    /**
     * The parent [KObjectPathAccessor] of this [KObjectPathAccessor].
     */
    val parent: KObjectPathAccessor?
    /**
     * The value which is currently represented by this [KObjectPathAccessor].
     */
    val value: Any?
    /**
     * The part with which this accessor was created.
     */
    val pathPart: PathPart?

    /**
     * Will access the property or index (defined in the given [part]) on the current [value].
     * The result will be wrapped in another [KObjectPathAccessor].
     * If the [value] is null this will throw an exception, because it can not access anything.
     * If the [value] does not contain the expected property or is no list it will throw an exception.
     *
     * @throws ObjectPathAccessorException If [value] is null or the [value] does not contain the expected property
     * or is not iterable.
     */
    fun access(part: PathPart): KObjectPathAccessor

    /**
     * Will set the current value.
     */
    fun set(value: Any?)
}
