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
inline fun <T> Any.get(name: String) = XposedHelpers.getObjectField(this, name) as T

/**
 * Returns the field with the name
 */
inline fun Any.get(name: String) = get<Any>(name)

/**
 * Returns the field with the name which can be null
 */
inline fun Any.getNull(name: String) = get<Any?>(name)

/**
 * Sets the field with the name to the value
 */
inline fun Any.set(name: String, value: Any?) =
        XposedHelpers.setObjectField(this, name, value)

// STATIC FIELDS

/**
 * Returns the static field with the name
 */
@JvmName("getStaticAs")
inline fun <T> Any.getStatic(name: String) = XposedHelpers.getStaticObjectField(toJavaClass(), name) as T

/**
 * Returns the static field with the name
 */
inline fun Any.getStatic(name: String) = getStatic<Any>(name)

/**
 * Returns the static field with the name which can be null
 */
inline fun Any.getStaticNull(name: String) = getStatic<Any?>(name)

/**
 * Sets the field with the name to the value
 */
inline fun Any.setStatic(name: String, value: Any?) =
        XposedHelpers.setStaticObjectField(toJavaClass(), name, value)

// ADDITIONAL

/**
 * Returns the additional field with the name
 */
@JvmName("getAdditionalAs")
inline fun <T> Any.getAdditional(name: String) = XposedHelpers.getAdditionalInstanceField(this, name) as T

/**
 * Returns the additional static field with the name
 */
inline fun Any.getAdditional(name: String) = getAdditional<Any>(name)

/**
 * Returns the additional static field with the name
 */
inline fun Any.getAdditionalNull(name: String) = getAdditional<Any?>(name)

/**
 * Sets the field with the name to the value
 */
inline fun Any.setAdditional(name: String, value: Any?) {
    XposedHelpers.setAdditionalInstanceField(this, name, value)
}

/**
 * Removes the additional field with the name
 */
inline fun Any.removeAdditional(name: String) {
    XposedHelpers.removeAdditionalInstanceField(this, name)
}

// ADDITIONAL STATIC

/**
 * Returns the additional static field with the name
 */
@JvmName("getAdditionalStaticAs")
inline fun <T> Any.getAdditionalStatic(name: String) = XposedHelpers.getAdditionalStaticField(toJavaClass(), name) as T

/**
 * Returns the additional static field with the name
 */
inline fun Any.getAdditionalStatic(name: String) = getAdditionalStatic<Any>(name)

/**
 * Returns the additional static field with the name which can be null
 */
inline fun Any.getAdditionalStaticNull(name: String) = getAdditionalStatic<Any?>(name)

/**
 * Sets the additional static field with the name to the value
 */
inline fun Any.setAdditionalStatic(name: String, value: Any?) {
    XposedHelpers.setAdditionalStaticField(toJavaClass(), name, value)
}

/**
 * Removes the additional static field with the name
 */
inline fun Any.removeAdditionalStatic(name: String) {
    XposedHelpers.removeAdditionalStaticField(toJavaClass(), name)
}

// METHODS

/**
 * Calls the method with the name and the args and returns the result
 */
@JvmName("invokeAs")
inline fun <T> Any.invoke(name: String,
                          vararg args: Any?) =
        XposedHelpers.callMethod(this, name, *args) as T

/**
 * Calls the method with the name and the args and returns the result
 */
inline fun Any.invoke(name: String, vararg args: Any?) = invoke<Any>(name, *args)

/**
 * Calls the method with the name and the args and returns the result which can be null
 */
inline fun Any.invokeNull(name: String, vararg args: Any?) = invoke<Any?>(name, *args)

/**
 * Calls the method with the name, the param types and the args and returns the result
 */
@JvmName("invokeAs")
inline fun <T> Any.invoke(name: String,
                          parameterTypes: Array<Class<*>>,
                          vararg args: Any?) =
        XposedHelpers.callMethod(this, name, parameterTypes, *args) as T

/**
 * Calls the method with the name, the param types and the args and returns the result
 */
inline fun Any.invoke(name: String,
                      parameterTypes: Array<Class<*>>,
                      vararg args: Any?) = invoke<Any>(name, parameterTypes, *args)

/**
 * Calls the method with the name, the param types and the args and returns the result which can be null
 */
inline fun Any.invokeNull(name: String,
                      parameterTypes: Array<Class<*>>,
                      vararg args: Any?) = invoke<Any?>(name, parameterTypes, *args)

/**
 * Calls the static method with the name and the args and returns the result
 */
@JvmName("invokeStaticAs")
inline fun <T> Any.invokeStatic(name: String,
                                vararg args: Any?) =
        XposedHelpers.callStaticMethod(toJavaClass(), name, *args) as T

/**
 * Calls the static method with the name and the args and returns the result
 */
inline fun Any.invokeStatic(name: String,
                            vararg args: Any?) = invokeStatic<Any>(name, *args)

