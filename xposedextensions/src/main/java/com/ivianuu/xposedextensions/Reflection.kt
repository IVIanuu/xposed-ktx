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

import de.robv.android.xposed.XposedHelpers
import kotlin.reflect.KClass

// CLASS

/**
 * Returns the class with the name
 */
inline fun ClassLoader.find(className: String): Class<*> =
        XposedHelpers.findClass(className, this)

/**
 * Returns the class with the name or null
 */
inline fun ClassLoader.findOptional(className: String): Class<*>?
        = XposedHelpers.findClassIfExists(className, this)

/**
 * Returns a new instance of this class
 */
inline fun Class<*>.new(vararg args: Any) =
        XposedHelpers.newInstance(this, *args)

/**
 * Returns a new instance of this class
 */
@JvmName("newAs")
inline fun <T> Class<T>.new(vararg args: Any) =
        XposedHelpers.newInstance(this, *args) as T

/**
 * Returns a new instance of this class
 */
inline fun Class<*>.new(parameterTypes: Array<Class<*>>,
                        vararg args: Any) =
        XposedHelpers.newInstance(this, parameterTypes, *args)

/**
 * Returns a new instance of this class
 */
@JvmName("newAs")
inline fun <T> Class<T>.new(parameterTypes: Array<Class<*>>,
                        vararg args: Any) =
        XposedHelpers.newInstance(this, parameterTypes, *args) as T

/**
 * Returns a new instance of this class
 */
inline fun KClass<*>.new(vararg args: Any) =
        XposedHelpers.newInstance(this.java, *args)

/**
 * Returns a new instance of this class
 */
@JvmName("newAs")
inline fun <T : Any> KClass<T>.new(vararg args: Any) =
        XposedHelpers.newInstance(this.java, *args) as T

/**
 * Returns a new instance of this class
 */
inline fun KClass<*>.new(parameterTypes: Array<Class<*>>,
                         vararg args: Any) =
        XposedHelpers.newInstance(this.java, parameterTypes, *args)

/**
 * Returns a new instance of this class
 */
@JvmName("newAs")
inline fun <T : Any> KClass<T>.new(parameterTypes: Array<Class<*>>,
                                   vararg args: Any) =
        XposedHelpers.newInstance(this.java, parameterTypes, *args) as T

// FIELDS

/**
 * Returns the field with the name
 */
