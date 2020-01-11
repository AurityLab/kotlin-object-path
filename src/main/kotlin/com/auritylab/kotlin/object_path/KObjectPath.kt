package com.auritylab.kotlin.object_path

import com.auritylab.kotlin.object_path.accessor.DefaultObjectPathAccessor
import com.auritylab.kotlin.object_path.part.IndexPathPart
import com.auritylab.kotlin.object_path.part.PathPart
import com.auritylab.kotlin.object_path.part.PropertyPathPart

/**
 * Represents the main class for "kotlin-object-path".
 * You can pass any given object to the constructor.
 * The object will then be used to accessed by a path.
 */
class KObjectPath(private val input: Any, private val accessorFactory: ((input: Any) -> KObjectPathAccessor)? = null) {
    /**
     * Will parse the given [path] and return a [KObjectPathOperations] instance, which can be used to access the path.
     * Example [path]: "a.b.c"
     */
    fun path(path: String): KObjectPathOperations {
        val split = path.split(".")

        return KObjectPathOperations(walkPath(parse(split)))
    }

    /**
     * Will parse the given [path] and return a [KObjectPathOperations] instance, which can be used to access the path.
     * Example [path]: "a", "b", "c"
     */
    fun path(vararg path: String): KObjectPathOperations {
        return KObjectPathOperations(walkPath(parse(path.toList())))
    }

    /**
     * Will take the given [input] and parses each element to an [PathPart].
     * An element either represents a [IndexPathPart] or a [PropertyPathPart].
     */
    private fun parse(input: Iterable<String>): List<PathPart> {
        return input.mapIndexed { index, part ->
            // Try to parse an int from the element
            val parsedInt = part.toIntOrNull()

            if (parsedInt != null) {
                // Is index part.
                IndexPathPart(parsedInt, part, index)
            } else {
                // Is property part.
                PropertyPathPart(part, part, index)
            }
        }
    }

    /**
     * Will walk the given path [parts] on the current [input].
     */
    private fun walkPath(parts: List<PathPart>): KObjectPathAccessor {
        return parts.fold(getInitialObjectAccessor(), KObjectPathAccessor::access)
    }

    /**
     * Will create the initial [KObjectPathAccessor] with the current [input].
     */
    private fun getInitialObjectAccessor(): KObjectPathAccessor =
            DefaultObjectPathAccessor(null, null, input, null)
}
