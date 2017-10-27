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
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

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
inline fun <T> Any.new(vararg args: Any?) =
        XposedHelpers.newInstance(toJavaClass(), *args) as T

/**
 * Returns a new instance of this class
 */
inline fun <T> Any.new(parameterTypes: Array<Class<*>>,
                       vararg args: Any?) =
        XposedHelpers.newInstance(toJavaClass(), parameterTypes, *args) as T

// FIELDS

/**
 * Returns the field with the name
 */
@JvmName("getAs")
inline fun <T> Any.get(fieldName: String) =
        XposedHelpers.getObjectField(this, fieldName) as T

/**
 * Sets the field with the name to the value
 */
inline fun Any.set(fieldName: String, value: Any?) =
        XposedHelpers.setObjectField(this, fieldName, value)

// STATIC FIELDS

/**
 * Returns the field with the name
 */
inline fun <T> Any.getStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(toJavaClass(), fieldName) as T
/**
 * Sets the field with the name to the value
 */
inline fun Any.setStatic(fieldName: String, value: Any?) =
        XposedHelpers.setStaticObjectField(toJavaClass(), fieldName, value)

// ADDITIONAL

/**
 * Returns the field with the name
 */
inline fun <T> Any.getAdditional(fieldName: String) =
        XposedHelpers.getAdditionalInstanceField(this, fieldName) as T

/**
 * Sets the field with the name to the value
 */
inline fun Any.setAdditional(fieldName: String, value: Any?) {
    XposedHelpers.setAdditionalInstanceField(this, fieldName, value)
}

/**
 * Removes the additional field with the name
 */
inline fun Any.removeAdditional(fieldName: String) {
    XposedHelpers.removeAdditionalInstanceField(this, fieldName)
}

// ADDITIONAL STATIC

/**
 * Returns the field with the name
 */
inline fun <T> Any.getAdditionalStatic(fieldName: String)
        = XposedHelpers.getAdditionalStaticField(toJavaClass(), fieldName) as T

/**
 * Sets the field with the name to the value
 */
inline fun Any.setAdditionalStatic(fieldName: String, value: Any?) {
    XposedHelpers.setAdditionalStaticField(toJavaClass(), fieldName, value)
}

/**
 * Removes the additional field with the name
 */
inline fun Any.removeAdditionalStatic(fieldName: String) {
    XposedHelpers.removeAdditionalStaticField(toJavaClass(), fieldName)
}

// METHODS

/**
 * Calls the method with the name and the args
 */
inline fun <T> Any.invoke(methodName: String,
                          vararg args: Any?) =
        XposedHelpers.callMethod(this, methodName, *args) as T

/**
 * Calls the method with the name and the args
 */
inline fun <T> Any.invoke(methodName: String,
                          parameterTypes: Array<Class<*>>,
                          vararg args: Any?) =
        XposedHelpers.callMethod(this, methodName, parameterTypes, *args) as T

/**
 * Calls the static method with the name and the args
 */
inline fun <T> Any.invokeStatic(methodName: String,
                                vararg args: Any?) =
        XposedHelpers.callStaticMethod(this::class.java, methodName, *args) as T

/**
 * Calls the static method with the name and the args
 */
inline fun <T> Any.invokeStatic(methodName: String,
                                parameterTypes: Array<Class<*>>,
                                vararg args: Any?) =
        XposedHelpers.callStaticMethod(toJavaClass(), methodName, parameterTypes, *args) as T


// DELEGATES

/**
 * Returns a read write field which uses the fieldName to get and set it
 */
fun <T : Any> Any.field(fieldName: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.get<T>(fieldName)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.set(fieldName, value)
    }
}

/**
 * Returns a static read write field which uses the fieldName to get and set it
 */
fun <T> Any.staticField(fieldName: String) =  object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = thisRef.get<T>(fieldName)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.set(fieldName, value)
    }
}

/**
 * Returns a additional read write field which uses the fieldName to get and set it
 */
fun <T> Any.additionalField(fieldName: String) = object : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T =
            thisRef.getAdditional(fieldName)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setAdditional(fieldName, value)
    }
}

/**
 * Returns a additional static read write field which uses the fieldName to get and set it
 */
fun <T> Any.additionalStaticField(fieldName: String) = object : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T =
            thisRef.getAdditionalStatic(fieldName)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        thisRef.setAdditionalStatic(fieldName, value)
    }
}

fun Any.toJavaClass(): Class<*> = when {
    this is Class<*> -> this
    this is KClass<*> -> this.java
    else -> this::class.java
}