inline fun Any.get(fieldName: String) =
        XposedHelpers.getObjectField(this, fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getAs")
inline fun <T> Any.get(fieldName: String) =
        XposedHelpers.getObjectField(this, fieldName) as T

/**
 * Returns the nullable field with the name
 */
inline fun Any.getOptional(fieldName: String): Any? =
        XposedHelpers.getObjectField(this, fieldName)

/**
 * Returns the nullable field with the name
 */
@JvmName("getOptionalAs")
inline fun <T> Any.getOptional(fieldName: String) =
        XposedHelpers.getObjectField(this, fieldName) as T?

/**
 * Sets the field with the name to the value
 */
inline fun Any.set(fieldName: String, value: Any?) =
        XposedHelpers.setObjectField(this, fieldName, value)

// STATIC FIELDS

/**
 * Returns the field with the name
 */
inline fun Class<*>.getStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(this, fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getStaticAs")
inline fun <T> Class<*>.getStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(this, fieldName) as T

/**
 * Returns the field with the name
 */
inline fun KClass<*>.getStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(this.java, fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getStaticAs")
inline fun <T> KClass<*>.getStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(this.java, fieldName) as T

/**
 * Returns the field with the name
 */
inline fun Any.getStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(this::class.java, fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getStaticAs")
inline fun <T> Any.getStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(this::class.java, fieldName) as T

/**
 * Returns the field with the name
 */
inline fun Class<*>.getOptionalStatic(fieldName: String): Any?
        = XposedHelpers.getStaticObjectField(this, fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getOptionalStaticAs")
inline fun <T> Class<*>.getOptionalStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(this, fieldName) as T?

/**
 * Returns the field with the name
 */
inline fun KClass<*>.getOptionalStatic(fieldName: String): Any?
        = XposedHelpers.getStaticObjectField(this.java, fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getOptionalStaticAs")
inline fun <T> KClass<*>.getOptionalStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(this.java, fieldName) as T?

/**
 * Sets the field with the name to the value
 */
inline fun Class<*>.setStatic(fieldName: String, value: Any?) =
        XposedHelpers.setStaticObjectField(this, fieldName, value)

/**
 * Sets the field with the name to the value
 */
inline fun KClass<*>.setStatic(fieldName: String, value: Any?) =
        XposedHelpers.setStaticObjectField(this.java, fieldName, value)

// METHODS

/**
 * Calls the method with the name and the args
 */
inline fun Any.invoke(methodName: String,
                      vararg args: Any) =
        XposedHelpers.callMethod(this, methodName, *args)

/**
 * Calls the method with the name and the args
 */
@JvmName("invokeAs")
inline fun <T> Any.invoke(methodName: String,
                          vararg args: Any) =
        XposedHelpers.callMethod(this, methodName, *args) as T

/**
 * Calls the method with the name and the args
 */
inline fun Any.invoke(methodName: String,
                          parameterTypes: Array<Class<*>>,
                          vararg args: Any) =
        XposedHelpers.callMethod(this, methodName, parameterTypes, *args)

/**
 * Calls the method with the name and the args
 */
@JvmName("invokeAs")
inline fun <T> Any.invoke(methodName: String,
                          parameterTypes: Array<Class<*>>,
                          vararg args: Any) =
        XposedHelpers.callMethod(this, methodName, parameterTypes, *args) as T

/**
 * Calls the static method with the name and the args
 */
inline fun Class<*>.invokeStatic(methodName: String,
                                 vararg args: Any) =
        XposedHelpers.callStaticMethod(this, methodName, *args)

/**
 * Calls the static method with the name and the args
 */
@JvmName("invokeStaticAs")
inline fun <T> Class<*>.invokeStatic(methodName: String,
                                     vararg args: Any) =
        XposedHelpers.callStaticMethod(this, methodName, *args) as T

/**
 * Calls the static method with the name and the args
 */
inline fun Class<*>.invokeStatic(methodName: String,
                                     parameterTypes: Array<Class<*>>,
                                     vararg args: Any) =
        XposedHelpers.callStaticMethod(this, methodName, parameterTypes, *args)

/**
 * Calls the static method with the name and the args
 */
@JvmName("invokeStaticAs")
inline fun <T> Class<*>.invokeStatic(methodName: String,
                                     parameterTypes: Array<Class<*>>,
                                     vararg args: Any) =
        XposedHelpers.callStaticMethod(this, methodName, parameterTypes, *args) as T

/**
 * Calls the static method with the name and the args
 */
inline fun KClass<*>.invokeStatic(methodName: String,
                                  vararg args: Any) =
        XposedHelpers.callStaticMethod(this.java, methodName, *args)

/**
 * Calls the static method with the name and the args
 */
@JvmName("invokeStaticAs")
inline fun <T> KClass<*>.invokeStatic(methodName: String,
                                      vararg args: Any) =
        XposedHelpers.callStaticMethod(this.java, methodName, *args) as T

/**
 * Calls the static method with the name and the args
 */
inline fun KClass<*>.invokeStatic(methodName: String,
                                      parameterTypes: Array<Class<*>>,
                                      vararg args: Any) =
        XposedHelpers.callStaticMethod(this.java, methodName, parameterTypes, *args)

/**
 * Calls the static method with the name and the args
 */
@JvmName("invokeStaticAs")
inline fun <T> KClass<*>.invokeStatic(methodName: String,
                                      parameterTypes: Array<Class<*>>,
                                      vararg args: Any) =
        XposedHelpers.callStaticMethod(this.java, methodName, parameterTypes, *args) as T