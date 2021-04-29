package demo

import java.lang.reflect.Method

interface ILabel{
    fun getLabelInLanguage(label: Labels, lang: String): String
}

enum class Labels(val english: String, val deutsch: String): ILabel {
    intLabel1("Good Morning", "Guten Tag"),
    intLabel2("eng", "deutsch"),
    stringLabel1("String end", "String de");


    override fun getLabelInLanguage(label: Labels, lang: String): String {

        val methodsUnfiltered: Array<Method> = Labels::class.java.methods

        val methodsFiltered: List<Method> = methodsUnfiltered.filter {
            it.name.startsWith("get")
            && it.declaringClass.name.endsWith("Labels")
            && !it.name.equals("getLabelInLanguage") //TODO: take name of this method by runtime
        }

        val methodMap = methodsFiltered.map { it.name.removePrefix("get") to it }.toMap()

//        println("Englishinvoke: " + methodMap["English"]?.invoke(label))

        if(methodMap.containsKey(lang)) {
            return methodMap[lang]!!.invoke(label) as String
        }else{
            throw IllegalArgumentException("Language not found")
        }


    }
}



