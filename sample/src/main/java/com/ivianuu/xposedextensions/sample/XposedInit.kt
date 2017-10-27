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

import com.ivianuu.xposedextensions.*
import de.robv.android.xposed.callbacks.XC_LoadPackage
import android.widget.TextView
import de.robv.android.xposed.*

/**
 * Xposed init
 */
class XposedInit: IXposedHookZygoteInit, IXposedHookLoadPackage {

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        logX { "init zygote" }
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != SYSTEM_UI) return

        val clockClass = lpparam.classLoader.find(CLOCK)

        clockClass.invokeStatic("")

        clockClass.hook("updateClock") {
            priority { XC_MethodHook.PRIORITY_HIGHEST }
            before { logX { "before update clock" } }
            before {
                it.instance().invokeStatic("")
                it.instance<TextView>().customField?.let {
                    logX { "hey custom field $it" }
                }

                it.instance<TextView>().freezesText?.let {
                    logX { "hey freezes text $it" }
                }
            }
            after {
                val instance = it.instance<TextView>()
                instance.customField = instance.text
            }
        }
    }

    private var TextView.customField by optionalAdditionalField<CharSequence>("field")
    private var TextView.freezesText by optionalField("mFreezesText")

    private companion object {
        private const val SYSTEM_UI = "com.android.systemui"
        private const val CLOCK = "com.android.systemui.statusbar.policy.Clock"
    }
}