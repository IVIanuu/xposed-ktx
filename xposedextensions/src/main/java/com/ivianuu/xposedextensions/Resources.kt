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

import android.content.res.XResources
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LayoutInflated
import de.robv.android.xposed.callbacks.XC_LayoutInflated.LayoutInflatedParam

class ResourcesHook(private val resources: XResources) {

    fun replace(id: Int, value: Any) {
        resources.setReplacement(id, value)
    }

    fun replace(pkg: String, type: String, name: String, value: Any) {
        resources.setReplacement(pkg, type, name, value)
    }

    fun hookLayout(id: Int, action: (LayoutInflatedParam) -> Unit) {
        resources.hookLayout(id, object : XC_LayoutInflated() {
            override fun handleLayoutInflated(liparam: LayoutInflatedParam) {
                action(liparam)
            }
        })
    }

    fun hookLayout(pkg: String, type: String, name: String, action: (LayoutInflatedParam) -> Unit) {
        resources.hookLayout(pkg, type, name, object : XC_LayoutInflated() {
            override fun handleLayoutInflated(liparam: LayoutInflatedParam) {
                action(liparam)
            }
        })
    }
}

/**
 * Hooks the resources
 */
fun XResources.hook(init: ResourcesHook.() -> Unit) {
    val hook = ResourcesHook(this)
    init(hook)
}

/**
 * Replaces system wide
 */
fun replaceSystemWide(id: Int, value: Any) {
    XResources.setSystemWideReplacement(id, value)
}

/**
 * Replaces system wide
 */
fun replaceSystemWide(pkg: String, type: String, name: String, value: Any) {
    XResources.setSystemWideReplacement(pkg, type, name, value)
}

/**
 * Replaces system wide
 */
fun hookLayoutSystemWide(id: Int, action: (LayoutInflatedParam) -> Unit) {
    XResources.hookSystemWideLayout(id, object : XC_LayoutInflated() {
        override fun handleLayoutInflated(liparam: LayoutInflatedParam) {
            action(liparam)
        }
    })
}

/**
 * Replaces system wide
 */
fun hookLayoutSystemWide(pkg: String, type: String, name: String, action: (LayoutInflatedParam) -> Unit) {
    XResources.hookSystemWideLayout(pkg, type, name, object : XC_LayoutInflated() {
        override fun handleLayoutInflated(liparam: LayoutInflatedParam) {
            action(liparam)
        }
    })
}