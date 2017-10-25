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
inline fun Any.getField(fieldName: String) =
        XposedHelpers.getObjectField(this, fieldName)

/**
 * Returns the field with the name
 */
inline fun <T> Any.getFieldAs(fieldName: String) =
        XposedHelpers.getObjectField(this, fieldName) as T

/**
 * Returns the nullable field with the name
 */
inline fun Any.getNullableField(fieldName: String): Any? =
        XposedHelpers.getObjectField(this, fieldName)

/**
 * Returns the nullable field with the name
 */
inline fun <T> Any.getNullableFieldAs(fieldName: String) =
        XposedHelpers.getObjectField(this, fieldName) as T?

/**
 * Returns the additional field with the name
 */
inline fun Any.getAdditionalField(fieldName: String) =
        XposedHelpers.getAdditionalInstanceField(this, fieldName)

/**
 * Returns the additional field with the name as t
 */
inline fun <T> Any.getAdditionalFieldAs(fieldName: String) =
        getAdditionalField(fieldName)

/**
 * Sets the field with the name to the value
 */
inline fun Any.setField(fieldName: String, value: Any?) =
        XposedHelpers.setObjectField(this, fieldName, value)

/**
 * Sets the additional field with the name to the value
 */
inline fun Any.setAdditionalField(fieldName: String, value: Any?) =
        XposedHelpers.setAdditionalInstanceField(this, fieldName, value)

/**
 * Removes the the additional field
 */
inline fun Any.removeAdditionalField(fieldName: String) =
        XposedHelpers.removeAdditionalInstanceField(this, fieldName)

/**
 *  Returns the this reference of the surrounding object
 */
fun Any.getSurroundingThis() = XposedHelpers.getSurroundingThis(this)

/**
 *  Returns the this reference of the surrounding object
 */
fun <T> Any.getSurroundingThisAs() = getSurroundingThis() as T

// STATIC FIELDS

/**
 * Returns the field with the name
 */
inline fun Class<*>.getStaticField(fieldName: String)
        = XposedHelpers.getStaticObjectField(this, fieldName)

/**
 * Returns the field with the name
 */
inline fun Any.getStaticField(fieldName: String)
        = this::class.java.getStaticField(fieldName)

/**
 * Returns the field with the name
 */
inline fun <T> Class<*>.getStaticFieldAs(fieldName: String)
        = XposedHelpers.getStaticObjectField(this, fieldName) as T

/**
 * Returns the field with the name
 */
inline fun <T> Any.getStaticFieldAs(fieldName: String)
        = this::class.java.getStaticField(fieldName) as T

/**
 * Returns the field with the name
 */
inline fun Class<*>.getNullableStaticField(fieldName: String): Any?
        = XposedHelpers.getStaticObjectField(this, fieldName)

/**
 * Returns the field with the name
 */
inline fun <T> Class<*>.getNullableStaticFieldAs(fieldName: String)
        = XposedHelpers.getStaticObjectField(this, fieldName) as T?

/**
 * Returns the additional field with the name
 */
inline fun Any.getAdditionalStaticField(fieldName: String) =
        XposedHelpers.getAdditionalStaticField(this, fieldName)

/**
 * Returns the additional field with the name as t
 */
inline fun <T> Any.getAdditionalStaticFieldAs(fieldName: String) =
        getAdditionalStaticField(fieldName)

/**
 * Sets the field with the name to the value
 */
inline fun Class<*>.setStaticField(fieldName: String, value: Any) =
        XposedHelpers.setStaticObjectField(this, fieldName, value)

/**
 * Sets the additional field with the name to the value
 */
inline fun Any.setAdditionalStaticField(fieldName: String, value: Any?) =
        XposedHelpers.setAdditionalStaticField(this, fieldName, value)

/**
 * Removes the the additional field
 */
inline fun Any.removeAdditionalStaticField(fieldName: String) =
        XposedHelpers.removeAdditionalStaticField(this, fieldName)

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