package demo.mountainForm.model

import model.util.ILabel

enum class Labels(val german : String, val english : String) : ILabel{
    ID("ID", "ID"),
    NAME("Name", "Name"),
    HEIGHT("Höhe (m)", "Height (m)"),
    TYPE("Typ", "Type"),
    REGION("Region", "Region"),
    CANTONS("Kantone", "Cantons"),
    RANGE("Gebiet", "Range"),
    ISOLATON("Dominanz", "Isolation"),
    ISOLATIONPOINT("km bis", "Isolation Point"),
    PROMINENCE("Schartenhöhe", "Prominence"),
    PROMINENCEPOINT("m bis", "Prominence Point"),
    IMAGECAPTION("Bildunterschrift", "Caption"),
    IMAGEURL("Bild Url", "Image Url");

    // Needed to get Languages
    companion object {
        fun getLanguages(): List<String> {
            return (values()[0] as ILabel).getLanguagesDynamic()
        }
    }
}