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
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.reflect.Constructor
import java.lang.reflect.Method

private val hookMethodStub: (MethodHookParam) -> Unit = {}

class MethodHook(private val before: (MethodHookParam) -> Unit,
                           private val after: (MethodHookParam) -> Unit): XC_MethodHook() {
    override fun beforeHookedMethod(param: MethodHookParam) = before(param)
    override fun afterHookedMethod(param: MethodHookParam) = after(param)
}

class MethodReplacement(private val replacement: (MethodHookParam) -> Any?)
    : XC_MethodReplacement() {
    override fun replaceHookedMethod(param: MethodHookParam) = replacement(param)
}

// CONSTRUCTORS

/**
 * Hooks all constructors and calls the functions on the events
 */
fun Class<*>.hookAllConstructors(before: (MethodHookParam) -> Unit = hookMethodStub,
                                 after: (MethodHookParam) -> Unit = hookMethodStub) =
        XposedBridge.hookAllConstructors(this, MethodHook(before, after))

/**
 * Hooks all constructors of this class and call the function before it 
 */
fun Class<*>.beforeAllConstructors(before: (MethodHookParam) -> Unit) =
        hookAllConstructors(before = before)

/**
 * Hooks all constructors of this class and calls the function after it
 */
fun Class<*>.afterAllConstructors(after: (MethodHookParam) -> Unit) =
        hookAllConstructors(after = after)

/**
 * Replaces all constructors of this class with replacement function
 */
fun Class<*>.replaceAllConstructors(replacement: (MethodHookParam) -> Any?) =
        XposedBridge.hookAllConstructors(this, MethodReplacement(replacement))

/**
 * Hooks the constructor and calls the functions on the events
 */
fun Class<*>.hookConstructor(vararg args: Any,
                             before: (MethodHookParam) -> Unit = hookMethodStub,
                             after: (MethodHookParam) -> Unit = hookMethodStub) =
        XposedHelpers.findAndHookConstructor(this, *args, MethodHook(before, after))

/**
 * Hooks the constructor and calls the functions on the events
 */
fun ClassLoader.hookConstructor(className: String,
                                vararg args: Any,
                                before: (MethodHookParam) -> Unit = hookMethodStub,
                                after: (MethodHookParam) -> Unit = hookMethodStub) =
        XposedHelpers.findAndHookConstructor(
                className, this, *args, MethodHook(before, after))

/**
 * Hooks the constructor of this class and call the function before it
 */
fun Class<*>.beforeConstructor(vararg args: Any,
                               before: (MethodHookParam) -> Unit) =
        hookConstructor(args = *args, before = before)

/**
 * Hooks the constructor of this class and call the function before it
 */
fun ClassLoader.beforeConstructor(className: String,
                                  vararg args: Any,
                                  before: (MethodHookParam) -> Unit) =
        hookConstructor(className = className, args = *args, before = before)

/**
 * Hooks all constructors of this class and calls the function after it
 */
fun Class<*>.afterConstructor(vararg args: Any,
                              after: (MethodHookParam) -> Unit) =
        hookConstructor(args = *args, after = after)

/**
 * Hooks all constructors of this class and calls the function after it
 */
fun ClassLoader.afterConstructor(className: String,
                                 vararg args: Any,
                                 after: (MethodHookParam) -> Unit) =
        hookConstructor(className = className, args = *args, after = after)

/**
 * Replaces all constructors of this class with replacement function
 */
fun Class<*>.replaceConstructor(vararg args: Any,
                                replacement: (MethodHookParam) -> Any?) =
        XposedHelpers.findAndHookConstructor(this, *args, MethodReplacement(replacement))

/**
 * Replaces all constructors of this class with replacement function
 */
fun ClassLoader.replaceConstructor(className: String,
                                   vararg args: Any,
                                   replacement: (MethodHookParam) -> Any?) =
        XposedHelpers.findAndHookConstructor(
                className, this, *args, (MethodReplacement(replacement)))

// METHODS

/**
 * Hooks all methods of this class with the name and calls the functions on the events
 */
fun Class<*>.hookAllMethods(methodName: String,
                            before: (MethodHookParam) -> Unit = hookMethodStub,
                            after: (MethodHookParam) -> Unit = hookMethodStub) =
        XposedBridge.hookAllMethods(this, methodName, MethodHook(before, after))

/**
 * Hooks all methods with the name of this class and call the function before it
 */
fun Class<*>.beforeAllMethods(methodName: String,
                                  before: (MethodHookParam) -> Unit) =
        hookAllMethods(methodName = methodName, before = before)

