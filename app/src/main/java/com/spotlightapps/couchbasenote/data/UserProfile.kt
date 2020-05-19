package com.spotlightapps.couchbasenote.data

import com.couchbase.lite.Blob

/**
 * Created by Ahmad Jawid Muhammadi on 17/5/20
 */

data class UserProfile(
    var type: UserType,
    var name: String,
    var password: String?,
    var address: String = "Pune"
)

enum class UserType {
    USER,
    ADMIN,
}

