package uz.safix.shoptestapp.ui.screens

/**
 * Created by: androdev
 * Date: 24-07-2024
 * Time: 12:19 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

sealed interface AppScreen {
    val route: String

    data object ProductsScreen : AppScreen {
        override val route: String = "ProductsScreen"
    }

    data object ProductDetailsScreen : AppScreen {
        override val route: String = "ProductDetailsScreen/{$ARG_PRODUCT_ID}"

        fun getRoute(productId: Long): String {
            return "ProductDetailsScreen/$productId"
        }
    }

    data object InsertScreen : AppScreen {
        override val route: String = "InsertScreen"
    }

    data object UpdateScreen : AppScreen {
        override val route: String = "UpdateScreen/{$ARG_PRODUCT_ID}"

        fun getRoute(productId: Long? = null): String {
            return "UpdateScreen/${productId ?: -1L}"
        }
    }

    companion object {
        const val ARG_PRODUCT_ID = "productId"
    }
}