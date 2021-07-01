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

package model

import androidx.compose.ui.focus.FocusRequester
import model.util.presentationElements.Group
import model.util.attribute.Attribute

interface IModel {
    fun getIPAdress(): String

    fun getGroups() : List<Group>
    fun getTitle() : String
    fun setTitle(title: String)
    fun saveAll() : Boolean
    fun resetAll() : Boolean
    fun validateAll()
    fun setCurrentLanguageForAll(lang : String)
    fun isCurrentLanguageForAll(lang: String): Boolean
    fun getCurrentLanguage(): String
    fun setChangedForAll()
    fun setValidForAll()
    fun isValidForAll() : Boolean
    fun isChangedForAll(): Boolean
    fun addGroup(group: Group)
    fun getPossibleLanguages(): List<String>

    fun getAttributeById(id: Int?): Attribute<*,*,*>?{
        return getGroups().flatMap{it.getAttributes()}.find{it.getId() == id}
    }

    fun attributeChanged(attr: Attribute<*,*,*>)
    fun validationChanged(attr: Attribute<*,*,*>)
    fun textChanged(attr: Attribute<*,*,*>)

    fun addFocusRequester(fr: FocusRequester, attr: Attribute<*,*,*>): Int
    fun focusNext()
    fun focusPrevious()
    fun setCurrentFocusIndex(index: Int?)
    fun getCurrentFocusIndex(): Int?

    fun smartphoneOption() : Boolean

}