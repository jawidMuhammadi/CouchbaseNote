package com.spotlightapps.couchbasenote

import android.app.Application
import com.couchbase.lite.CouchbaseLite

/**
 * Created by Ahmad Jawid Muhammadi on 17/5/20
 */
class CouchbaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CouchbaseLite.init(this)
    }
}