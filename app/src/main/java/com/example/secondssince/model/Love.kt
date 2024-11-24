package com.example.secondssince.model

import android.net.Uri
import java.util.Date
import java.util.UUID

class Love(
    var id: UUID = UUID.randomUUID(),
    var userName: String,
    var loveName: String,
    var anniversary: Date,
    var loveImageUri: Uri? = null,
    var loveEvents: List<LoveEvent> = emptyList<LoveEvent>()
) {
    companion object {
        fun testLove(): Love {
            return Love(
                userName = "Grant",
                loveName = "Dream",
                anniversary = Date(1638662400000)
            )
        }
    }
}