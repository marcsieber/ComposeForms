package demo.playGroundForm

import model.util.ILabel


enum class Labels(val english: String, val deutsch: String): ILabel {
    stringLabel("English String", "Deutscher String"),
    intLabel("English Int", "Deutscher Int"),
    shortLabel("English Short", "deutscher Short"),
    longLabel("English Long", "deutscher Long"),
    floatLabel("English Float", "deutscher Float"),
    doubleLabel("English Double", "deutscher Double"),
    selectionLabel("English Selection", "deutsche Selektion");

    // Needed to get Languages
    companion object {
        fun getLanguages(): List<String> {
            return (values()[0] as ILabel).getLanguagesDynamic()
        }
    }
}