/**
 * Hooks all methods with the name of this class and calls the function after it
 */
fun Class<*>.afterAllMethods(methodName: String,
                                 after: (MethodHookParam) -> Unit) =
        hookAllMethods(methodName = methodName, after = after)

/**
 * Replaces all methods with the name of this class and calls the function after it
 */
fun Class<*>.replaceAllMethods(methodName: String,
                               replacement: (MethodHookParam) -> Any?) =
        XposedBridge.hookAllMethods(
                this, methodName, MethodReplacement(replacement))

/**
 * Hooks the constructor and calls the functions on the events
 */
fun Class<*>.hookMethod(methodName: String,
                        vararg args: Any,
                        before: (MethodHookParam) -> Unit = hookMethodStub,
                        after: (MethodHookParam) -> Unit = hookMethodStub) =
        XposedHelpers.findAndHookMethod(this, methodName, *args, MethodHook(before, after))

/**
 * Hooks the Method and calls the functions on the events
 */
fun ClassLoader.hookMethod(className: String,
                           methodName: String,
                           vararg args: Any,
                           before: (MethodHookParam) -> Unit = hookMethodStub,
                           after: (MethodHookParam) -> Unit = hookMethodStub) =
        XposedHelpers.findAndHookMethod(
                className, this, methodName, *args, MethodHook(before, after))

/**
 * Hooks the Method of this class and call the function before it
 */
fun Class<*>.beforeMethod(methodName: String,
                          vararg args: Any,
                          before: (MethodHookParam) -> Unit) =
        hookMethod(methodName = methodName, args = *args, before = before)

/**
 * Hooks the Method of this class and call the function before it
 */
fun ClassLoader.beforeMethod(className: String,
                             methodName: String,
                             vararg args: Any,
                             before: (MethodHookParam) -> Unit) =
        hookMethod(className = className, methodName = methodName, args = *args, before = before)

/**
 * Hooks all Methods of this class and calls the function after it
 */
fun Class<*>.afterMethod(methodName: String,
                         vararg args: Any,
                         after: (MethodHookParam) -> Unit) =
        hookMethod(methodName = methodName, args = *args, after = after)

/**
 * Hooks all Methods of this class and calls the function after it
 */
fun ClassLoader.afterMethod(className: String,
                            methodName: String,
                            vararg args: Any,
                            after: (MethodHookParam) -> Unit) =
        hookMethod(className = className, methodName = methodName, args = *args, after = after)

/**
 * Replaces all Methods of this class with replacement function
 */
fun Class<*>.replaceMethod(methodName: String,
                           vararg args: Any,
                           replacement: (MethodHookParam) -> Any?) =
        XposedHelpers.findAndHookMethod(this, methodName, *args, MethodReplacement(replacement))


/**
 * Replaces all Methods of this class with replacement function
 */
fun ClassLoader.replaceMethod(className: String,
                              methodName: String,
                              vararg args: Any,
                              replacement: (MethodHookParam) -> Any?) =
        XposedHelpers.findAndHookMethod(
                className, this, methodName, *args, MethodReplacement(replacement))

/**
 * Hooks this constructor
 */
fun Constructor<*>.hook(before: (MethodHookParam) -> Unit = hookMethodStub,
                        after: (MethodHookParam) -> Unit = hookMethodStub) =
        XposedBridge.hookMethod(this, MethodHook(before, after))

/**
 * Hooks this constructor
 */
fun Constructor<*>.before(before: (MethodHookParam) -> Unit = hookMethodStub) =
        hook(before = before)

/**
 * Hooks this constructor
 */
fun Constructor<*>.after(after: (MethodHookParam) -> Unit = hookMethodStub) =
        hook(after = after)

/**
 * Hooks this method
 */
fun Constructor<*>.replace(replacement: (MethodHookParam) -> Any?) =
        XposedBridge.hookMethod(this, MethodReplacement(replacement))

/**
 * Hooks this method
 */
fun Method.hook(before: (MethodHookParam) -> Unit = hookMethodStub,
                after: (MethodHookParam) -> Unit = hookMethodStub) =
        XposedBridge.hookMethod(this, MethodHook(before, after))

/**
 * Hooks this method
 */
fun Method.before(before: (MethodHookParam) -> Unit = hookMethodStub) =
        hook(before = before)

/**
 * Hooks this method
 */
fun Method.after(after: (MethodHookParam) -> Unit = hookMethodStub) =
        hook(after = after)

/**
 * Hooks this method
 */
fun Method.replace(replacement: (MethodHookParam) -> Any?) =
        XposedBridge.hookMethod(this, MethodReplacement(replacement))