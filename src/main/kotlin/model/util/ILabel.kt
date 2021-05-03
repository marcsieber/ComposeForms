package model.util

import java.lang.reflect.Method

interface ILabel{

    fun getLabelInLanguage(label: Enum<*>, lang: String): String {

        val methodMap = getMethods().map { it.name.removePrefix("get").toLowerCase() to it }.toMap()

        if (methodMap.containsKey(lang.toLowerCase())) {
            return methodMap[lang]!!.invoke(label) as String
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

        return methodsUnfiltered.filter {
            it.name.startsWith("get")
                    && !it.declaringClass.name.contains("Object")
                    && !it.declaringClass.name.contains("Enum")
                    && !it.name.equals("getLanguagesDynamic")//TODO: take name of this method by runtime
                    && !it.name.equals("getLabelInLanguage")//TODO: take name of this method by runtime
        }
    }
}