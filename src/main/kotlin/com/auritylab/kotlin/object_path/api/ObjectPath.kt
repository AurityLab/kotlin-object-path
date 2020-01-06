package com.auritylab.kotlin.object_path.api

import com.auritylab.kotlin.object_path.path.PathPart

interface ObjectPath {
    val parts: List<PathPart>
}
