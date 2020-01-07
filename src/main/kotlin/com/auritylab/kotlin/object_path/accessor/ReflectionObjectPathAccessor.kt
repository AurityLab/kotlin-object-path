package com.auritylab.kotlin.object_path.accessor

import com.auritylab.kotlin.object_path.api.ObjectPathAccessor
import com.auritylab.kotlin.object_path.exception.ObjectPathAccessorException
import com.auritylab.kotlin.object_path.path.IndexPathPart
import com.auritylab.kotlin.object_path.path.PathPart
import com.auritylab.kotlin.object_path.path.PropertyPathPart
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Represents a [ObjectPathAccessor] which uses Kotlin Reflection to access the properties.
 */
class ReflectionObjectPathAccessor(root: ObjectPathAccessor?,
                                   override val parent: ObjectPathAccessor?,
                                   override val value: Any?,
                                   override val pathPart: PathPart?) : ObjectPathAccessor {
    override val root: ObjectPathAccessor = root ?: this

    override fun access(part: PathPart): ObjectPathAccessor {
        if (value == null)
            throw ObjectPathAccessorException(part, "Can not access value 'null'")

        if (part is PropertyPathPart) {
            val v = if (value is Map<*, *>)
                accessMap(value, part)
            else
                accessProperty(value, part)

            return ReflectionObjectPathAccessor(root, this, v, part)
        } else if (part is IndexPathPart)
            return ReflectionObjectPathAccessor(root, this, accessIndex(value, part), part)

        throw UnsupportedOperationException()
    }

    override fun set(value: Any?): Any? {
        throw UnsupportedOperationException()
    }

    /**
     * Will access the [part] on the given [value]. If the [part] is not accessible it will be unlocked
     * and locked again after accessing.
     */
    private fun accessProperty(value: Any, part: PropertyPathPart): Any? {
        val property = getMemberProperty(value, part)

        val originAccessible = property.isAccessible

        // If the property is not accessible it will be unlocked.
        if (!originAccessible)
            property.isAccessible = true

        // Access the property.
        val result = property.get(value)

        // If the property was not accessible it will be locked again.
        if (!originAccessible)
            property.isAccessible = false

        return result
    }

    /**
     * Will search for the property given in the [part] on the given [value]
     */
    private fun getMemberProperty(value: Any, part: PropertyPathPart): KProperty1<Any, *> {
        return value::class.memberProperties.firstOrNull { it.name == part.property } as KProperty1<Any, *>?
                ?: throw ObjectPathAccessorException(part, "Property '${part.property}' could not be found")
    }

    /**
     * Will search for the give [part] on the given [value].
     * The [IndexPathPart.index] will be used as index on the list.
     */
    private fun accessIndex(value: Any, part: IndexPathPart): Any? {
        // Check if value is iterable.
        if (value !is Iterable<*> && value !is Array<*>)
            throw ObjectPathAccessorException(part, "Can not access index on non-iterable value")

        // Get a list from the iterable.
        val list = if (value is Iterable<*>) value.toList() else if (value is Array<*>) value.toList() else null

        // Check if the list has given index.
        if (part.index > list!!.size)
            throw ObjectPathAccessorException(part, "Index '${part.index}' could not be found")

        return list[part.index]
    }

    /**
     * Will search for the given [part] on the given [map].
     * The [PropertyPathPart.property] will be used as key for the map.
     */
    private fun accessMap(map: Map<*, *>, part: PropertyPathPart): Any? {
        // Check if the key is available.
        if (!map.containsKey(part.property))
            throw ObjectPathAccessorException(part, "Property '${part.property}' could not be found")

        return map[part.property]
    }
}
