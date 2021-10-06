package tec.mx.covid19.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@ExperimentalCoilApi
@Composable
fun DetailScreen(name: String?, flag: String?, cases: Int, recovered: Int, deaths: Int){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ){
            Text(
                text = "$name",
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(top = 20.dp)
            )
            Image(
                painter = rememberImagePainter(flag),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(260.dp)
                    .padding(20.dp, 20.dp)
            )
            Row() {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(
                            0.dp,0.dp,30.dp,0.dp
                        )
                ) {
                    Text(text = "Casos:")
                    Text(text = "Recuperados:")
                    Text(text = "Decesos")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(
                        20.dp,0.dp,0.dp,0.dp
                    )
                ) {
                    Text(text = "$cases")
                    Text(text = "$recovered")
                    Text(text = "$deaths")
                }
            }

        }
}