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

package model.util.presentationElements

import model.IModel
import model.util.attribute.Attribute

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */
class Group(val model : IModel, val title : String, vararg fields : Field) {

    private val groupFields = fields.map { it }.toMutableList()
    private val groupAttributes = fields.map { it.getAttribute() }.toMutableList()


    init {
        model.addGroup(this)
        fields.forEach{
            if(model != it.getAttribute().getModel()){
                throw IllegalArgumentException("Model of the attribute does not match the model of the group.")
            }
            it.getAttribute().setCurrentLanguage(model.getCurrentLanguage())
        }
    }

    fun addAttribute(attribute : Attribute<*,*,*>){
        groupAttributes.add(attribute)
        attribute.setCurrentLanguage(model.getCurrentLanguage())
    }

    fun removeAttribute(attribute: Attribute<*, *, *>){
        groupAttributes.remove(attribute)
    }

    //**************
    //Getter

    fun getAttributes(): List<Attribute<*,*,*>>{
        return groupAttributes
    }

    fun getFields(): List<Field>{
        return groupFields
    }
}