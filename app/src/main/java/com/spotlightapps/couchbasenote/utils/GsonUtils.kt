package com.spotlightapps.couchbasenote.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Ahmad Jawid Muhammadi on 20/5/20
 */

//convert a data class to a map
fun <T> T.serializeToMap(): Map<String, Any> {
    return convert()
}

//convert a map to a data class
inline fun <reified T> Map<String, Any>.toDataClass(): T {
    return convert()
}

//convert an object of type I to type O
inline fun <I, reified O> I.convert(): O {
    val json = Gson().toJson(this)
    return Gson().fromJson(json, object : TypeToken<O>() {}.type)
}