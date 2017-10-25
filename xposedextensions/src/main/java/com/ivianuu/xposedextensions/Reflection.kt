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
inline fun Class<*>.new(parameterTypes: Array<Class<*>>,
                        vararg args: Any) =
        XposedHelpers.newInstance(this, parameterTypes, *args)

// FIELDS

/**
 * Returns the field with the name
 */
inline fun Any.get(fieldName: String) =
        XposedHelpers.getObjectField(this, fieldName)

/**
 * Returns the field with the name
 */
inline fun <T> Any.getAs(fieldName: String) =
        XposedHelpers.getObjectField(this, fieldName) as T

/**
 * Returns the nullable field with the name
 */
inline fun Any.getOptional(fieldName: String): Any? =
        XposedHelpers.getObjectField(this, fieldName)

/**
 * Returns the nullable field with the name
 */
inline fun <T> Any.getOptionalAs(fieldName: String) =
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
inline fun Any.getStatic(fieldName: String)
        = this::class.java.getStatic(fieldName)

/**
 * Returns the field with the name
 */
inline fun <T> Class<*>.getStaticAs(fieldName: String)
        = XposedHelpers.getStaticObjectField(this, fieldName) as T

/**
 * Returns the field with the name
 */
inline fun <T> Any.getStaticAs(fieldName: String)
        = this::class.java.getStatic(fieldName) as T

/**
 * Returns the field with the name
 */
inline fun Class<*>.getOptionalStatic(fieldName: String): Any?
        = XposedHelpers.getStaticObjectField(this, fieldName)

/**
 * Returns the field with the name
 */
inline fun <T> Class<*>.getOptionalStaticAs(fieldName: String)
        = XposedHelpers.getStaticObjectField(this, fieldName) as T?

/**
 * Sets the field with the name to the value
 */
inline fun Class<*>.setStatic(fieldName: String, value: Any) =
        XposedHelpers.setStaticObjectField(this, fieldName, value)

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
inline fun Any.invoke(methodName: String,
                      parameterTypes: Array<Class<*>>,
                      vararg args: Any) =
        XposedHelpers.callMethod(this, methodName, parameterTypes, *args)

/**
 * Calls the static method with the name and the args
 */
inline fun Class<*>.invokeStatic(methodName: String,
                                 vararg args: Any) =
        XposedHelpers.callStaticMethod(this, methodName, *args)

/**
 * Calls the static method with the name and the args
 */
inline fun Class<*>.invokeStatic(methodName: String,
                                 parameterTypes: Array<Class<*>>,
                                 vararg args: Any) =
        XposedHelpers.callStaticMethod(this, methodName, parameterTypes, *args)