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

package com.ivianuu.xposedextensions

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// DELEGATED FIELDS

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("bindFieldAs")
fun <T> Any.bindField(name: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T = thisRef.getField<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setField(name, value)
    }
}

/**
 * Returns a read write field which uses the name to get and set it
 */
fun Any.bindField(name: String) = bindField<Any>(name)

/**
 * Returns a nullable read write field which uses the name to get and set it
 */
fun Any.bindNullableField(name: String) = bindField<Any?>(name)

/**
 * Returns a static read write field which uses the name to get and set it
 */
@JvmName("bindStaticFieldAs")
fun <T> Any.bindStaticField(name: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.getStaticField<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setStaticField(name, value)
    }
}

/**
 * Returns a static read write field which uses the name to get and set it
 */
fun Any.bindStaticField(name: String) = bindStaticField<Any>(name)

/**
 * Returns a nullable static read write field which uses the name to get and set it
 */
fun Any.bindNullableStaticField(name: String) = bindStaticField<Any?>(name)

/**
 * Returns a additional read write field which uses the name to get and set it
 */
@JvmName("bindAdditionalFieldAs")
fun <T> Any.bindAdditionalField(name: String) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val value = thisRef.getAdditionalField<T>(name)
        logX { "retrieving field from ${thisRef.toJavaClass().name} " +
                "value is $value" }
        return value
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        logX { "setting field to ${thisRef.toJavaClass().name} " +
                "new value is $value" }
        thisRef.setAdditionalField(name, value)
    }
}

/**
 * Returns a additional read write field which uses the name to get and set it
 */
fun Any.bindAdditionalField(name: String) = bindAdditionalField<Any>(name)

/**
 * Returns a nullable additional read write field which uses the name to get and set it
 */
fun Any.bindNullableAdditionalField(name: String) = bindAdditionalField<Any?>(name)

/**
 * Returns a additional static read write field which uses the name to get and set it
 */
@JvmName("bindAdditionalStaticFieldAs")
fun <T> Any.bindAdditionalStaticField(name: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.getAdditionalStaticField<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setAdditionalStaticField(name, value)
    }
}

/**
 * Returns a additional read write field which uses the name to get and set it
 */
fun Any.bindAdditionalStaticField(name: String) = bindAdditionalStaticField<Any>(name)

/**
 * Returns a nullable additional read write field which uses the name to get and set it
 */
fun Any.bindNullableAdditionalStaticField(name: String) = bindAdditionalStaticField<Any?>(name)