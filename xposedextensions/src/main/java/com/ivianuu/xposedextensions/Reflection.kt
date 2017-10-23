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
inline fun ClassLoader.findClass(className: String): Class<*> =
        XposedHelpers.findClass(className, this)

/**
 * Returns the class with the name or null
 */
inline fun ClassLoader.findNullableClass(className: String): Class<*>?
        = XposedHelpers.findClassIfExists(className, this)

// FIELDS

/**
 * Returns the field with the name
 */
inline fun Any.getField(fieldName: String): Any = XposedHelpers.getObjectField(this, fieldName)

/**
 * Sets the field with the name to the value
 */
inline fun Any.setField(fieldName: String, value: Any) =
        XposedHelpers.setObjectField(this, fieldName, value)

/**
 * Returns the nullable field with the name
 */
inline fun Any.getNullableField(fieldName: String): Any? = XposedHelpers.getObjectField(this, fieldName)

/**
 * Returns the field with the name as T
 */
inline fun <T> Any.getFieldAs(fieldName: String) =
        getField(fieldName) as T

/**
 * Returns the nullable field with the name
 */
inline fun <T> Any.getNullableFieldAs(fieldName: String) =
        getNullableField(fieldName) as T

/**
 * Returns the boolean field with the name
 */
inline fun Any.getBooleanField(fieldName: String) = getFieldAs<Boolean>(fieldName)

/**
 * Returns the byte field with the name
 */
inline fun Any.getByteField(fieldName: String) = getFieldAs<Byte>(fieldName)

/**
 * Returns the char field with the name
 */
inline fun Any.getCharField(fieldName: String) = getFieldAs<Char>(fieldName)

/**
 * Returns the double field with the name
 */
inline fun Any.getDoubleField(fieldName: String) = getFieldAs<Double>(fieldName)

/**
 * Returns the float field with the name
 */
inline fun Any.getFloatField(fieldName: String) = getFieldAs<Float>(fieldName)

/**
 * Returns the int field with the name
 */
inline fun Any.getIntField(fieldName: String) = getFieldAs<Int>(fieldName)

/**
 * Returns the long field with the name
 */
inline fun Any.getLongField(fieldName: String) = getFieldAs<Long>(fieldName)

/**
 * Returns the short field with the name
 */
inline fun Any.getShortField(fieldName: String) = getFieldAs<Short>(fieldName)

/**
 * Returns the string field with the name
 */
inline fun Any.getStringField(fieldName: String) = getFieldAs<String>(fieldName)

// STATIC FIELDS

/**
 * Returns the field with the name
 */
inline fun Class<*>.getStaticField(fieldName: String): Any 
        = XposedHelpers.getStaticObjectField(this, fieldName)

/**
 * Sets the field with the name to the value
 */
inline fun Class<*>.setStaticField(fieldName: String, value: Any) =
        XposedHelpers.setStaticObjectField(this, fieldName, value)

/**
 * Returns the nullable field with the name
 */
inline fun Class<*>.getStaticNullableField(fieldName: String): Any? =
        XposedHelpers.getStaticObjectField(this, fieldName)

/**
 * Returns the field with the name as T
 */
inline fun <T> Class<*>.getStaticFieldAs(fieldName: String) =
        getStaticField(fieldName) as T

/**
 * Returns the nullable field with the name
 */
inline fun <T> Class<*>.getStaticNullableFieldAs(fieldName: String) =
        getStaticNullableField(fieldName) as T

/**
 * Returns the boolean field with the name
 */
inline fun Class<*>.getStaticBooleanField(fieldName: String) =
        getStaticFieldAs<Boolean>(fieldName)

/**
 * Returns the byte field with the name
 */
inline fun Class<*>.getStaticByteField(fieldName: String) =
        getStaticFieldAs<Byte>(fieldName)

/**
 * Returns the char field with the name
 */
inline fun Class<*>.getStaticCharField(fieldName: String) =
        getStaticFieldAs<Char>(fieldName)

/**
 * Returns the double field with the name
 */
inline fun Class<*>.getStaticDoubleField(fieldName: String) =
        getStaticFieldAs<Double>(fieldName)

/**
 * Returns the float field with the name
 */
inline fun Class<*>.getStaticFloatField(fieldName: String) =
        getStaticFieldAs<Float>(fieldName)

/**
 * Returns the int field with the name
 */
inline fun Class<*>.getStaticIntField(fieldName: String) =
        getStaticFieldAs<Int>(fieldName)

/**
 * Returns the long field with the name
 */
inline fun Class<*>.getStaticLongField(fieldName: String) =
        getStaticFieldAs<Long>(fieldName)

/**
 * Returns the short field with the name
 */
inline fun Class<*>.getStaticShortField(fieldName: String) =
        getStaticFieldAs<Short>(fieldName)

/**
 * Returns the string field with the name
 */
inline fun Class<*>.getStaticStringField(fieldName: String) =
        getStaticFieldAs<String>(fieldName)

// METHODS

/**
 * Calls the method with the name and the args
 */
inline fun Any.callMethod(methodName: String,
                          vararg args: Any) = XposedHelpers.callMethod(this, methodName, args)

/**
 * Calls the method with the name and the args
 */
inline fun Any.callMethod(methodName: String,
                          parameterTypes: Array<Class<*>>,
                          vararg args: Any) =
        XposedHelpers.callMethod(this, methodName, parameterTypes, args)

/**
 * Calls the static method with the name and the args
 */
inline fun Class<*>.callStaticMethod(methodName: String,
                                     vararg args: Any) =
        XposedHelpers.callStaticMethod(this, methodName, args)

/**
 * Calls the static method with the name and the args
 */
inline fun Class<*>.callStaticMethod(methodName: String,
                          parameterTypes: Array<Class<*>>,
                          vararg args: Any) =
        XposedHelpers.callStaticMethod(this, methodName, parameterTypes, args)