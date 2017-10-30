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

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodHook.MethodHookParam
import de.robv.android.xposed.XC_MethodHook.Unhook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Constructor
import java.lang.reflect.Method
import kotlin.reflect.KClass

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

    /**
     * Sets the priority
     */
    fun priority(action: () -> Int) {
        priority(action())
    }

    /**
     * Will be invoked before the hooked method
     */
    fun before(action: (Param) -> Unit) {
        this.before = action
        this.beforeSet = true
    }

    /**
     * Will be invoked after the hooked method
     */
    fun after(action: (Param) -> Unit) {
        this.after = action
        this.afterSet = true
    }

    /**
     * Replaces the hooked method and returns the result of the function
     */
    fun replace(action: (Param) -> Any?) {
        this.replace = action
        this.replaceSet = true
    }

    /**
     * Replaces the original method and returns the constant
     */
    fun returnConstant(constant: Any?) {
        this.returnConstant = constant
        this.returnConstantSet = true
    }

    /**
     * Replaces the original method and returns the constant
     */
    fun returnConstant(action: () -> Any?) {
        returnConstant(action())
    }

    /**
     * Skips the hooked method
     */
    fun doNothing() {
        doNothingSet = true
    }

    /**
     * Skips the hooked method
     */
    fun doNothing(action: () -> Unit) {
        doNothingSet = true
    }

    /**
     * Builds the method hook
     */
    fun build(): XC_MethodHook = when {
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
    /**
     * The instance
     */
    val instance = value.thisObject
    /**
     * The hooked method
     */
    val method = value.method
    /**
     * Args of the hooked method
     */
    val args = value.args
    /**
     * The result of the hooked method
     */
    var result: Any?
        get() = value.result
        set(value) { this.value.result = value }
    /**
     * The exception of the hooked method
     */
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

    /**
     * Returns the result as t
     */
    fun <T> result() = value.result as T?

    /**
     * Returns the arg at the index as t
     */
    @JvmName("argAs")
    fun <T> arg(index: Int) = args[index] as T

    /**
     * Returns the arg at the index
     */
    fun arg(index: Int) = args[index]
}

/**
 * Hooks all methods with name
 * If the name is empty it will the constructors
 */
inline fun Class<*>.hook(methodName: String = "",
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
inline fun Class<*>.hook(methodName: String = "",
                  vararg args: Any,
                  init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return if (methodName.isEmpty()) {
        XposedHelpers.findAndHookConstructor(this, *args, hook.build())
    } else {
        XposedHelpers.findAndHookMethod(this, methodName, *args, hook.build())
    }
}

/**
 * Hooks all methods with name
 * If the name is empty it will the constructors
 */
inline fun KClass<*>.hook(methodName: String = "",
                   init: MethodHook.() -> Unit): Set<Unhook> {
    val hook = MethodHook()
    init(hook)
    return if (methodName.isEmpty()) {
        XposedBridge.hookAllConstructors(this.java, hook.build())
    } else {
        XposedBridge.hookAllMethods(this.java, methodName, hook.build())
    }
}

/**
 * Hooks all methods with name
 * If the name is empty it will the constructors
 */
inline fun KClass<*>.hook(methodName: String = "",
                   vararg args: Any,
                   init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return if (methodName.isEmpty()) {
        XposedHelpers.findAndHookConstructor(this.java, *args, hook.build())
    } else {
        XposedHelpers.findAndHookMethod(this.java, methodName, *args, hook.build())
    }
}

/**
 * Hooks this constructor
 */
inline fun Constructor<*>.hook(init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookMethod(this, hook.build())
}

/**
 * Hooks this method
 */
inline fun Method.hook(init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookMethod(this, hook.build())
}