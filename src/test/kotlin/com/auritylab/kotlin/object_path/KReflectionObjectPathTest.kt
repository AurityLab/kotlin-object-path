package com.auritylab.kotlin.object_path

import io.kotlintest.matchers.types.shouldBeInstanceOf
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

internal class KReflectionObjectPathTest : StringSpec({
    "Should get only property value properly" {
        val input = Container("1a", "1b", Container("2a", listOf(Container("3a", "3b", "3c")), "2c"))

        val value = KReflectionObjectPath.get(input, "c.a")

        value.shouldNotBeNull()
        value.shouldBeInstanceOf<String>()
    }

    "Should get index value properly" {
        val input = Container("1a", "1b", Container("2a", listOf(Container("3a", "3b", "3c")), "2c"))

        val value = KReflectionObjectPath.get(input, "c.b.0.a")

        value.shouldNotBeNull()
        value.shouldBeInstanceOf<String>()
        value.shouldBe("3a")
    }
})

private data class Container(
        val a: Any,
        val b: Any,
        val c: Any
)
