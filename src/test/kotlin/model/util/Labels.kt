package model.util

enum class Labels(val test: String, val eng: String): ILabel {
    TEST("test", "testEng");

    companion object {
        fun getLanguages(): List<String> {
            return (demo.Labels.values()[0] as ILabel).getLanguagesDynamic()
        }
    }
}