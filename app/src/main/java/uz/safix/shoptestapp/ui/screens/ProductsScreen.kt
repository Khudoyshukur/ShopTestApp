package uz.safix.shoptestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.safix.shoptestapp.R
import uz.safix.shoptestapp.data.db.entity.ProductEntity
import uz.safix.shoptestapp.ui.util.PreviewUtils
import uz.safix.shoptestapp.ui.viewmodels.ProductsViewModel

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:54 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@Composable
fun ProductsScreen(
    viewModel: ProductsViewModel,
    onOpenDetails: (ProductEntity) -> Unit,
    onEdit: (ProductEntity) -> Unit,
    onAdd: () -> Unit,
) {
    val products by viewModel.productsStream.collectAsStateWithLifecycle(initialValue = emptyList())
    val query by viewModel.queryStream.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.background(Color.White),
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                containerColor = colorResource(id = R.color.purple_200),
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_product)
                )
            }
        }
    ) { padding ->
        MainContent(
            modifier = Modifier.padding(padding),
            query = query,
            onQueryChanged = viewModel::updateQuery,
            products = products,
            onDelete = { viewModel.deleteProduct(it.id) },
            onEdit = onEdit,
            onOpenDetails = onOpenDetails
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChanged: (String) -> Unit,
    products: List<ProductEntity>,
    onDelete: (ProductEntity) -> Unit,
    onEdit: (ProductEntity) -> Unit,
    onOpenDetails: (ProductEntity) -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            value = query,
            placeholder = { Text(text = stringResource(id = R.string.search_product)) },
            onValueChange = onQueryChanged,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_product)
                )
            }
        )

        ProductListTitles()

        LazyColumn {
            items(items = products) {
                Product(
                    item = it,
                    onDelete = onDelete,
                    onEdit = onEdit,
                    onClick = onOpenDetails
                )
            }
        }
    }
}

@Composable
fun ProductListTitles() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 96.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.naming),
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
        )
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.amount),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun Product(
    item: ProductEntity,
    onDelete: (ProductEntity) -> Unit,
    onEdit: (ProductEntity) -> Unit,
    onClick: (ProductEntity) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick(item) }
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = item.name,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.weight(1f),
            text = item.amount.toString(),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Icon(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(24.dp)
                .height(24.dp)
                .clickable { onEdit(item) },
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(id = R.string.edit),
            tint = colorResource(id = R.color.purple_700)
        )

        Icon(
            modifier = Modifier.clickable { onDelete(item) },
            imageVector = Icons.Default.Delete,
            contentDescription = stringResource(id = R.string.delete),
            tint = Color.Red
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun ProductPreview() {
    Product(
        item = PreviewUtils.getProduct(),
        onDelete = {},
        onEdit = {},
        onClick = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun ProductListPreview() {
    MainContent(
        products = PreviewUtils.getProducts(5),
        onDelete = {},
        onEdit = {},
        onOpenDetails = {},
        query = "",
        onQueryChanged = {})
}