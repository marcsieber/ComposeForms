/*
 *
 *   ========================LICENSE_START=================================
 *   Compose Forms
 *   %%
 *   Copyright (C) 2021 FHNW Technik
 *   %%
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   =========================LICENSE_END==================================
 *
 */

package ch.fhnw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import ch.model.Model
import ch.ui.UI

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */
object App : IApp {

    override fun initialize(activity: AppCompatActivity, savedInstanceState: Bundle?) {
        Model.connectAndSubscribe()
    }

    @ExperimentalFoundationApi
    @Composable
    override fun createAppUI() {
        UI(Model)
    }
}