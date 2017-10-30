import de.robv.android.xposed.XSharedPreferences

/*
 * Copyright 2017 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

private const val DEFAULT_BOOLEAN = false
private const val DEFAULT_FLOAT = 0f
private const val DEFAULT_INT = 0
private const val DEFAULT_LONG = 0L
private const val DEFAULT_STRING = ""

/**
 * Reloads the prefs and returns whether the prefs contains the key or not
 */
fun XSharedPreferences.reloadAndContains(key: String): Boolean {
    reload()
    return contains(key)
}

/**
 * Reloads the prefs and returns all entries
 */
fun XSharedPreferences.reloadAndGetAll(): Map<String, *> {
    reload()
    return all
}

/**
 * Reloads the prefs the and returns the value for the key
 */
fun XSharedPreferences.reloadAndGetBoolean(key: String, default: Boolean = DEFAULT_BOOLEAN): Boolean {
    reload()
    return getBoolean(key, default)
}

/**
 * Returns the value for the key
 */
fun XSharedPreferences.getBoolean(key: String): Boolean = getBoolean(key, DEFAULT_BOOLEAN)

/**
 * Reloads the prefs the and returns the value for the key
 */
fun XSharedPreferences.reloadAndGetFloat(key: String, default: Float = DEFAULT_FLOAT): Float {
    reload()
    return getFloat(key, default)
}

/**
 * Returns the value for the key
 */
fun XSharedPreferences.getFloat(key: String): Float = getFloat(key, DEFAULT_FLOAT)

/**
 * Reloads the prefs the and returns the value for the key
 */
fun XSharedPreferences.reloadAndGetInt(key: String, default: Int = DEFAULT_INT): Int {
    reload()
    return getInt(key, default)
}

/**
 * Returns the value for the key
 */
fun XSharedPreferences.getInt(key: String): Int = getInt(key, DEFAULT_INT)

/**
 * Reloads the prefs the and returns the value for the key
 */
fun XSharedPreferences.reloadAndGetLong(key: String, default: Long = DEFAULT_LONG): Long {
    reload()
    return getLong(key, default)
}

/**
 * Returns the value for the key
 */
fun XSharedPreferences.getLong(key: String): Long = getLong(key, DEFAULT_LONG)

/**
 * Reloads the prefs the and returns the value for the key
 */
fun XSharedPreferences.reloadAndGetString(key: String, default: String = DEFAULT_STRING): String {
    reload()
    return getString(key, default)
}

/**
 * Returns the value for the key
 */
fun XSharedPreferences.getString(key: String): String = getString(key, DEFAULT_STRING)

/**
 * Reloads the prefs the and returns the value for the key
 */
fun XSharedPreferences.reloadAndGetStringSet(key: String, default: Set<String> = emptySet()): Set<String> {
    reload()
    return getStringSet(key, default)
}

/**
 * Returns the value for the key
 */
fun XSharedPreferences.getStringSet(key: String): Set<String> = getStringSet(key, emptySet())