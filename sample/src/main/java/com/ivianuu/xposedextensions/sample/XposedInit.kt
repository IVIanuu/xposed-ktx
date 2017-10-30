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

package com.ivianuu.xposedextensions.sample

import android.app.Activity
import android.view.Window
import com.ivianuu.xposedextensions.*
import de.robv.android.xposed.callbacks.XC_LoadPackage
import de.robv.android.xposed.*
import de.robv.android.xposed.callbacks.XC_InitPackageResources

/**
 * Xposed init
 */
class XposedInit: IXposedHookZygoteInit, IXposedHookLoadPackage, IXposedHookInitPackageResources {
    override fun handleInitPackageResources(resparam: XC_InitPackageResources.InitPackageResourcesParam) {
        resparam.res.hook {
            replace(0, "")

            hookLayout(0) {

            }
        }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        logX { "init zygote" }
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.ivianuu.xposedextensions.sample") {
            Activity::class.hook("onTouchEvent") {
                before { } // do something before on create
            }
        }
    }

    private val Window.shouldCloseOnOutsideTouch
            by function2<Boolean>("shouldCloseOnOutsideTouch")
}