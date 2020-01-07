package com.auritylab.kotlin.object_path.accessor

import com.auritylab.kotlin.object_path.path.PathParser
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.specs.StringSpec

internal class ReflectionObjectPathAccessorTest : StringSpec({
    "Should access property correctly" {
        val part = PathParser().parsePath("b").parts.first()

        val rootWalker = ReflectionObjectPathAccessor(null, null,
                Container("1a", Container("2a", "2b", "3c"), "2c"), null)

        val accessedProperty = rootWalker.access(part)


        accessedProperty.shouldNotBeNull()
        accessedProperty.value.shouldNotBeNull()
        accessedProperty.value.shouldBeInstanceOf<Container>()
    }

    "Should access index correctly" {
        val part = PathParser().parsePath("2").parts.first()

        val rootWalker = ReflectionObjectPathAccessor(null, null, listOf("a", "b", "c"), null)

        val accessedProperty = rootWalker.access(part)

        accessedProperty.shouldNotBeNull()
        accessedProperty.value.shouldNotBeNull()
        accessedProperty.value.shouldBeInstanceOf<String>()
    }
})

private data class Container(
        val a: Any,
        val b: Any,
        val c: Any
)