/**
 * Calls the static method with the name and the args and returns the result which can be null
 */
inline fun Any.invokeStaticNull(name: String,
                            vararg args: Any?) = invokeStatic<Any?>(name, *args)

/**
 * Calls the static method with the name and the args and returns the result
 */
@JvmName("invokeStaticAs")
inline fun <T> Any.invokeStatic(name: String,
                                parameterTypes: Array<Class<*>>,
                                vararg args: Any?) =
        XposedHelpers.callStaticMethod(toJavaClass(), name, parameterTypes, *args) as T

/**
 * Calls the static method with the name and the args and returns the result
 */
inline fun Any.invokeStatic(name: String,
                            parameterTypes: Array<Class<*>>,
                            vararg args: Any?) = invokeStatic<Any>(name, parameterTypes, *args)

/**
 * Calls the static method with the name and the args and returns the result which can be null
 */
inline fun Any.invokeStaticNull(name: String,
                            parameterTypes: Array<Class<*>>,
                            vararg args: Any?) = invokeStatic<Any>(name, parameterTypes, *args)


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
fun Any.nullField(name: String) = field<Any?>(name)

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
fun Any.nullAdditionalField(name: String) = additionalField<Any?>(name)

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
fun Any.nullAdditionalStaticField(name: String) = additionalStaticField<Any?>(name)

// DELEGATED FUNCTIONS

class Method<out R>(private val getter: (Any) -> R): ReadOnlyProperty<Any, R> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = getter(thisRef)
}

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function0As")
fun <T> function0(name: String) = Method<() -> T> { receiver ->
    { receiver.invoke<T>(name) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function0(name: String) = function0<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function0Null(name: String) = function0<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function1As")
fun <T> function1(name: String) = Method<(Any?) -> T> { receiver ->
    { p1 -> receiver.invoke<T>(name, p1) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function1(name: String) = function1<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function1Null(name: String) = function1<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function2As")
fun <T> function2(name: String) = Method<(Any?, Any?) -> T> { receiver ->
    { p1, p2 -> receiver.invoke<T>(name, p1, p2) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function2(name: String) = function2<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function2Null(name: String) = function2<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function3As")
fun <T> function3(name: String) = Method<(Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3 -> receiver.invoke<T>(name, p1, p2, p3) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function3(name: String) = function3<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function3Null(name: String) = function3<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function4As")
fun <T> function4(name: String) = Method<(Any?, Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3, p4 -> receiver.invoke<T>(name, p1, p2, p3, p4) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function4(name: String) = function4<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function4Null(name: String) = function4<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function5As")
fun <T> function5(name: String) = Method<(Any?, Any?, Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3, p4, p5 -> receiver.invoke<T>(name, p1, p2, p3, p4, p5) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function5(name: String) = function5<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function5Null(name: String) = function5<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function6As")
fun <T> function6(name: String) = Method<(Any?, Any?, Any?, Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3, p4, p5, p6 -> receiver.invoke<T>(name, p1, p2, p3, p4, p5, p6) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function6(name: String) = function6<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function6Null(name: String) = function6<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function7As")
fun <T> function7(name: String) = Method<(Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3, p4, p5, p6, p7 -> receiver.invoke<T>(name, p1, p2, p3, p4, p5, p6, p7) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function7(name: String) = function7<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function7Null(name: String) = function7<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function8As")
fun <T> function8(name: String) = Method<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3, p4, p5, p6, p7, p8 -> receiver.invoke<T>(name, p1, p2, p3, p4, p5, p6, p7, p8) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function8(name: String) = function8<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function8Null(name: String) = function8<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function9As")
fun <T> function9(name: String) = Method<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3, p4, p5, p6, p7, p8, p9 -> receiver.invoke<T>(name, p1, p2, p3, p4, p5, p6, p7, p8, p9) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function9(name: String) = function9<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function9Null(name: String) = function9<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function10As")
fun <T> function10(name: String) = Method<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10 -> receiver.invoke<T>(name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function10(name: String) = function10<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function10Null(name: String) = function10<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function11As")
fun <T> function11(name: String) = Method<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11 -> receiver.invoke<T>(name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function11(name: String) = function11<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function11Null(name: String) = function11<Any?>(name)

/**
 * Return a function which invokes the method with the name and returns the result
 */
@JvmName("function12As")
fun <T> function12(name: String) = Method<(Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> T> { receiver ->
    { p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12 -> receiver.invoke<T>(name, p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12) }
}

/**
 * Returns a function which invokes the method with name and returns the result
 */
fun function12(name: String) = function12<Any>(name)

/**
 * Returns a function which invokes the method with name and returns the result which might be null
 */
fun function12Null(name: String) = function12<Any?>(name)

// HELPER

fun Any.toJavaClass(): Class<*> = when {
    this is Class<*> -> this
    this is KClass<*> -> this.java
    else -> this::class.java
}
