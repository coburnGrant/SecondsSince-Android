package com.example.secondssince.model

import java.util.Date
import java.util.UUID

class Love(
    var id: UUID = UUID.randomUUID(),
    var userName: String,
    var loveName: String,
    var anniversary: Date,
    var loveEvents: List<LoveEvent> = emptyList<LoveEvent>()
)