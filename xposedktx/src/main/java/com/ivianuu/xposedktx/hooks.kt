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

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodHook.MethodHookParam
import de.robv.android.xposed.XC_MethodHook.Unhook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import kotlin.reflect.KClass

class MethodHook {

    private var priority = XC_MethodHook.PRIORITY_DEFAULT
    private var beforeHookedMethod: ((MethodHookParam) -> Unit)? = null
    private var afterHookedMethod: ((MethodHookParam) -> Unit)? = null
    private var replaceHookedMethod: ((MethodHookParam) -> Any?)? = null
    private var returnConstant: Any? = null

    private var beforeHookedMethodSet = false
    private var afterHookedMethodSet = false
    private var replaceHookedMethodSet = false
    private var returnConstantSet = false
    private var doNothingSet = false

    fun priority(priority: Int) {
        this.priority = priority
    }

    fun priority(action: () -> Int) {
        priority(action())
    }

    fun beforeHookedMethod(action: (MethodHookParam) -> Unit) {
        this.beforeHookedMethod = action
        this.beforeHookedMethodSet = true
    }

    fun afterHookedMethod(action: (MethodHookParam) -> Unit) {
        this.afterHookedMethod = action
        this.afterHookedMethodSet = true
    }

    fun replaceHookedMethod(action: (MethodHookParam) -> Any?) {
        this.replaceHookedMethod = action
        this.replaceHookedMethodSet = true
    }

    fun returnConstant(constant: Any?) {
        this.returnConstant = constant
        this.returnConstantSet = true
    }

    fun returnConstant(action: () -> Any?) {
        returnConstant(action())
    }

    fun doNothing() {
        doNothingSet = true
    }

    fun build(): XC_MethodHook = when {
        doNothingSet -> XC_MethodReplacement.DO_NOTHING
        returnConstantSet -> XC_MethodReplacement.returnConstant(priority, returnConstant)
        replaceHookedMethodSet -> {
            object : XC_MethodReplacement(priority) {
                override fun replaceHookedMethod(param: MethodHookParam) =
                    replaceHookedMethod?.invoke(param)
            }
        }
        beforeHookedMethodSet || afterHookedMethodSet -> {
            object : XC_MethodHook(priority) {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    beforeHookedMethod?.invoke(param)
                }

                override fun afterHookedMethod(param: MethodHookParam) {
                    afterHookedMethod?.invoke(param)
                }
            }
        }
        else -> throw IllegalArgumentException("invalid configuration")
    }
}

inline fun KClass<*>.hookAllConstructors(init: MethodHook.() -> Unit) =
    java.hookAllConstructors(init)

inline fun Class<*>.hookAllConstructors(init: MethodHook.() -> Unit): Set<Unhook> {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookAllConstructors(this, hook.build())
}

inline fun KClass<*>.hookAllMethods(
    methodName: String,
    init: MethodHook.() -> Unit
) = java.hookAllMethods(methodName, init)

inline fun Class<*>.hookAllMethods(
    methodName: String,
    init: MethodHook.() -> Unit
): Set<Unhook> {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookAllMethods(this, methodName, hook.build())
}

inline fun KClass<*>.findAndHookConstructor(
    vararg args: Any,
    init: MethodHook.() -> Unit
) = java.findAndHookConstructor(*args, init = init)

inline fun Class<*>.findAndHookConstructor(
    vararg args: Any,
    init: MethodHook.() -> Unit
): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedHelpers.findAndHookConstructor(this, *args, hook.build())
}

inline fun KClass<*>.findAndHookMethod(
    methodName: String,
    vararg args: Any,
    init: MethodHook.() -> Unit
) = java.findAndHookMethod(methodName, *args, init = init)

inline fun Class<*>.findAndHookMethod(
    methodName: String,
    vararg args: Any,
    init: MethodHook.() -> Unit
): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedHelpers.findAndHookMethod(this, methodName, *args, hook.build())
}

inline fun Constructor<*>.hook(init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookMethod(this, hook.build())
}

inline fun Method.hook(init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookMethod(this, hook.build())
}

fun <T> MethodHookParam.thisObject() = thisObject as T

fun <T> MethodHookParam.result() = result as T
