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
    private var before: ((XC_MethodHook.MethodHookParam) -> Unit)? = null
    private var after: ((XC_MethodHook.MethodHookParam) -> Unit)? = null
    private var replace: ((XC_MethodHook.MethodHookParam) -> Any?)? = null
    
    fun priority(priority: Int) {
        this.priority = priority
    }

    fun priority(action: () -> Int) {
        this.priority = action()
    }

    fun before(action: (XC_MethodHook.MethodHookParam) -> Unit) {
        this.before = action
    }

    fun after(action: (XC_MethodHook.MethodHookParam) -> Unit) {
        this.after = action
    }

    fun replace(action: (XC_MethodHook.MethodHookParam) -> Any?) {
        this.replace = action
    }

    internal fun build(): XC_MethodHook {
        check(before == null && after == null && replace == null) {
            "one of before, after or replace must be set"
        }
        return if (replace != null) {
            check(before != null || after != null) {
                "before and after has no effect while replacing is set"
            }
            object : XC_MethodReplacement() {
                override fun replaceHookedMethod(param: MethodHookParam): Any? =
                        replace?.invoke(param)
            }
        } else {
            check(before == null || after == null) {
                "at least one of before or after must be set"
            }
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    before?.let { it(param) }
                }
                override fun afterHookedMethod(param: MethodHookParam) {
                    after?.let { it(param) }
                }
            }
        }
    }
}

// CONSTRUCTORS

/**
 * Hooks all constructors
 */
fun Class<*>.hookAllConstructors(init: MethodHook.() -> Unit): Set<Unhook> {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookAllConstructors(this, hook.build())
}

/**
 * Hooks the constructor
 */
fun Class<*>.hookConstructor(vararg args: Any,
                             init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedHelpers.findAndHookConstructor(this, *args, hook.build())
}

/**
 * Hooks the constructor
 */
fun ClassLoader.hookConstructor(className: String,
                                vararg args: Any,
                                init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedHelpers.findAndHookConstructor(className, this, *args, hook.build())
}

/**
 * Hooks this constructor
 */
fun Constructor<*>.hook(init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookMethod(this, hook.build())
}

// METHODS

/**
 * Hooks all methods
 */
fun Class<*>.hookAllMethods(methodName: String,
                            init: MethodHook.() -> Unit): Set<Unhook> {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookAllMethods(this, methodName, hook.build())
}

/**
 * Hooks the method
 */
fun Class<*>.hookMethod(methodName: String,
                        vararg args: Any,
                        init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedHelpers.findAndHookMethod(this, methodName, *args, hook.build())
}

/**
 * Hooks the method
 */
fun ClassLoader.hookMethod(className: String,
                           methodName: String,
                           vararg args: Any,
                           init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedHelpers.findAndHookConstructor(
            className, this, methodName, *args, hook.build())
}

/**
 * Hooks this method
 */
fun Method.hook(init: MethodHook.() -> Unit): Unhook {
    val hook = MethodHook()
    init(hook)
    return XposedBridge.hookMethod(this, hook.build())
}