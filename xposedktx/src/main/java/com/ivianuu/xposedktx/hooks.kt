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
import kotlin.reflect.KClass

class FunctionHook {

    private var priority = XC_MethodHook.PRIORITY_DEFAULT
    private var before: ((MethodHookParam) -> Unit)? = null
    private var after: ((MethodHookParam) -> Unit)? = null
    private var replace: ((MethodHookParam) -> Any?)? = null
    private var returnConstant: Any? = null

    private var beforeSet = false
    private var afterSet = false
    private var replaceSet = false
    private var returnConstantSet = false
    private var doNothingSet = false

    fun priority(priority: Int) {
        this.priority = priority
    }

    fun priority(block: () -> Int) {
        priority(block())
    }

    fun before(block: (MethodHookParam) -> Unit) {
        before = block
        beforeSet = true
    }

    fun after(block: (MethodHookParam) -> Unit) {
        after = block
        afterSet = true
    }

    fun replace(block: (MethodHookParam) -> Any?) {
        replace = block
        replaceSet = true
    }

    fun returnConstant(constant: Any?) {
        returnConstant = constant
        returnConstantSet = true
    }

    fun returnConstant(block: () -> Any?) {
        returnConstant(block())
    }

    fun doNothing() {
        doNothingSet = true
    }

    fun build(): XC_MethodHook = when {
        doNothingSet -> XC_MethodReplacement.DO_NOTHING
        returnConstantSet -> XC_MethodReplacement.returnConstant(priority, returnConstant)
        replaceSet -> {
            object : XC_MethodReplacement(priority) {
                override fun replaceHookedMethod(param: MethodHookParam) =
                    replace?.invoke(param)
            }
        }
        beforeSet || afterSet -> {
            object : XC_MethodHook(priority) {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    before?.invoke(param)
                }

                override fun afterHookedMethod(param: MethodHookParam) {
                    after?.invoke(param)
                }
            }
        }
        else -> throw IllegalArgumentException("invalid configuration")
    }
}

inline fun <reified T> hookAllConstructors(noinline block: FunctionHook.() -> Unit): Set<Unhook> =
    hookAllConstructors(T::class, block)

fun hookAllConstructors(type: KClass<*>, block: FunctionHook.() -> Unit): Set<Unhook> {
    val hook = FunctionHook()
    block(hook)
    return XposedBridge.hookAllConstructors(type.java, hook.build())
}

inline fun <reified T> hookAllFunctions(
    name: String,
    noinline block: FunctionHook.() -> Unit
): Set<Unhook> = hookAllFunctions(T::class, name, block)

fun hookAllFunctions(
    type: KClass<*>,
    name: String,
    block: FunctionHook.() -> Unit
): Set<Unhook> {
    val hook = FunctionHook()
    block(hook)
    return XposedBridge.hookAllMethods(type.java, name, hook.build())
}

inline fun <reified T> hookConstructor(
    vararg args: Any?,
    noinline block: FunctionHook.() -> Unit
): Unhook = hookConstructor(T::class, *args, block = block)

fun hookConstructor(
    type: KClass<*>,
    vararg args: Any?,
    block: FunctionHook.() -> Unit
): Unhook {
    val hook = FunctionHook()
    block(hook)
    return XposedHelpers.findAndHookConstructor(type.java, *args, hook.build())
}

inline fun <reified T> hookFunction(
    name: String,
    vararg args: Any?,
    noinline block: FunctionHook.() -> Unit
): Unhook = hookFunction(T::class, name, *args, block = block)

fun hookFunction(
    type: KClass<*>,
    name: String,
    vararg args: Any?,
    block: FunctionHook.() -> Unit
): Unhook {
    val hook = FunctionHook()
    block(hook)
    return XposedHelpers.findAndHookMethod(type.java, name, *args, hook.build())
}

fun <T> MethodHookParam.thisObject(): T = thisObject as T

fun <T> MethodHookParam.result(): T = result as T
