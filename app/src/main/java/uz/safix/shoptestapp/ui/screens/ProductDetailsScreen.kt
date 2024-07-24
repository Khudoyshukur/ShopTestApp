package uz.safix.shoptestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.safix.shoptestapp.R
import uz.safix.shoptestapp.data.db.entity.ProductEntity
import uz.safix.shoptestapp.ui.util.PreviewUtils
import uz.safix.shoptestapp.ui.viewmodels.ProductDetailsViewModel
import java.time.format.DateTimeFormatter

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:54 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@Composable
fun ProductDetailsScreen(
   viewModel: ProductDetailsViewModel = hiltViewModel()
) {
   val product by viewModel.productStream.collectAsStateWithLifecycle(null)
   Surface(modifier = Modifier.fillMaxSize().background(Color.White)) {
      product?.let { MainContent(product = it) }
   }

}

@Composable
private fun MainContent(product: ProductEntity) {
   val addedDate = remember(product.time) {
      val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
      product.time.format(formatter)
   }
   val tags = remember(product.tags) { product.tags.joinToString(", ") }
   val amount = remember(product.amount) { product.amount.toString() }

   Column(modifier = Modifier.padding(16.dp)) {
      Row(modifier = Modifier.fillMaxWidth()) {
         Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(id = R.string.naming), fontWeight = FontWeight.Bold)
            Text(text = product.name, modifier = Modifier.padding(vertical = 8.dp))
         }

         Column(modifier = Modifier.weight(1f)) {
            Text(text = stringResource(id = R.string.added_date), fontWeight = FontWeight.Bold)
            Text(text = addedDate, modifier = Modifier.padding(vertical = 8.dp))
         }
      }

      Column(modifier = Modifier.padding(top = 8.dp)) {
         Text(text = stringResource(id = R.string.tags), fontWeight = FontWeight.Bold)
         Text(text = tags, modifier = Modifier.padding(vertical = 8.dp))
      }

      Column(modifier = Modifier.padding(top = 8.dp)) {
         Text(text = stringResource(id = R.string.amount), fontWeight = FontWeight.Bold)
         Text(text = amount, modifier = Modifier.padding(vertical = 8.dp))
      }
   }
}

@Preview(showBackground = true)
@Composable
private fun MainContentPreview() {
   MainContent(product = PreviewUtils.getProduct())
}