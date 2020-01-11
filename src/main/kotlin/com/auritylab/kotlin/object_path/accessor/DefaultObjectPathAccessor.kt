package com.auritylab.kotlin.object_path.accessor

import com.auritylab.kotlin.object_path.KObjectPathAccessor
import com.auritylab.kotlin.object_path.exception.ObjectPathAccessorException
import com.auritylab.kotlin.object_path.part.IndexPathPart
import com.auritylab.kotlin.object_path.part.PathPart
import com.auritylab.kotlin.object_path.part.PropertyPathPart
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

/**
 * Represents a [KObjectPathAccessor] which uses Kotlin Reflection to access the properties.
 */
internal class DefaultObjectPathAccessor(root: KObjectPathAccessor?,
                                         override val parent: KObjectPathAccessor?,
                                         override val value: Any?,
                                         override val pathPart: PathPart?) : KObjectPathAccessor {
    // If given root is null this will be the root.
    override val root: KObjectPathAccessor = root ?: this

    override fun access(part: PathPart): KObjectPathAccessor {
        if (value == null)
            throw ObjectPathAccessorException(part, "Can not access value 'null'")

        if (part is PropertyPathPart) {
            val v = if (value is Map<*, *>)
                accessMapValue(value, part)
            else
                accessProperty(value, part)

            return DefaultObjectPathAccessor(root, this, v, part)
        } else if (part is IndexPathPart)
            return DefaultObjectPathAccessor(root, this, accessIterableValue(value, part), part)

        throw UnsupportedOperationException()
    }

    @Suppress("UNCHECKED_CAST")
    override fun set(value: Any?) {
        // Check if the parent is available. If it's not available assume it's the root accessor.
        if (parent == null)
            throw ObjectPathAccessorException("Can not set root value!")

        val parentValue = parent.value
        if (pathPart is PropertyPathPart) {

            if (parentValue is MutableMap<*, *>) {
                setMapValue(parentValue as MutableMap<Any, Any?>, pathPart, value)
            } else {
                throw ObjectPathAccessorException("Set operation is only supported on 'MutableMap'")
            }
        } else if (pathPart is IndexPathPart) {
            if (parentValue is MutableList<*>) {
                setListValue(parentValue as MutableList<Any>, pathPart, value)
            } else {
                throw ObjectPathAccessorException("Set operation for Index is only supported on 'MutableList'")
            }
        }
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
     * Will try to get the member property on the class of the given [value].
     *
     * @throws ObjectPathAccessorException If the given property could not be found on the class of the value.
     */
    private fun getMemberProperty(value: Any, part: PropertyPathPart): KProperty1<Any, *> {
        return value::class.memberProperties.firstOrNull { it.name == part.property } as KProperty1<Any, *>?
                ?: throw ObjectPathAccessorException(part, "Property '${part.property}' could not be found")
    }

    /**
     * Will search for the give [part] on the given [value].
     * The [IndexPathPart.index] will be used as index on the list.
     */
    private fun accessIterableValue(value: Any, part: IndexPathPart): Any? {
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
    private fun accessMapValue(map: Map<*, *>, part: PropertyPathPart): Any? {
        // Check if the key is available.
        if (!map.containsKey(part.property))
            throw ObjectPathAccessorException(part, "Property '${part.property}' could not be found")

        // Access the property on the map.
        return map[part.property]
    }

    /**
     * Will set the given [value] on the given key on the given [map].
     * The key will be taken from [PropertyPathPart.property].
     */
    private fun setMapValue(map: MutableMap<Any, Any?>, part: PropertyPathPart, value: Any?) {
        map[part.property] = value
    }

    /**
     * Will set the given [value] on the given index on the given [list].
     * The [list] must be mutable to allow changes. If the given [value] is `null` the entry in the list will be deleted.
     */
    private fun setListValue(list: MutableList<Any>, part: IndexPathPart, value: Any?) {
        if (value == null) {
            list.removeAt(part.index)
        } else {
            list[part.index] = value
        }
    }
}
