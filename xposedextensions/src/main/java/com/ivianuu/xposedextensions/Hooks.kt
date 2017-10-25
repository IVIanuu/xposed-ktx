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

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodHook.MethodHookParam
import de.robv.android.xposed.XC_MethodHook.Unhook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Constructor
import java.lang.reflect.Method

/**
 * Dsl builder to build method hooks
 */
class MethodHook {

    private var priority = XC_MethodHook.PRIORITY_DEFAULT
    private var before: ((Param) -> Unit)? = null
    private var after: ((Param) -> Unit)? = null
    private var replace: ((Param) -> Any?)? = null
    private var returnConstant: Any? = null

    private var beforeSet = false
    private var afterSet = false
    private var replaceSet = false
    private var returnConstantSet = false
    private var doNothingSet = false

    /**
     * Sets the priority
     */
    fun priority(priority: Int) {
        this.priority = priority
    }

    fun priority(action: () -> Int) {
        priority(action())
    }

    fun before(action: (Param) -> Unit) {
        this.before = action
        this.beforeSet = true
    }

    fun after(action: (Param) -> Unit) {
        this.after = action
        this.afterSet = true
    }

    fun replace(action: (Param) -> Any?) {
        this.replace = action
        this.replaceSet = true
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

    fun doNothing(action: () -> Unit) {
        doNothingSet = true
    }

    internal fun build(): XC_MethodHook = when {
        doNothingSet -> XC_MethodReplacement.DO_NOTHING
        returnConstantSet -> XC_MethodReplacement.returnConstant(priority, returnConstant)
        replaceSet -> {
            object : XC_MethodReplacement(priority) {
                override fun replaceHookedMethod(param: MethodHookParam): Any? =
                        replace?.invoke(Param(param))
            }
        }
        beforeSet || afterSet -> {
            object : XC_MethodHook(priority) {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    before?.let { it(Param(param)) }
                }
                override fun afterHookedMethod(param: MethodHookParam) {
                    after?.let { it(Param(param)) }
                }
            }
        }
        else -> throw IllegalArgumentException("invalid configuration")
    }
}

/**
 * Wraps a method hook param
 */
class Param(private val value: MethodHookParam) {

    val method = value.method
    val args = value.args
    var result: Any?
        get() = value.result
        set(value) { this.value.result = value }
    var exception: Throwable?
        get() = value.throwable
        set(value) { this.value.throwable = value }

    /**
     * Returns the return value or throws the exception
     */
    fun returns(): Any? = value.resultOrThrowable

    /**
     * Returns the instance as t
     */
    fun <T> instance() = value.thisObject as T
}

/**
 * Hooks all methods with name
 * If the name is empty it will the constructors
 */
fun Class<*>.hook(methodName: String = "",
                  init: MethodHook.() -> Unit): Set<Unhook> {
    val hook = MethodHook()
    init(hook)
    return if (methodName.isEmpty()) {
        XposedBridge.hookAllConstructors(this, hook.build())
    } else {
        XposedBridge.hookAllMethods(this, methodName, hook.build())
    }
}

/**
 * Hooks all methods with name
 * If the name is empty it will the constructors
 */
fun Class<*>.hook(methodName: String = "",
                  vararg args: Any,
                  init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return if (methodName.isEmpty()) {
        XposedHelpers.findAndHookConstructor(this, methodName, *args, hook.build())
    } else {
        XposedHelpers.findAndHookMethod(this, methodName, *args, hook.build())
    }
}

/**
 * Hooks all methods with name
 * If the name is empty it will the constructors
 */
fun ClassLoader.hook(className: String,
                     methodName: String = "",
                     init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return if (methodName.isEmpty()) {
        XposedHelpers.findAndHookConstructor(
                className, this, methodName, hook.build())
    } else {
        XposedHelpers.findAndHookMethod(
                className, this, methodName, hook.build())
    }
}

/**
 * Hooks all methods with name
 * If the name is empty it will the constructors
 */
fun ClassLoader.hook(className: String,
                     methodName: String = "",
                     vararg args: Any,
                     init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)

    return if (methodName.isEmpty()) {
        XposedHelpers.findAndHookConstructor(
                className, this, *args, hook.build())
    } else {
        XposedHelpers.findAndHookMethod(
                className, this, methodName, *args, hook.build())
    }
}

/**
 * Hooks this constructor
 */
fun Constructor<*>.hook(init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookMethod(this, hook.build())
}

/**
 * Hooks this method
 */
fun Method.hook(init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookMethod(this, hook.build())
}