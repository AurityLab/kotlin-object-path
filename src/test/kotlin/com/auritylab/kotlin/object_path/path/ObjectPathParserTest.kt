package com.auritylab.kotlin.object_path.path

import com.auritylab.kotlin.object_path.exception.PathParserException
import io.kotlintest.matchers.collections.shouldHaveAtLeastSize
import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

internal class ObjectPathParserTest : StringSpec({
    "Should parse property only path correctly" {
        val parser = PathParser().parsePath("a.b.c.d")

        parser.parts.shouldHaveAtLeastSize(4)
        parser.parts.forEach { pathPart ->
            pathPart.shouldBeInstanceOf<PropertyPathPart>()
        }
    }

    "Should parse path with indexes correctly" {
        val parser = PathParser().parsePath("a.0.b.0")

        parser.parts.shouldHaveAtLeastSize(4)

        parser.parts.forEachIndexed { index, pathPart ->
            if ((index + 1) % 2 == 0)
                pathPart.shouldBeInstanceOf<IndexPathPart>()
            else
                pathPart.shouldBeInstanceOf<PropertyPathPart>()
        }
    }

    "Should accept index part as first" {
        val parser = PathParser().parsePath("0")

        parser.parts.shouldHaveAtLeastSize(1)
        parser.parts[0].shouldBeInstanceOf<IndexPathPart>()
    }
})
