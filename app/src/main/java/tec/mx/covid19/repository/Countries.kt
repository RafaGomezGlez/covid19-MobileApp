package tec.mx.covid19.repository

import tec.mx.covid19.model.Country


object CountriesList {
    val countries = listOf(
        Country(
            name =  "Mexico",
            numberOfCases = 12345,
            imageId = "Hh",
            recovered = 10,
            deaths = 100

        ),
        Country(
            name = "Alemania",
            numberOfCases = 1234,
            imageId = "ASDS",
            recovered = 100,
            deaths = 10
        )
    )
}
