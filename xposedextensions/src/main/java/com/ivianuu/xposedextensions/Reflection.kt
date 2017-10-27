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
inline fun Class<*>.new(vararg args: Any?) =
        XposedHelpers.newInstance(this, *args)

/**
 * Returns a new instance of this class
 */
@JvmName("newAs")
inline fun <T> Class<T>.new(vararg args: Any?) =
        XposedHelpers.newInstance(this, *args) as T

/**
 * Returns a new instance of this class
 */
inline fun Class<*>.new(parameterTypes: Array<Class<*>>,
                        vararg args: Any?) =
        XposedHelpers.newInstance(this, parameterTypes, *args)

/**
 * Returns a new instance of this class
 */
@JvmName("newAs")
inline fun <T> Class<T>.new(parameterTypes: Array<Class<*>>,
                            vararg args: Any?) =
        XposedHelpers.newInstance(this, parameterTypes, *args) as T

/**
 * Returns a new instance of this class
 */
inline fun KClass<*>.new(vararg args: Any?) =
        XposedHelpers.newInstance(this.java, *args)

/**
 * Returns a new instance of this class
 */
@JvmName("newAs")
inline fun <T : Any> KClass<T>.new(vararg args: Any?) =
        XposedHelpers.newInstance(this.java, *args) as T

/**
 * Returns a new instance of this class
 */
inline fun KClass<*>.new(parameterTypes: Array<Class<*>>,
                         vararg args: Any?) =
        XposedHelpers.newInstance(this.java, parameterTypes, *args)

/**
 * Returns a new instance of this class
 */
