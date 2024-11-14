package com.example.secondssince.model

import java.util.Date

class Love(
    var userName: String,
    var loveName: String,
    var anniversary: Date,
    var loveEvents: List<LoveEvent> = emptyList<LoveEvent>()
)