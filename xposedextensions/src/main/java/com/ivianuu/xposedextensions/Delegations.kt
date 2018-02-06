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

fun <T> Any.bindField(name: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T = thisRef.getField<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setField(name, value)
    }
}

fun <T> Any.bindStaticField(name: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.getStaticField<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setStaticField(name, value)
    }
}

fun <T> Any.bindAdditionalField(name: String) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return thisRef.getAdditionalField(name)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setAdditionalField(name, value)
    }
}

fun <T> Any.bindAdditionalStaticField(name: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.getAdditionalStaticField<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setAdditionalStaticField(name, value)
    }
}