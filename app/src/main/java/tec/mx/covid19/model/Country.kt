package tec.mx.covid19.model

import java.io.Serializable

data class Country (
    var name: String,
    val numberOfCases: Int,
    val imageId : String,
    val recovered : Int,
    val deaths : Int
): Serializable {
    constructor() : this("",  0, "", 0, 0)
}