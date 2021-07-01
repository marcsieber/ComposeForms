/*
 *
 *  * ========================LICENSE_START=================================
 *  * Compose Forms
 *  * %%
 *  * Copyright (C) 2021 FHNW Technik
 *  * %%
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  * =========================LICENSE_END==================================
 *
 */

package ch.fhnw

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ch.model.Model

class MainActivity : AppCompatActivity() {
    private lateinit var app: IApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("intent data: ${intent.data}")
        intent.data?.lastPathSegment?.also { ip ->
            println(ip)
            Model.mqttBroker = ip
        }

        app = App

        app.initialize(activity = this, savedInstanceState = savedInstanceState)

        setContent {
            app.createAppUI()
        }
    }

    override fun onStop() {
        super.onStop()
        app.onStop(activity = this)
    }
}