@JvmName("newAs")
inline fun <T : Any> KClass<T>.new(parameterTypes: Array<Class<*>>,
                                   vararg args: Any?) =
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
inline fun Any.getStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(toJavaClass(), fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getStaticAs")
inline fun <T> Any.getStatic(fieldName: String)
        = XposedHelpers.getStaticObjectField(toJavaClass(), fieldName) as T

/**
 * Returns the field with the name
 */
inline fun Any.getStaticOptional(fieldName: String): Any?
        = XposedHelpers.getStaticObjectField(toJavaClass(), fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getStaticOptionalAs")
inline fun <T> Any.getStaticOptional(fieldName: String)
        = XposedHelpers.getStaticObjectField(toJavaClass(), fieldName) as T?

/**
 * Sets the field with the name to the value
 */
inline fun Any.setStatic(fieldName: String, value: Any?) =
        XposedHelpers.setStaticObjectField(toJavaClass(), fieldName, value)

// ADDITIONAL

/**
 * Returns the field with the name
 */
inline fun Any.getAdditional(fieldName: String) =
        XposedHelpers.getAdditionalInstanceField(this, fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getAdditionalAs")
inline fun <T> Any.getAdditional(fieldName: String) =
        XposedHelpers.getAdditionalInstanceField(this, fieldName) as T

/**
 * Returns the nullable field with the name
 */
inline fun Any.getAdditionalOptional(fieldName: String): Any? =
        XposedHelpers.getAdditionalInstanceField(this, fieldName)

/**
 * Returns the nullable field with the name
 */
@JvmName("getAdditionalOptionalAs")
inline fun <T> Any.getAdditionalOptional(fieldName: String) =
        XposedHelpers.getAdditionalInstanceField(this, fieldName) as T?

/**
 * Sets the field with the name to the value
 */
inline fun Any.setAdditional(fieldName: String, value: Any?) =
        XposedHelpers.setAdditionalInstanceField(this, fieldName, value)

/**
 * Removes the additional field with the name
 */
inline fun Any.removeAdditional(fieldName: String) =
        XposedHelpers.removeAdditionalInstanceField(this, fieldName)

// ADDITIONAL STATIC

/**
 * Returns the field with the name
 */
inline fun Any.getAdditionalStatic(fieldName: String)
        = XposedHelpers.getAdditionalStaticField(toJavaClass(), fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getAdditionalStaticAs")
inline fun <T> Any.getAdditionalStatic(fieldName: String)
        = XposedHelpers.getAdditionalStaticField(toJavaClass(), fieldName) as T

/**
 * Returns the field with the name
 */
inline fun Any.getAdditionalStaticOptional(fieldName: String): Any?
        = XposedHelpers.getAdditionalStaticField(toJavaClass(), fieldName)

/**
 * Returns the field with the name
 */
@JvmName("getAdditionalStaticOptionalAs")
inline fun <T> Any.getAdditionalStaticOptional(fieldName: String)
        = XposedHelpers.getAdditionalStaticField(toJavaClass(), fieldName) as T?

/**
 * Sets the field with the name to the value
 */
inline fun Any.setAdditionalStatic(fieldName: String, value: Any?) =
        XposedHelpers.setAdditionalStaticField(toJavaClass(), fieldName, value)

/**
 * Removes the additional field with the name
 */
inline fun Any.removeAdditionalStatic(fieldName: String) =
        XposedHelpers.removeAdditionalStaticField(toJavaClass(), fieldName)

// METHODS

/**
 * Calls the method with the name and the args
 */
inline fun Any.invoke(methodName: String,
                      vararg args: Any?) =
        XposedHelpers.callMethod(this, methodName, *args)

/**
 * Calls the method with the name and the args
 */
@JvmName("invokeAs")
inline fun <T> Any.invoke(methodName: String,
                          vararg args: Any?) =
        XposedHelpers.callMethod(this, methodName, *args) as T

/**
 * Calls the method with the name and the args
 */
inline fun Any.invoke(methodName: String,
                      parameterTypes: Array<Class<*>>,
                      vararg args: Any?) =
        XposedHelpers.callMethod(this, methodName, parameterTypes, *args)

/**
 * Calls the method with the name and the args
 */
@JvmName("invokeAs")
inline fun <T> Any.invoke(methodName: String,
                          parameterTypes: Array<Class<*>>,
                          vararg args: Any?) =
        XposedHelpers.callMethod(this, methodName, parameterTypes, *args) as T

/**
 * Calls the static method with the name and the args
 */
inline fun Any.invokeStatic(methodName: String,
                            vararg args: Any?) =
        XposedHelpers.callStaticMethod(this::class.java, methodName, *args)

/**
 * Calls the static method with the name and the args
 */
@JvmName("invokeStaticAs")
inline fun <T> Any.invokeStatic(methodName: String,
                                vararg args: Any?) =
        XposedHelpers.callStaticMethod(this::class.java, methodName, *args) as T

/**
 * Calls the static method with the name and the args
 */
inline fun Any.invokeStatic(methodName: String,
                            parameterTypes: Array<Class<*>>,
                            vararg args: Any?) =
        XposedHelpers.callStaticMethod(this::class.java, methodName, parameterTypes, *args)

/**
 * Calls the static method with the name and the args
 */
@JvmName("invokeStaticAs")
inline fun <T> Any.invokeStatic(methodName: String,
                                parameterTypes: Array<Class<*>>,
                                vararg args: Any?) =
        XposedHelpers.callStaticMethod(toJavaClass(), methodName, parameterTypes, *args) as T


// DELEGATES

/**
 * Returns a read write field which uses the fieldName to get and set it
 */
fun field(fieldName: String) = Field<Any>(fieldName)

/**
 * Returns a read write field which uses the fieldName to get and set it
 */
@JvmName("fieldAs")
fun <T : Any> field(fieldName: String) = Field<T>(fieldName)

/**
 * Returns a read write field which uses the fieldName to get and set it
 */
fun optionalField(fieldName: String) = Field<Any?>(fieldName)

/**
 * Returns a read write field which uses the fieldName to get and set it
 */
@JvmName("optionalFieldAs")
fun <T : Any> optionalField(fieldName: String) = Field<T?>(fieldName)

/**
 * Returns a static read write field which uses the fieldName to get and set it
 */
fun staticField(fieldName: String) = StaticField<Any>(fieldName)

/**
 * Returns a static read write field which uses the fieldName to get and set it
 */
@JvmName("fieldAs")
fun <T : Any> staticField(fieldName: String) = StaticField<T>(fieldName)

/**
 * Returns a static read write field which uses the fieldName to get and set it
 */
fun optionalStaticField(fieldName: String) = StaticField<Any?>(fieldName)

/**
 * Returns a static read write field which uses the fieldName to get and set it
 */
@JvmName("staticOptionalFieldAs")
fun <T : Any> optionalStaticField(fieldName: String) = StaticField<T?>(fieldName)

/**
 * Returns a additional read write field which uses the fieldName to get and set it
 */
fun additionalField(fieldName: String) = AdditionalField<Any>(fieldName)

/**
 * Returns a additional read write field which uses the fieldName to get and set it
 */
@JvmName("addtionalFieldAs")
fun <T : Any> additionalField(fieldName: String) = AdditionalField<T>(fieldName)

/**
 * Returns a additional read write field which uses the fieldName to get and set it
 */
fun optionalAdditionalField(fieldName: String) = AdditionalField<Any?>(fieldName)

/**
 * Returns a additional read write field which uses the fieldName to get and set it
 */
@JvmName("optionalFieldAs")
fun <T : Any> optionalAdditionalField(fieldName: String) = AdditionalField<T?>(fieldName)

/**
 * Returns a additional static read write field which uses the fieldName to get and set it
 */
fun additionalStaticField(fieldName: String) = AdditionalStaticField<Any>(fieldName)

/**
 * Returns a additional static read write field which uses the fieldName to get and set it
 */
@JvmName("additionalStaticFieldAs")
fun <T : Any> additionalStaticField(fieldName: String) = AdditionalStaticField<T>(fieldName)

/**
 * Returns a additional static read write field which uses the fieldName to get and set it
 */
fun optionalAdditionalStaticField(fieldName: String) = AdditionalStaticField<Any?>(fieldName)

/**
 * Returns a additional static read write field which uses the fieldName to get and set it
 */
@JvmName("optionalAdditionalStaticFieldAs")
fun <T : Any> optionalAdditionalStaticField(fieldName: String) = AdditionalStaticField<T?>(fieldName)

/**
 * Reads and writes fields
 */
class Field<Value : Any?>(private val fieldName: String): ReadWriteProperty<Any, Value> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Value =
            thisRef.get<Value>(fieldName)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Value) {
        thisRef.set(fieldName, value)
    }
}

/**
 * Reads and writes static fields
 */
class StaticField<Value : Any?>(private val fieldName: String): ReadWriteProperty<Any, Value> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Value =
            thisRef.get<Value>(fieldName)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Value) {
        thisRef.set(fieldName, value)
    }
}

/**
 * Reads and writes additional field
 */
class AdditionalField<Value : Any?>(private val fieldName: String): ReadWriteProperty<Any, Value> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Value =
            thisRef.getAdditional<Value>(fieldName)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Value) {
        thisRef.setAdditional(fieldName, value)
    }
}

/**
 * Reads and writes additional static fields
 */
class AdditionalStaticField<Value : Any?>(private val fieldName: String): ReadWriteProperty<Any, Value> {

    override fun getValue(thisRef: Any, property: KProperty<*>): Value =
            thisRef.getAdditionalStatic<Value>(fieldName)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Value) {
        thisRef.setAdditionalStatic(fieldName, value)
    }
}

fun Any.toJavaClass(): Class<*> = when {
    this is Class<*> -> this
    this is KClass<*> -> this.java
    else -> this::class.java
}