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
fun <T> Any.staticField(name: String) = object : ReadWriteProperty<Any, T> {

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
fun Any.nullableStaticField(name: String) = staticField<Any?>(name)

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

// FIELDS WITH RECEIVER

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("fieldWithReceiverAs")
fun <T> fieldWithReceiver(name: String,
                          receiver: () -> Any) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = receiver().get<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        receiver().set(name, value)
    }
}

/**
 * Returns a read write field which uses the name to get and set it
 */
fun fieldWithReceiver(name: String,
                      receiver: () -> Any) = fieldWithReceiver<Any>(name, receiver)

/**
 * Returns a nullable read write field which uses the name to get and set it
 */
fun nullableFieldWithReceiver(name: String,
                              receiver: () -> Any) = fieldWithReceiver<Any?>(name, receiver)

/**
 * Returns a static read write field which uses the name to get and set it
 */
@JvmName("staticFieldWithReceiverAs")
fun <T> staticFieldWithReceiver(name: String,
                                receiver: () -> Any) =  object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = receiver().get<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        receiver().set(name, value)
    }
}

/**
 * Returns a static read write field which uses the name to get and set it
 */
fun Any.staticFieldWithReceiver(name: String,
                                receiver: () -> Any) = staticFieldWithReceiver<Any>(name, receiver)

/**
 * Returns a nullable static read write field which uses the name to get and set it
 */
fun Any.nullableStaticFieldWithReceiver(name: String,
                                        receiver: () -> Any) = staticFieldWithReceiver<Any?>(name, receiver)

/**
 * Returns a additional read write field which uses the name to get and set it
 */
@JvmName("additionalFieldWithReceiverAs")
fun <T> additionalFieldWithReceiver(name: String,
                        receiver: () -> Any) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = receiver().getAdditional<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        receiver().setAdditional(name, value)
    }
}

/**
 * Returns a additional read write field which uses the name to get and set it
 */
fun additionalFieldWithReceiver(name: String,
                                receiver: () -> Any) = additionalFieldWithReceiver<Any>(name, receiver)

/**
 * Returns a nullable additional read write field which uses the name to get and set it
 */
fun nullableAdditionalFieldWithReceiver(name: String,
                                        receiver: () -> Any) = additionalFieldWithReceiver<Any?>(name, receiver)

/**
 * Returns a additional static read write field which uses the name to get and set it
 */
@JvmName("additionalStaticFieldWithReceiverAs")
fun <T> additionalStaticFieldWithReceiver(name: String,
                                          receiver: () -> Any) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = receiver().getAdditionalStatic<T>(name)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        receiver().setAdditionalStatic(name, value)
    }
}

/**
 * Returns a additional read write field which uses the name to get and set it
 */
fun additionalStaticFieldWithReceiver(name: String,
                                      receiver: () -> Any) = additionalStaticFieldWithReceiver<Any>(name, receiver)

/**
 * Returns a nullable additional read write field which uses the name to get and set it
 */
fun nullableAdditionalStaticFieldWithReceiver(name: String,
                                              receiver: () -> Any) = additionalStaticFieldWithReceiver<Any?>(name, receiver)

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

// FUNCTIONS WITH RECEIVER

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("functionWithReceiverAs")
fun <T> functionWithReceiver(name: String,
                             receiver: () -> Any) = object : ReadOnlyProperty<Any, Function<T>> {
    private var function: Function<T>? = null
    override fun getValue(thisRef: Any, property: KProperty<*>): Function<T> {
        if (function == null) {
            function = object : Function<T> {
                override fun invoke(vararg args: Any) = receiver().invoke<T>(name, *args)
            }
        }
        return function!!
    }

}

/**
 * Returns a function which returns the result of name when called
 */
fun functionWithReceiver(name: String,
                         receiver: () -> Any) = functionWithReceiver<Any>(name, receiver)

/**
 * Returns a function which returns the result of name when called which can be null
 */
fun functionWithReceiverSafe(name: String,
                             receiver: () -> Any) = functionWithReceiver<Any?>(name, receiver)

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("functionWithReceiverAs")
fun <T> functionWithReceiver(name: String,
                             parameterTypes: Array<Class<*>>,
                             receiver: () -> Any) = object : ReadOnlyProperty<Any, Function<T>> {
    private var function: Function<T>? = null
    override fun getValue(thisRef: Any, property: KProperty<*>): Function<T> {
        if (function == null) {
            function = object : Function<T> {
                override fun invoke(vararg args: Any) = receiver().invoke<T>(name, parameterTypes, *args)
            }
        }
        return function!!
    }

}

/**
 * Returns a function which returns the result of name when called
 */
fun functionWithReceiver(name: String,
                         parameterTypes: Array<Class<*>>,
                         receiver: () -> Any) = functionWithReceiver<Any>(name, parameterTypes, receiver)

/**
 * Returns a function which returns the result of name when called which can be null
 */
fun functionWithReceiverSafe(name: String,
                             parameterTypes: Array<Class<*>>,
                             receiver: () -> Any) = functionWithReceiver<Any?>(name, parameterTypes, receiver)

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("staticFunctionWithReceiverAs")
fun <T> staticFunctionWithReceiver(name: String,
                                   receiver: () -> Any) = object : ReadOnlyProperty<Any, Function<T>> {
    private var function: Function<T>? = null
    override fun getValue(thisRef: Any, property: KProperty<*>): Function<T> {
        if (function == null) {
            function = object : Function<T> {
                override fun invoke(vararg args: Any) = receiver().invokeStatic<T>(name, *args)
            }
        }
        return function!!
    }

}

/**
 * Returns a function which returns the result of name when called
 */
fun staticFunctionWithReceiver(name: String,
                         receiver: () -> Any) = staticFunctionWithReceiver<Any>(name, receiver)

/**
 * Returns a function which returns the result of name when called which can be null
 */
fun staticFunctionWithReceiverSafe(name: String,
                             receiver: () -> Any) = staticFunctionWithReceiver<Any?>(name, receiver)

/**
 * Returns a read write field which uses the name to get and set it
 */
@JvmName("staticFunctionWithReceiverAs")
fun <T> staticFunctionWithReceiver(name: String,
                             parameterTypes: Array<Class<*>>,
                             receiver: () -> Any) = object : ReadOnlyProperty<Any, Function<T>> {
    private var function: Function<T>? = null
    override fun getValue(thisRef: Any, property: KProperty<*>): Function<T> {
        if (function == null) {
            function = object : Function<T> {
                override fun invoke(vararg args: Any) = receiver().invokeStatic<T>(name, parameterTypes, *args)
            }
        }
        return function!!
    }

}

/**
 * Returns a function which returns the result of name when called
 */
fun staticFunctionWithReceiver(name: String,
                         parameterTypes: Array<Class<*>>,
                         receiver: () -> Any) = staticFunctionWithReceiver<Any>(name, parameterTypes, receiver)

/**
 * Returns a function which returns the result of name when called which can be null
 */
fun staticFunctionWithReceiverSafe(name: String,
                                   parameterTypes: Array<Class<*>>,
                                   receiver: () -> Any) = staticFunctionWithReceiver<Any?>(name, parameterTypes, receiver)