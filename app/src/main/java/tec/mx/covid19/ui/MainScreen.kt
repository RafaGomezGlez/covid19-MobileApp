package tec.mx.covid19.ui

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import tec.mx.covid19.Screen
import tec.mx.covid19.model.Country
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        @get:Synchronized
        var instance: MyApplication? = null
            private set
        val getAppContext: Context
            get() = instance!!.applicationContext
    }
}

class CountryModel : ViewModel() {
    val countryList = mutableStateListOf<Country>()
    val context = MyApplication.getAppContext

    init {
        getJson()
    }

    private  fun getJson(){
        val url = "https://disease.sh/v3/covid-19/countries"
        val queue : RequestQueue = Volley.newRequestQueue(context)
        var nombre : String
        var cases : Int
        var imageId : String
        var recovered : Int
        var deaths : Int
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                for(i in 0 until response.length()){
                    val actual = response.getJSONObject(i)
                    val countryInfo = actual.getJSONObject("countryInfo")

                    nombre = actual.getString("country")
                    cases = actual.getString("cases").toInt()
                    imageId  = countryInfo.getString("flag")

                    recovered = actual.getString("recovered").toInt()
                    deaths = actual.getString("deaths").toInt()

                    val obj = Country(nombre, cases, imageId, recovered, deaths)
                    countryList.add(obj)
                    //Log.wtf("JSON", "${actual.getString("country")} : ${countryInfo.getString("iso3")}")
                }
            },
            { error ->
                Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonArrayRequest)
    }


}


@Composable
fun MainScreen(navController: NavController
){
    val viewModel: CountryModel = viewModel()

    title()
    listCountries(viewModel.countryList, navController)
}

@Preview
@Composable
fun title() {
    Text(
        "Hello World!",
        fontSize = 20.sp,
        color = Color.DarkGray,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    )
}
@ExperimentalCoilApi
@Composable
fun listCountries(data : SnapshotStateList<Country>, navController: NavController){
    LazyColumn(
        Modifier.padding(top = 60.dp)
    ){
        items(data){ country ->
            countryCard(country, navController)
        }
    }
}

@ExperimentalCoilApi
@Composable
fun countryCard(country : Country, navController: NavController) {
    val encodedUrl = URLEncoder.encode(country.imageId, StandardCharsets.UTF_8.toString())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                15.dp, 5.dp
            )
            .clickable {
                navController.navigate(
                    Screen.DetailScreen.withArgs(
                        country.name,
                        encodedUrl.toString(),
                        country.numberOfCases.toString(),
                        country.recovered.toString(),
                        country.deaths.toString())
                    )
            },
        elevation = 5.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .size(90.dp)
        ) {
            Image(
                painter = rememberImagePainter(country.imageId),
                contentDescription = null,
                alignment = Alignment.BottomCenter,
                modifier = Modifier
                    .size(55.dp)
                    .padding(0.dp, 10.dp)
            )
            Text(
                text = country.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = country.numberOfCases.toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}