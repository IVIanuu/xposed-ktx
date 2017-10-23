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

private val hookMethodStub: (MethodHookParam) -> Unit = {}

/**
 * Hooks all constructors and calls the functions on the events
 */
fun Class<*>.hookAllConstructors(before: (MethodHookParam) -> Unit = hookMethodStub,
                                 after: (MethodHookParam) -> Unit = hookMethodStub) {
    XposedBridge.hookAllConstructors(this, object : XC_MethodHook(){
        override fun beforeHookedMethod(param: MethodHookParam) = before(param)
        override fun afterHookedMethod(param: MethodHookParam) = after(param)
    })
}

/**
 * Hooks all constructors of this class and call the function before it 
 */
fun Class<*>.hookBeforeAllConstructors(before: (MethodHookParam) -> Unit) =
        hookAllConstructors(before = before)

/**
 * Hooks all constructors of this class and calls the function after it
 */
fun Class<*>.hookAfterAllConstructors(after: (MethodHookParam) -> Unit) =
        hookAllConstructors(after = after)

/**
 * Replaces all constructors of this class with replacement function
 */
fun Class<*>.replaceAllConstructors(replacement: (MethodHookParam) -> Any?) {
    XposedBridge.hookAllConstructors(this, object : XC_MethodReplacement(){
        override fun replaceHookedMethod(param: MethodHookParam) = replacement(param)
    })
}

/**
 * Hooks all methods of this class with the name and calls the functions on the events
 */
fun Class<*>.hookAllMethods(methodName: String,
                            before: (MethodHookParam) -> Unit = hookMethodStub,
                            after: (MethodHookParam) -> Unit = hookMethodStub) {
    XposedBridge.hookAllMethods(this, methodName, object : XC_MethodHook(){
        override fun beforeHookedMethod(param: MethodHookParam) = before(param)
        override fun afterHookedMethod(param: MethodHookParam) = after(param)
    })
}

/**
 * Hooks all methods with the name of this class and call the function before it
 */
fun Class<*>.hookBeforeAllMethods(methodName: String,
                                  before: (MethodHookParam) -> Unit) =
        hookAllMethods(methodName = methodName, before = before)

/**
 * Hooks all methods with the name of this class and calls the function after it
 */
fun Class<*>.hookAfterAllMethods(methodName: String,
                                 after: (MethodHookParam) -> Unit) =
        hookAllMethods(methodName = methodName, after = after)

/**
 * Replaces all methods with the name of this class and calls the function after it
 */
fun Class<*>.replaceAllMethods(methodName: String,
                               replacement: (MethodHookParam) -> Any?) {
    XposedBridge.hookAllMethods(this, methodName, object : XC_MethodReplacement(){
        override fun replaceHookedMethod(param: MethodHookParam) = replacement(param)
    })
}