package demo.zwischenprasentationDemo.model

import model.BaseModel
import model.util.attribute.Attribute

class DemoModel : BaseModel() {

    override fun attributeChanged(attr: Attribute<*, *, *>) {
    }

    override fun getPossibleLanguages(): List<String> {
        return Labels.getLanguages()
    }


    override fun validationChanged(attr: Attribute<*, *, *>) {

    }
}