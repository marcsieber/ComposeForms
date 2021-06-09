package demo.zwischenprasentationDemo.model

import model.BaseFormModel
import model.util.attribute.Attribute

class DemoModel() : BaseFormModel() {

    override fun attributeChanged(attr: Attribute<*, *, *>) {
    }

    override fun getPossibleLanguages(): List<String> {
        return Labels.getLanguages()
    }


    override fun validationChanged(attr: Attribute<*, *, *>) {

    }
}