package demo.playGroundForm

import model.util.ILabel


enum class Labels(val english: String, val deutsch: String): ILabel {
    stringLabel("English String", "Deutscher String"),
    intLabel("English Int", "Deutscher Int"),
    shortLabel("English Short", "deutscher Short"),
    longLabel("English Long", "deutscher Long"),
    floatLabel("English Float", "deutscher Float"),
    doubleLabel("English Double", "deutscher Double"),
    selectionLabel("English Selection", "deutsche Selektion"),
    timeLabel("Time", "Uhrzeit"),
    convertImmediately("convert immediately", "sofort konvertieren"),
    convertOnUnfocussing("convert on unfocussing", "bei Verlassen konvertieren"),
    doNotConvert("do not convert user view", "User View nicht konvertieren");
}



