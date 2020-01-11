package com.auritylab.kotlin.object_path.exception

class ObjectPathParserException(message: String) : RuntimeException(message) {
    constructor(partIndex: Int, message: String) : this("Index $partIndex: $message")
}
