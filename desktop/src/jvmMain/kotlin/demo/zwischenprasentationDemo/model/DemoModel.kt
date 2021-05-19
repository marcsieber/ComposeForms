package demo.zwischenprasentationDemo.model

import model.BaseFormModel

class DemoModel() : BaseFormModel() {





    override fun getPossibleLanguages(): List<String> {
        return Labels.getLanguages()
    }
}