package model.util

import java.lang.reflect.Method

interface ILabel{

    fun getLabelInLanguage(label: Enum<*>, lang: String): String {

        val methodMap = getMethods().map { it.name.removePrefix("get").toLowerCase() to it }.toMap()

        if (methodMap.containsKey(lang.toLowerCase())) {
            return methodMap[lang.toLowerCase()]!!.invoke(label) as String
        } else {
            throw IllegalArgumentException("Language not found")
        }
    }

    fun getLanguagesDynamic(): List<String> {
        val methodsFiltered = getMethods()
        return methodsFiltered.map { it.name.removePrefix("get").toLowerCase() }
    }

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