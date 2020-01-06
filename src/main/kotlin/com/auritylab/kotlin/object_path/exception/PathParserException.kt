package com.auritylab.kotlin.object_path.exception

class PathParserException(message: String) : RuntimeException(message) {
    constructor(partIndex: Int, message: String) : this("Index $partIndex: $message")
}
