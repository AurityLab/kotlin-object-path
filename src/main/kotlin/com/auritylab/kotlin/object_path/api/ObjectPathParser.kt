package com.auritylab.kotlin.object_path.api

interface ObjectPathParser {
    fun parsePath(input: String): ObjectPath
    fun parsePath(input: Array<String>): ObjectPath
    fun parsePath(input: Collection<String>): ObjectPath
}
