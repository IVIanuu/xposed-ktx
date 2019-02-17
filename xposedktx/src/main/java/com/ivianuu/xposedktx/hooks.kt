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

class FunctionHookBuilder {

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

fun KClass<*>.hookAllConstructors(block: FunctionHookBuilder.() -> Unit): Set<Unhook> {
    val hook = FunctionHookBuilder()
    block(hook)
    return XposedBridge.hookAllConstructors(java, hook.build())
}

fun KClass<*>.hookAllFunctions(
    name: String,
    block: FunctionHookBuilder.() -> Unit
): Set<Unhook> {
    val hook = FunctionHookBuilder()
    block(hook)
    return XposedBridge.hookAllMethods(java, name, hook.build())
}

fun KClass<*>.hookConstructor(
    vararg args: Any?,
    block: FunctionHookBuilder.() -> Unit
): Unhook {
    val hook = FunctionHookBuilder()
    block(hook)
    return XposedHelpers.findAndHookConstructor(java, *args, hook.build())
}

fun KClass<*>.hookFunction(
    name: String,
    vararg args: Any?,
    block: FunctionHookBuilder.() -> Unit
): Unhook {
    val hook = FunctionHookBuilder()
    block(hook)
    return XposedHelpers.findAndHookMethod(java, name, *args, hook.build())
}

fun <T> MethodHookParam.thisObject(): T = thisObject as T

fun <T> MethodHookParam.result(): T = result as T