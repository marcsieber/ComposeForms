package model.util

import java.lang.reflect.Method

interface ILabel{

    /**
     * This method returns the label name of an enum constant in the desired language.
     * If the language is not defined an IllegalArgumentException is thrown
     *
     * @param label : Enum<*>
     * @param lang : String
     * @return label : String
     * @throws IllegalArgumentException
     */
    fun getLabelInLanguage(label: Enum<*>, lang: String): String {

        val methodMap = getMethods().map { it.name.removePrefix("get").toLowerCase() to it }.toMap()

        if (methodMap.containsKey(lang.toLowerCase())) {
            return methodMap[lang.toLowerCase()]!!.invoke(label) as String
        } else {
            throw IllegalArgumentException("Language not found")
        }
    }

    /**
     * This method can read out all not private parameters (languages) of an enum.
     * It returns all used languages as a list.
     *
     * @return languages : List<String>
     */
    fun getLanguagesDynamic(): List<String> {
        val methodsFiltered = getMethods()
        return methodsFiltered.map { it.name.removePrefix("get").toLowerCase() }
    }

    /**
     * This method returns all getter functions that are created in an enum.
     *
     * @return getter : List<Method>
     */
    private fun getMethods(): List<Method> {

        val methodsUnfiltered: Array<Method> = this::class.java.methods

        val ownMethodNames = ILabel::class.java.methods.map{ it.name }

        return methodsUnfiltered.filter {
            it.name.startsWith("get")
                    && !it.declaringClass.name.contains("Object")
                    && !it.declaringClass.name.contains("Enum")
                    && !it.name.equals(ownMethodNames[0])
                    && !it.name.equals(ownMethodNames[1])
        }
    }
}