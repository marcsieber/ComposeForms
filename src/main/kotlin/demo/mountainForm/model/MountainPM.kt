package demo.mountainForm.model

import demo.mountainForm.service.MountainDTO
import demo.mountainForm.service.MountainService
import model.BaseFormModel
import model.util.attribute.DoubleAttribute
import model.util.attribute.LongAttribute
import model.util.attribute.StringAttribute
import java.util.*

class MountainPM(var service : MountainService) : BaseFormModel() {

    private val BASE_URL = "https://dieterholz.github.io/StaticResources/mountainpictures/"
    private var currentMountain: MountainDTO

    init {
        setTitle(if (isCurrentLanguageForAll("Berge")) "Demo Title" else "Mountains")
        val id = Random().nextInt(231).toLong()
        currentMountain = service.get(id)
    }


    private val id              = LongAttribute(  value = currentMountain.getId(),model = this, label = Labels.ID, readOnly = true)
    private val name            = StringAttribute(value = currentMountain.getName(), model = this, label = Labels.NAME, required = true)
    private val height          = DoubleAttribute(value = currentMountain.getHeight(), model = this, label = Labels.HEIGHT)
    private val type            = StringAttribute(value = currentMountain.getType(), model = this, label = Labels.TYPE)
    private val region          = StringAttribute(value = currentMountain.getRegion(), model = this, label = Labels.REGION)
    private val cantons         = StringAttribute(value = currentMountain.getCantons(), model = this, label = Labels.CANTONS)
    private val range           = StringAttribute(value = currentMountain.getRange(), model = this, label = Labels.RANGE)
    private val isolation       = DoubleAttribute(value = currentMountain.getIsolation(), model = this, label = Labels.ISOLATON)
    private val isolationPoint  = StringAttribute(value = currentMountain.getIsolationPoint(), model = this, label = Labels.ISOLATIONPOINT)
    private val prominence      = DoubleAttribute(value = currentMountain.getProminence(), model = this, label = Labels.PROMINENCE)
    private val prominencePoint = StringAttribute(value = currentMountain.getProminencePoint(), model = this, label = Labels.PROMINENCEPOINT)
    private val imageCaption    = StringAttribute(value = currentMountain.getImageCaption(), model = this, label = Labels.IMAGECAPTION)
    private val imageUrl        = StringAttribute(value = currentMountain.getImageCaption(), model = this, label = Labels.IMAGEURL)

    init {
        setCurrentLanguageForAll("german")
    }

    fun of(dto: MountainDTO): MountainPM {
        val pm = MountainPM(service)
        pm.apply(dto)
        pm.saveAll()
        return pm
    }

    fun apply(dto: MountainDTO) {
        //Formatierung hilft zu erkennen, dass auf allen Attributen das "gleiche" gemacht wird
        idAttribute().setValueAsText(dto.getId().toString())
        nameAttribute().setValueAsText(dto.getName())
        heightAttribute().setValueAsText(dto.getHeight().toString())
        regionAttribute().setValueAsText(dto.getRegion())
        typeAttribute().setValueAsText(dto.getType())
        cantonsAttribute().setValueAsText(dto.getCantons())
        rangeAttribute().setValueAsText(dto.getRange())
        isolationAttribute().setValueAsText(dto.getIsolation().toString())
        isolationPointAttribute().setValueAsText(dto.getIsolationPoint())
        prominenceAttribute().setValueAsText(dto.getProminence().toString())
        prominencePointAttribute().setValueAsText(dto.getProminencePoint())
        imageCaptionAttribute().setValueAsText(dto.getImageCaption())
        imageURLAttribute().setValueAsText(BASE_URL + dto.getId().toString() + "-1.jpg")
    }

    fun toDTO(): MountainDTO {
        return MountainDTO(listOf(
            idAttribute().toString(),
            nameAttribute().toString(),
            heightAttribute().toString(),
            regionAttribute().toString(),
            typeAttribute().toString(),
            cantonsAttribute().toString(),
            rangeAttribute().toString(),
            isolationAttribute().toString(),
            isolationPointAttribute().toString(),
            prominenceAttribute().toString(),
            prominencePointAttribute().toString(),
            imageCaptionAttribute().toString()
        ))
    }

    override fun getPossibleLanguages(): List<String> {
        return Labels.getLanguages()
    }


    // alle Getter und Setter

    fun idAttribute(): LongAttribute<Labels> {
        return id
    }

    fun nameAttribute(): StringAttribute<Labels> {
        return name
    }

    fun heightAttribute(): DoubleAttribute<Labels> {
        return height
    }

    fun typeAttribute(): StringAttribute<Labels> {
        return type
    }

    fun regionAttribute(): StringAttribute<Labels> {
        return region
    }

    fun cantonsAttribute(): StringAttribute<Labels> {
        return cantons
    }

    fun rangeAttribute(): StringAttribute<Labels> {
        return range
    }

    fun isolationAttribute(): DoubleAttribute<Labels> {
        return isolation
    }

    fun isolationPointAttribute(): StringAttribute<Labels> {
        return isolationPoint
    }

    fun prominenceAttribute(): DoubleAttribute<Labels> {
        return prominence
    }

    fun prominencePointAttribute(): StringAttribute<Labels> {
        return prominencePoint
    }

    fun imageCaptionAttribute(): StringAttribute<Labels> {
        return imageCaption
    }

    fun imageURLAttribute(): StringAttribute<Labels> {
        return imageUrl
    }


}