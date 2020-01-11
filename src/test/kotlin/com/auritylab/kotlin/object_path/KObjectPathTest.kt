package com.auritylab.kotlin.object_path

import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

internal class KObjectPathTest : StringSpec({
    "Should get simple value properly" {
        val first = KObjectPath(TestUtil.testContainer()).path("a").get()

        first.shouldNotBeNull()
        first.shouldBe("0a")
    }

    "Should get list value properly" {
        val first = KObjectPath(TestUtil.testContainer()).path("c.b.0").get()

        first.shouldNotBeNull()
        first.shouldBe("0c1b0")
    }

    "Should get map value properly" {
        val first = KObjectPath(TestUtil.testContainer()).path("c.c.aa").get()

        first.shouldNotBeNull()
        first.shouldBe("0c1c0")
    }

    "Should set value on list properly" {
        val container = TestUtil.testContainer()

        val set = KObjectPath(container).path("c.b.0").set("new value")

        val get = KObjectPath(container).path("c.b.0").get()

        set.shouldNotBeNull()
        set.shouldBe("0c1b0")

        get.shouldNotBeNull()
        get.shouldBe("new value")
    }

    "Should set value on map properly" {
        val container = TestUtil.testContainer()

        val set = KObjectPath(container).path("c.c.aa").set("new value")

        val get = KObjectPath(container).path("c.c.aa").get()

        set.shouldNotBeNull()
        set.shouldBe("0c1c0")

        get.shouldNotBeNull()
        get.shouldBe("new value")
    }

    "Should get value properly on parsed JSON (Jackson)" {
        val container = TestUtil.testJson()

        val getA = KObjectPath(container).path("a").get()
        getA.shouldNotBeNull()
        getA.shouldBe("0a")

        val getB = KObjectPath(container).path("c.b.0").get()
        getB.shouldNotBeNull()
        getB.shouldBe("0c1b0")

        val getC = KObjectPath(container).path("c.c").get()
        getC.shouldNotBeNull()
        getC.shouldBe("hello")
    }

    "Should set value properly on parsed JSON (Jackson)" {
        val containerA = TestUtil.testJson()

        // Simple value

        val setA = KObjectPath(containerA).path("a").set("new value")
        val getA = KObjectPath(containerA).path("a").get()

        setA.shouldNotBeNull()
        setA.shouldBe("0a")

        getA.shouldNotBeNull()
        getA.shouldBe("new value")

        val containerB = TestUtil.testJson()

        val setB = KObjectPath(containerB).path("c.b.0").set("new value")
        val getB = KObjectPath(containerB).path("c.b.0").get()

        setB.shouldNotBeNull()
        setB.shouldBe("0c1b0")

        getB.shouldNotBeNull()
        getB.shouldBe("new value")
    }
})
