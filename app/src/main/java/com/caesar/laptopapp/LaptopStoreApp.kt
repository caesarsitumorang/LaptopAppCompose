package com.caesar.laptopapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.caesar.laptopapp.ui.detail.DetailLaptopScreen
import com.caesar.laptopapp.ui.home.HomeScreen
import com.caesar.laptopapp.ui.navigation.Screen
import com.caesar.laptopapp.ui.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaptopStoreApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) {
    Scaffold (

    ){
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(it).testTag("DetailLaptopScreen")
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { laptopId ->
                        navController.navigate(Screen.DetailLaptop.createRoute(laptopId ))
                    },
                    navigateToProfile = { navController.navigate(Screen.Profile.route) }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
            composable(
                Screen.DetailLaptop.route,
                arguments = listOf(navArgument("laptopId") { type = NavType.IntType }),
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getInt("laptopId") ?: -1
                DetailLaptopScreen(
                    laptopId = id,
                    shareAction = { summary ->
                        shareLaptop(context, summary)
                    },
                    navigateBack = { navController.popBackStack()}
                )
            }
        }
    }
}


private fun shareLaptop(context: Context, summary: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.amazing_laptop))
            putExtra(Intent.EXTRA_TEXT, summary)
        }
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.amazing_laptop)))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "Tidak ada aplikasi yang dapat menangani intent", Toast.LENGTH_SHORT).show()
    }
}
