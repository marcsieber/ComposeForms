//package demo.mountainForm.model
//
//import demo.mountainForm.service.MountainService
//import javafx.beans.property.*
//import javafx.beans.value.ObservableValue
//import java.util.*
//
//class Switzerland(service: MountainService) {
//
//    private val applicationTitle: StringProperty = SimpleStringProperty()
//    private val currentLocale: ObjectProperty<Locale> = SimpleObjectProperty(Locale.GERMANY)
//    private val changed: BooleanProperty = SimpleBooleanProperty()
//
//    private var currentMountain: MountainPM
//    private var service: MountainService
//
//    init{
//        this.service = service
//        val id = Random().nextInt(231).toLong() // einen Mountain zufaellig auswaehlen
//        currentMountain = MountainPM().of(service.get(id))
////        setupValueChangedListeners()
////        setupBindings()
//        translateEverything()
//    }
//
//
//    fun save() {
//        service.save(currentMountain.toDTO())
//        currentMountain.saveAll()
//    }
//
//    fun revert() {
//        currentMountain.undoAll()
//    }
//
////    private fun setupValueChangedListeners() {
////        currentLocale.addListener { observable: ObservableValue<out Locale>?, oldValue: Locale?, newValue: Locale? -> translateEverything() }
////    }
////
////    private fun setupBindings() {
////        changed.bind(currentMountain.changedProperty())
////    }
//
//    private fun translateEverything() {
//        setApplicationTitle(if (getCurrentLocale() == Locale.GERMANY) "Mountain Formular" else "Mountain Editor")
//        currentMountain.setCurrentLanguageForAll("german")
//    }
//
//    //alle generierten getter und setter
//    fun getApplicationTitle(): String {
//        return applicationTitle.get()
//    }
//
//    fun applicationTitleProperty(): StringProperty {
//        return applicationTitle
//    }
//
//    fun setApplicationTitle(applicationTitle: String) {
//        this.applicationTitle.set(applicationTitle)
//    }
//
//    fun getCurrentMountain(): MountainPM {
//        return currentMountain
//    }
//
//    fun getCurrentLocale(): Locale {
//        return currentLocale.get()
//    }
//
//    fun currentLocaleProperty(): ObjectProperty<Locale> {
//        return currentLocale
//    }
//
//    fun setCurrentLocale(currentLocale: Locale) {
//        this.currentLocale.set(currentLocale)
//    }
//
//    fun isChanged(): Boolean {
//        return changed.get()
//    }
//
//    fun changedProperty(): BooleanProperty {
//        return changed
//    }
//
//    fun setChanged(changed: Boolean) {
//        this.changed.set(changed)
//    }
//}