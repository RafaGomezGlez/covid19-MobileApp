package tec.mx.covid19

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import tec.mx.covid19.ui.DetailScreen
import tec.mx.covid19.ui.MainScreen

@ExperimentalCoilApi
@Composable
fun Navigation(){
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route){
        composable(
            route = Screen.MainScreen.route
        ){
            MainScreen(navController)
        }
        composable(
            route =  Screen.DetailScreen.route + "/{name}/{flag}/{cases}/{recovered}/{deaths}",
            arguments = listOf(
                navArgument("name"){
                    type = NavType.StringType
                    defaultValue = "rafa"
                    nullable = true
                },
                navArgument("flag"){
                    type = NavType.StringType
                    defaultValue = "gg"
                    nullable = true
                },
                navArgument("cases") {
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("recovered"){
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                },
                navArgument("deaths"){
                    type = NavType.IntType
                    defaultValue = 0
                    nullable = false
                }

            )
        ){ entry ->
            entry.arguments?.let {
                DetailScreen(
                    name = entry.arguments?.getString("name"),
                    flag = entry.arguments?.getString("flag"),
                    cases = entry.arguments!!.getInt("cases"),
                    recovered = entry.arguments!!.getInt("recovered"),
                    deaths = entry.arguments!!.getInt("deaths")
                )
            }
        }
    }

}
