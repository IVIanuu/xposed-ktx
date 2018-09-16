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

package com.ivianuu.xposedktx

import de.robv.android.xposed.XposedHelpers
import kotlin.reflect.KClass

// CLASS

fun ClassLoader.findClass(className: String): Class<*> =
    XposedHelpers.findClass(className, this)

fun ClassLoader.findClassIfExists(className: String): Class<*>? =
    XposedHelpers.findClassIfExists(className, this)

fun <T : Any> KClass<T>.newInstance(vararg args: Any?) =
    XposedHelpers.newInstance(java)

fun <T : Any> KClass<T>.newInstance(
    parameterTypes: Array<Class<*>>,
    vararg args: Any?
) = XposedHelpers.newInstance(java, parameterTypes, *args) as T

fun <T> Any.getField(name: String) = XposedHelpers.getObjectField(this, name) as T

fun Any.setField(name: String, value: Any?) =
    XposedHelpers.setObjectField(this, name, value)

fun <T> KClass<*>.getStaticField(name: String) =
    XposedHelpers.getStaticObjectField(java, name) as T

fun KClass<*>.setStaticField(name: String, value: Any?) =
    XposedHelpers.setStaticObjectField(java, name, value)

fun <T> Any.getAdditionalField(name: String) =
    XposedHelpers.getAdditionalInstanceField(this, name) as T

fun Any.setAdditionalField(name: String, value: Any?) {
    XposedHelpers.setAdditionalInstanceField(this, name, value)
}

fun Any.removeAdditionalField(name: String) {
    XposedHelpers.removeAdditionalInstanceField(this, name)
}

fun <T> KClass<*>.getAdditionalStaticField(name: String) =
    XposedHelpers.getAdditionalStaticField(java, name) as T

fun KClass<*>.setAdditionalStaticField(name: String, value: Any?) {
    XposedHelpers.setAdditionalStaticField(java, name, value)
}

fun KClass<*>.removeAdditionalStaticField(name: String) {
    XposedHelpers.removeAdditionalStaticField(java, name)
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

fun <T> KClass<*>.invokeStaticFunction(
    name: String,
    vararg args: Any?
) = XposedHelpers.callStaticMethod(java, name, *args) as T

fun <T> KClass<*>.invokeStaticFunction(
    name: String,
    parameterTypes: Array<Class<*>>,
    vararg args: Any?
) = XposedHelpers.callStaticMethod(java, name, parameterTypes, *args) as T