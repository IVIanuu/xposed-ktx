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

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.ivianuu.xposedextensions

import de.robv.android.xposed.XposedHelpers
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

// CLASS

/**
 * Returns the class with the name
 */
inline fun ClassLoader.findClass(className: String): Class<*> =
        XposedHelpers.findClass(className, this)

/**
 * Returns the class with the name or null
 */
inline fun ClassLoader.findClassIfExists(className: String): Class<*>?
        = XposedHelpers.findClassIfExists(className, this)

/**
 * Returns a new instance of this class
 */
inline fun <T> Any.newInstance(vararg args: Any?) =
        XposedHelpers.newInstance(toJavaClass(), *args) as T

/**
 * Returns a new instance of this class
 */
inline fun <T> Any.newInstance(parameterTypes: Array<Class<*>>,
                               vararg args: Any?) =
        XposedHelpers.newInstance(toJavaClass(), parameterTypes, *args) as T

// FIELDS

/**
 * Returns the field with the name
 */
@JvmName("getFieldAs")
inline fun <T> Any.getField(name: String) = XposedHelpers.getObjectField(this, name) as T

/**
 * Returns the field with the name
 */
inline fun Any.getField(name: String) = getField<Any>(name)

/**
 * Returns the field with the name which can be null
 */
inline fun Any.getSafe(name: String) = getField<Any?>(name)

/**
 * Sets the field with the name to the value
 */
inline fun Any.setField(name: String, value: Any?) =
        XposedHelpers.setObjectField(this, name, value)

// STATIC FIELDS

/**
 * Returns the static field with the name
 */
@JvmName("getStaticFieldAs")
inline fun <T> Any.getStaticField(name: String) = XposedHelpers.getStaticObjectField(toJavaClass(), name) as T

/**
 * Returns the static field with the name
 */
inline fun Any.getStaticField(name: String) = getStaticField<Any>(name)

/**
 * Returns the static field with the name which can be null
 */
inline fun Any.getStaticFieldSafe(name: String) = getStaticField<Any?>(name)

/**
 * Sets the field with the name to the value
 */
inline fun Any.setStaticField(name: String, value: Any?) =
        XposedHelpers.setStaticObjectField(toJavaClass(), name, value)

// ADDITIONAL

/**
 * Returns the additional field with the name
 */
@JvmName("getAdditionalFieldAs")
inline fun <T> Any.getAdditionalField(name: String) =
        XposedHelpers.getAdditionalInstanceField(this, name) as T

/**
 * Returns the additional static field with the name
 */
inline fun Any.getAdditionalField(name: String) = getAdditionalField<Any>(name)

/**
 * Returns the additional static field with the name
 */
inline fun Any.getAdditionalFieldSafe(name: String) = getAdditionalField<Any?>(name)

/**
 * Sets the field with the name to the value
 */
inline fun Any.setAdditionalField(name: String, value: Any?) {
    XposedHelpers.setAdditionalInstanceField(this, name, value)
}

/**
 * Removes the additional field with the name
 */
inline fun Any.removeAdditionalField(name: String) {
    XposedHelpers.removeAdditionalInstanceField(this, name)
}

// ADDITIONAL STATIC

/**
 * Returns the additional static field with the name
 */
@JvmName("getAdditionalStaticFieldAs")
inline fun <T> Any.getAdditionalStaticField(name: String) =
        XposedHelpers.getAdditionalStaticField(toJavaClass(), name) as T

/**
 * Returns the additional static field with the name
 */
inline fun Any.getAdditionalStaticField(name: String) = getAdditionalStaticField<Any>(name)

/**
 * Returns the additional static field with the name which can be null
 */
inline fun Any.getAdditionalStaticFieldSafe(name: String) = getAdditionalStaticField<Any?>(name)

/**
 * Sets the additional static field with the name to the value
 */
inline fun Any.setAdditionalStaticField(name: String, value: Any?) {
    XposedHelpers.setAdditionalStaticField(toJavaClass(), name, value)
}

/**
 * Removes the additional static field with the name
 */
inline fun Any.removeAdditionalStaticField(name: String) {
    XposedHelpers.removeAdditionalStaticField(toJavaClass(), name)
}

// METHODS

/**
 * Calls the method with the name and the args and returns the result
 */
@JvmName("invokeFunctionAs")
inline fun <T> Any.invokeFunction(name: String,
                          vararg args: Any?) =
        XposedHelpers.callMethod(this, name, *args) as T

/**
 * Calls the method with the name and the args and returns the result
 */
inline fun Any.invokeFunction(name: String, vararg args: Any?) = invokeFunction<Any>(name, *args)

/**
 * Calls the method with the name and the args and returns the result which can be null
 */
inline fun Any.invokeFunctionSafe(name: String, vararg args: Any?) = invokeFunction<Any?>(name, *args)

/**
 * Calls the method with the name, the param types and the args and returns the result
 */
@JvmName("invokeFunctionAs")
inline fun <T> Any.invokeFunction(name: String,
                          parameterTypes: Array<Class<*>>,
                          vararg args: Any?) =
        XposedHelpers.callMethod(this, name, parameterTypes, *args) as T

/**
 * Calls the method with the name, the param types and the args and returns the result
 */
inline fun Any.invokeFunction(name: String,
                      parameterTypes: Array<Class<*>>,
                      vararg args: Any?) = invokeFunction<Any>(name, parameterTypes, *args)

/**
 * Calls the method with the name, the param types and the args and returns the result which can be null
 */
inline fun Any.invokeFunctionSafe(name: String,
                      parameterTypes: Array<Class<*>>,
                      vararg args: Any?) = invokeFunction<Any?>(name, parameterTypes, *args)

/**
 * Calls the static method with the name and the args and returns the result
 */
@JvmName("invokeStaticFunctionAs")
inline fun <T> Any.invokeStaticFunction(name: String,
                                vararg args: Any?) =
        XposedHelpers.callStaticMethod(toJavaClass(), name, *args) as T

/**
 * Calls the static method with the name and the args and returns the result
 */
inline fun Any.invokeStaticFunction(name: String,
                            vararg args: Any?) = invokeStaticFunction<Any>(name, *args)

/**
 * Calls the static method with the name and the args and returns the result which can be null
 */
inline fun Any.invokeStaticFunctionSafe(name: String,
                            vararg args: Any?) = invokeStaticFunction<Any?>(name, *args)

/**
 * Calls the static method with the name and the args and returns the result
 */
@JvmName("invokeStaticFunctionAs")
inline fun <T> Any.invokeStaticFunction(name: String,
                                parameterTypes: Array<Class<*>>,
                                vararg args: Any?) =
        XposedHelpers.callStaticMethod(toJavaClass(), name, parameterTypes, *args) as T

/**
 * Calls the static method with the name and the args and returns the result
 */
inline fun Any.invokeStaticFunction(name: String,
                            parameterTypes: Array<Class<*>>,
                            vararg args: Any?) = invokeStaticFunction<Any>(name, parameterTypes, *args)

/**
 * Calls the static method with the name and the args and returns the result which can be null
 */
inline fun Any.invokeStaticFunctionSafe(name: String,
                            parameterTypes: Array<Class<*>>,
                            vararg args: Any?) = invokeStaticFunction<Any>(name, parameterTypes, *args)

// HELPER

fun Any.toJavaClass(): Class<*> = when {
    this is Class<*> -> this
    this is KClass<*> -> this.java
    else -> this::class.java
}
