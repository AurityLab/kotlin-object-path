package com.auritylab.kotlin.object_path.exception

import com.auritylab.kotlin.object_path.path.PathPart

class ObjectPathAccessorException(message: String) : RuntimeException(message) {
    constructor(part: PathPart, message: String): this("Part '${part.partString}'(Index ${part.partIndex}): $message")
}
