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

package demo.personForm

import model.util.ILabel

enum class PersonLabels(val deutsch : String, val english : String) : ILabel{
    //Personal Information
    ID("ID", "ID"),
    FIRSTNAME("Vorname", "First name"),
    LASTNAME("Nachname", "Last name"),
    GENDER("Geschlecht", "Gender"),
    AGE("Alter", "Age"),
    SIZE("Grösse", "Size"),
    OCCUPATION("Beruf", "Occupation"),
    TAXNUMBER("Steuer-Nummer", "Tax Number"),

    //Adress
    POSTCODE("Postleitzahl", "Postcode"),
    PLACE("Ort", "Town/City"),
    STREET("Strasse", "Street"),
    HOUSENUMBER("Hausnummer","House Number");


    // Needed to get Languages
    companion object {
        fun getLanguages(): List<String> {
            return (values()[0] as ILabel).getLanguagesDynamic()
        }
    }
}