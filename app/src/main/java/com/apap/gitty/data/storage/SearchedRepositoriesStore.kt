package com.apap.gitty.data.storage

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

private const val SEARCHED_REPOSITORIES_KEY = "LAST_SEARCHED_REPOSITORIES_KEY"
private const val SEARCHED_REPOSITORIES_MAX_LIST_SIZE = 10

@Singleton
class SearchedRepositoriesStore @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    private val cache = mutableListOf<String>()

    fun load(): List<String> = cache

    fun add(owner: String, name: String) {
        val data = "$owner/$name"
        if (cache.contains(data).not()) {
            when {
                cache.size < SEARCHED_REPOSITORIES_MAX_LIST_SIZE -> cache.add(data)
                else -> cache[0] = data
            }
        }

        val serialized = cache.joinToString(",")
        sharedPreferences.edit()
            .putString(SEARCHED_REPOSITORIES_KEY, serialized)
            .apply()
    }

    fun loadFromSharedPreferences(): List<String> {
        val serialized = sharedPreferences.getString(SEARCHED_REPOSITORIES_KEY, null).orEmpty()
        return serialized.split(',')
    }
}