package com.auritylab.kotlin.object_path

import com.fasterxml.jackson.databind.ObjectMapper

internal object TestUtil {
    val testContainer = {
        Container(
                "0a",
                "0b",
                Container(
                        "0c1a",
                        listOf("0c1b0", "0c1b1", "0c1b2"),
                        mapOf(Pair("aa", "0c1c0"), Pair("bb", "0c1c2"))
                )
        )
    }

    val testJson = {
        val a = """{"a": "0a", "b": "0b", "c": {"a": "0c1a", "b": ["0c1b0", "0c1b1", "0c1b2"], "c": "hello"}}"""
        ObjectMapper().readValue(a, Map::class.java)
    }


    data class Container(val a: Any, val b: Any, val c: Any)
}
