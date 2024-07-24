package uz.safix.shoptestapp.ui.screens

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.safix.shoptestapp.R
import uz.safix.shoptestapp.ui.theme.Primary

/**
 * Created by: androdev
 * Date: 24-07-2024
 * Time: 7:57 AM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHostScreen() {
    val context = LocalContext.current
    val navController = rememberNavController()
    var title by remember { mutableStateOf("") }

    val listener = remember {
        NavController.OnDestinationChangedListener { _, destination, arguments ->
            val productId = arguments?.getString(AppScreen.ARG_PRODUCT_ID)?.toLong()
            title = when (destination.route) {
                AppScreen.ProductsScreen.route -> context.getString(R.string.products_list)
                AppScreen.ProductDetailsScreen.route -> context.getString(
                    R.string.product_view, productId
                )

                AppScreen.InsertScreen.route -> context.getString(R.string.insert_product)
                AppScreen.UpdateScreen.route -> context.getString(
                    R.string.update_product, productId
                )

                else -> ""
            }
        }
    }

    DisposableEffect(listener) {
        navController.addOnDestinationChangedListener(listener)
        onDispose { navController.removeOnDestinationChangedListener(listener) }
    }


    Column {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    textAlign = TextAlign.Center
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Primary)
        )
        NavHost(
            modifier = Modifier.background(Color.White),
            navController = navController,
            startDestination = AppScreen.ProductsScreen.route
        ) {
            composable(AppScreen.ProductsScreen.route) {
                ProductsScreen(
                    viewModel = hiltViewModel(),
                    onEdit = { product ->
                        navController.navigate(
                            AppScreen.UpdateScreen.getRoute(product.id)
                        )
                    },
                    onAdd = {
                        navController.navigate(AppScreen.InsertScreen.route)
                    },
                    onOpenDetails = { product ->
                        navController.navigate(AppScreen.ProductDetailsScreen.getRoute(product.id))
                    }
                )
            }

            composable(
                AppScreen.ProductDetailsScreen.route,
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { it } },
            ) { ProductDetailsScreen() }

            composable(
                AppScreen.InsertScreen.route,
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { it } },
            ) {
                UpsertProductScreen(
                    onActioned = { navController.popBackStack() }
                )
            }

            composable(
                AppScreen.UpdateScreen.route,
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { it } },
            ) {
                UpsertProductScreen(
                    onActioned = { navController.popBackStack() }
                )
            }
        }
    }
}