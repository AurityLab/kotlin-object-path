package com.auritylab.kotlin.object_path.path

import com.auritylab.kotlin.object_path.api.ObjectPath
import com.auritylab.kotlin.object_path.api.ObjectPathParser

/**
 * Represents a parser which is capable of parsing an incoming path (e.g. "person.name")
 * and generate different [PathPart]s.
 */
class PathParser : ObjectPathParser {

    override fun parsePath(input: String): ObjectPath {
        return parseIterable(input.split("."))
    }

    override fun parsePath(input: Array<String>): ObjectPath {
        return parseIterable(input.asIterable())
    }

    override fun parsePath(input: Collection<String>): ObjectPath {
        return parseIterable(input)
    }

    /**
     * Will parse each entry in the given [input] iterable. A entry can either be a Property part or a Index part.
     */
    private fun parseIterable(input: Iterable<String>): ObjectPath {
        val partsList = mutableListOf<PathPart>()
        input.forEachIndexed { index, part ->
            val parsedInt = part.toIntOrNull()

            if (parsedInt != null) {
                // Is index part.
                partsList.add(IndexPathPartImpl(part, parsedInt, index))
            } else {
                // Is property part.
                partsList.add(PropertyPathPartImpl(part, part, index))
            }
        }

        return ObjectPathImpl(partsList)
    }

    private data class ObjectPathImpl(override val parts: List<PathPart>) : ObjectPath
    private data class PropertyPathPartImpl(override val partString: String, override val property: String, override val partIndex: Int) : PropertyPathPart
    private data class IndexPathPartImpl(override val partString: String, override val index: Int, override val partIndex: Int) : IndexPathPart
}
