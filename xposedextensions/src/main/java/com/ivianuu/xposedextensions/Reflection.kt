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

@file:Suppress("UNCHECKED_CAST")

package com.ivianuu.xposedextensions

import de.robv.android.xposed.XposedHelpers
import kotlin.reflect.KClass

// CLASS

fun ClassLoader.findClass(className: String): Class<*> =
    XposedHelpers.findClass(className, this)

fun ClassLoader.findClassIfExists(className: String): Class<*>? =
    XposedHelpers.findClassIfExists(className, this)

fun <T> Any.newInstance(vararg args: Any?) =
    XposedHelpers.newInstance(toJavaClass(), *args) as T

fun <T> Any.newInstance(
    parameterTypes: Array<Class<*>>,
    vararg args: Any?
) =
    XposedHelpers.newInstance(toJavaClass(), parameterTypes, *args) as T

fun <T> Any.getField(name: String) = XposedHelpers.getObjectField(this, name) as T

fun Any.setField(name: String, value: Any?) =
    XposedHelpers.setObjectField(this, name, value)

fun <T> Any.getStaticField(name: String) =
    XposedHelpers.getStaticObjectField(toJavaClass(), name) as T

fun Any.setStaticField(name: String, value: Any?) =
    XposedHelpers.setStaticObjectField(toJavaClass(), name, value)

fun <T> Any.getAdditionalField(name: String) =
    XposedHelpers.getAdditionalInstanceField(this, name) as T

fun Any.setAdditionalField(name: String, value: Any?) {
    XposedHelpers.setAdditionalInstanceField(this, name, value)
}

fun Any.removeAdditionalField(name: String) {
    XposedHelpers.removeAdditionalInstanceField(this, name)
}

fun <T> Any.getAdditionalStaticField(name: String) =
    XposedHelpers.getAdditionalStaticField(toJavaClass(), name) as T

fun Any.setAdditionalStaticField(name: String, value: Any?) {
    XposedHelpers.setAdditionalStaticField(toJavaClass(), name, value)
}

fun Any.removeAdditionalStaticField(name: String) {
    XposedHelpers.removeAdditionalStaticField(toJavaClass(), name)
}

fun <T> Any.invokeFunction(
    name: String,
    vararg args: Any?
) =
    XposedHelpers.callMethod(this, name, *args) as T

fun <T> Any.invokeFunction(
    name: String,
    parameterTypes: Array<Class<*>>,
    vararg args: Any?
) =
    XposedHelpers.callMethod(this, name, parameterTypes, *args) as T

fun <T> Any.invokeStaticFunction(
    name: String,
    vararg args: Any?
) =
    XposedHelpers.callStaticMethod(toJavaClass(), name, *args) as T

fun <T> Any.invokeStaticFunction(
    name: String,
    parameterTypes: Array<Class<*>>,
    vararg args: Any?
) =
    XposedHelpers.callStaticMethod(toJavaClass(), name, parameterTypes, *args) as T

fun Any.toJavaClass(): Class<*> = when {
    this is Class<*> -> this
    this is KClass<*> -> this.java
    else -> this::class.java
}
