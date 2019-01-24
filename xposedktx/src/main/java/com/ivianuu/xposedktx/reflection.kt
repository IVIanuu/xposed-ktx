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

fun ClassLoader.getClass(name: String): KClass<*> =
    XposedHelpers.findClass(name, this).kotlin

fun ClassLoader.getClassOrNull(name: String): KClass<*>? =
    XposedHelpers.findClassIfExists(name, this)?.kotlin

fun <T : Any> KClass<T>.newInstance(vararg args: Any?): T =
    XposedHelpers.newInstance(java, *args) as T

fun <T : Any> KClass<T>.newInstance(
    parameterTypes: Array<KClass<*>>,
    vararg args: Any?
): T = XposedHelpers.newInstance(java, parameterTypes, args) as T

fun <T> Any.getProperty(name: String): T = XposedHelpers.getObjectField(this, name) as T

fun Any.setProperty(name: String, value: Any?) {
    XposedHelpers.setObjectField(this, name, value)
}

inline fun <reified T> getStaticProperty(name: String): T =
    getStaticProperty(T::class, name)

fun <T> getStaticProperty(type: KClass<*>, name: String): T =
    XposedHelpers.getStaticObjectField(type.java, name) as T

inline fun <reified T> setStaticProperty(name: String, value: Any?) {
    setStaticProperty(T::class, name, value)
}

fun setStaticProperty(type: KClass<*>, name: String, value: Any?) {
    XposedHelpers.setStaticObjectField(type.java, name, value)
}

fun <T> Any.getAdditionalProperty(name: String): T =
    XposedHelpers.getAdditionalInstanceField(this, name) as T

fun Any.setAdditionalProperty(name: String, value: Any?) {
    XposedHelpers.setAdditionalInstanceField(this, name, value)
}

fun Any.removeAdditionalProperty(name: String) {
    XposedHelpers.removeAdditionalInstanceField(this, name)
}

inline fun <reified I, T> getAdditionalStaticProperty(name: String): T =
    getAdditionalStaticProperty<T>(I::class, name)

fun <T> getAdditionalStaticProperty(type: KClass<*>, name: String): T =
    XposedHelpers.getAdditionalStaticField(type.java, name) as T

inline fun <reified T> setAdditionalStaticProperty(name: String, value: Any?) {
    setAdditionalStaticProperty(T::class, name, value)
}

fun setAdditionalStaticProperty(type: KClass<*>, name: String, value: Any?) {
    XposedHelpers.setAdditionalStaticField(type.java, name, value)
}

inline fun <reified T> removeAdditionalStaticProperty(name: String) {
    removeAdditionalStaticProperty(T::class, name)
}

fun removeAdditionalStaticProperty(type: KClass<*>, name: String) {
    XposedHelpers.removeAdditionalStaticField(type.java, name)
}

fun <T> Any.invokeFunction(
    name: String,
    vararg args: Any?
): T = XposedHelpers.callMethod(this, name, *args) as T

fun <T> Any.invokeFunction(
    name: String,
    parameterTypes: Array<KClass<*>>,
    vararg args: Any?
): T =
    XposedHelpers.callMethod(this, name, parameterTypes.map { it.java }.toTypedArray(), *args) as T

inline fun <reified S, T> invokeStaticFunction(
    name: String,
    vararg args: Any?
): T = invokeStaticFunction<T>(S::class, name, *args)

fun <T> invokeStaticFunction(
    type: KClass<*>,
    name: String,
    vararg args: Any?
): T = XposedHelpers.callStaticMethod(type.java, name, *args) as T

inline fun <reified S, T> invokeStaticFunction(
    name: String,
    parameterTypes: Array<KClass<*>>,
    vararg args: Any?
): T = invokeStaticFunction(S::class, name, parameterTypes, *args)

fun <T> invokeStaticFunction(
    type: KClass<*>,
    name: String,
    parameterTypes: Array<KClass<*>>,
    vararg args: Any?
): T = XposedHelpers.callStaticMethod(
    type.java,
    name,
    parameterTypes.map { it.java }.toTypedArray(),
    *args
) as T