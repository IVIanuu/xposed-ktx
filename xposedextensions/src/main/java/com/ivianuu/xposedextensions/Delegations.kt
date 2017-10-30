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

import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// DELEGATED FIELDS

/*/**
 * Binds a field of the receiver
 */
private fun <T> fieldWithReceiver(name: String, receiver: () -> Any) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T = receiver().get(name) as T
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        receiver().set(name, value)
    }
}*/

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("fieldAs")
fun <T> Any.field(name: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.get<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.set(name, value)
    }
}

/**
 * Returns a read write field which uses the name to get and set it
 */
fun Any.field(name: String) = field<Any>(name)

/**
 * Returns a nullable read write field which uses the name to get and set it
 */
fun Any.nullableField(name: String) = field<Any?>(name)

/**
 * Returns a static read write field which uses the name to get and set it
 */
@JvmName("staticFieldAs")
fun <T> Any.staticField(name: String) =  object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.get<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.set(name, value)
    }
}

/**
 * Returns a static read write field which uses the name to get and set it
 */
fun Any.staticField(name: String) = staticField<Any>(name)

/**
 * Returns a nullable static read write field which uses the name to get and set it
 */
fun Any.nullStaticField(name: String) = staticField<Any?>(name)

/**
 * Returns a additional read write field which uses the name to get and set it
 */
@JvmName("additionalFieldAs")
fun <T> Any.additionalField(name: String) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.getAdditional<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setAdditional(name, value)
    }
}

/**
 * Returns a additional read write field which uses the name to get and set it
 */
fun Any.additionalField(name: String) = additionalField<Any>(name)

/**
 * Returns a nullable additional read write field which uses the name to get and set it
 */
fun Any.nullableAdditionalField(name: String) = additionalField<Any?>(name)

/**
 * Returns a additional static read write field which uses the name to get and set it
 */
@JvmName("additionalStaticFieldAs")
fun <T> Any.additionalStaticField(name: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.getAdditionalStatic<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setAdditionalStatic(name, value)
    }
}

/**
 * Returns a additional read write field which uses the name to get and set it
 */
fun Any.additionalStaticField(name: String) = additionalStaticField<Any>(name)

/**
 * Returns a nullable additional read write field which uses the name to get and set it
 */
fun Any.nullableAdditionalStaticField(name: String) = additionalStaticField<Any?>(name)

// DELEGATED FUNCTIONS

interface Function<out T> {
    /**
     * Returns the result
     */
    operator fun invoke(vararg args: Any): T
}

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("functionAs")
fun <T> Any.function(name: String) = object : ReadOnlyProperty<Any, Function<T>> {
    private var function: Function<T>? = null
    override fun getValue(thisRef: Any, property: KProperty<*>): Function<T> {
        if (function == null) {
            function = object : Function<T> {
                override fun invoke(vararg args: Any) = thisRef.invoke<T>(name, *args)
            }
        }
        return function!!
    }

}

/**
 * Returns a function which returns the result of name when called
 */
fun Any.function(name: String) = function<Any>(name)

/**
 * Returns a function which returns the result of name when called which can be null
 */
fun Any.functionSafe(name: String) = function<Any?>(name)

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("functionAs")
fun <T> Any.function(name: String,
                     parameterTypes: Array<Class<*>>) = object : ReadOnlyProperty<Any, Function<T>> {
    private var function: Function<T>? = null
    override fun getValue(thisRef: Any, property: KProperty<*>): Function<T> {
        if (function == null) {
            function = object : Function<T> {
                override fun invoke(vararg args: Any) = thisRef.invoke<T>(name, parameterTypes, *args)
            }
        }
        return function!!
    }

}

/**
 * Returns a function which returns the result of name when called
 */
fun Any.function(name: String,
                 parameterTypes: Array<Class<*>>) = function<Any>(name, parameterTypes)

/**
 * Returns a function which returns the result of name when called which can be null
 */
fun Any.functionSafe(name: String,
                     parameterTypes: Array<Class<*>>) = function<Any?>(name, parameterTypes)

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("staticFunctionAs")
fun <T> Any.staticFunction(name: String) = object : ReadOnlyProperty<Any, Function<T>> {
    private var function: Function<T>? = null
    override fun getValue(thisRef: Any, property: KProperty<*>): Function<T> {
        if (function == null) {
            function = object : Function<T> {
                override fun invoke(vararg args: Any) = thisRef.invokeStatic<T>(name, *args)
            }
        }
        return function!!
    }

}

/**
 * Returns a function which returns the result of name when called
 */
fun Any.staticFunction(name: String) = staticFunction<Any>(name)

/**
 * Returns a function which returns the result of name when called which can be null
 */
fun Any.staticFunctionSafe(name: String) = staticFunction<Any?>(name)

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("staticFunctionAs")
fun <T> Any.staticFunction(name: String,
                           parameterTypes: Array<Class<*>>) = object : ReadOnlyProperty<Any, Function<T>> {
    private var function: Function<T>? = null
    override fun getValue(thisRef: Any, property: KProperty<*>): Function<T> {
        if (function == null) {
            function = object : Function<T> {
                override fun invoke(vararg args: Any) = thisRef.invokeStatic<T>(name, parameterTypes, *args)
            }
        }
        return function!!
    }

}

/**
 * Returns a function which returns the result of name when called
 */
fun Any.staticFunction(name: String,
                       parameterTypes: Array<Class<*>>) = staticFunction<Any>(name, parameterTypes)

/**
 * Returns a function which returns the result of name when called which can be null
 */
fun Any.staticFunctionSafe(name: String,
                           parameterTypes: Array<Class<*>>) = staticFunction<Any?>(name, parameterTypes)