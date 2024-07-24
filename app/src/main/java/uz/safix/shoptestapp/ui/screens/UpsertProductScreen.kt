package uz.safix.shoptestapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import uz.safix.shoptestapp.R
import uz.safix.shoptestapp.ui.viewmodels.UpsertMode
import uz.safix.shoptestapp.ui.viewmodels.UpsertProductViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Created by: androdev
 * Date: 23-07-2024
 * Time: 9:55 PM
 * Email: Khudoyshukur.Juraev.001@mail.ru
 */

@Composable
fun UpsertProductScreen(
    viewModel: UpsertProductViewModel = hiltViewModel(),
    onActioned: () -> Unit = {}
) {
    val actioned by viewModel.actionedStream.collectAsStateWithLifecycle()
    val name by viewModel.nameStream.collectAsStateWithLifecycle()
    val amount by viewModel.amountStream.collectAsStateWithLifecycle()
    val time by viewModel.datetimeStream.collectAsStateWithLifecycle()
    val tags by viewModel.tagsStream.collectAsStateWithLifecycle()
    val isActionEnabled by viewModel.isActionEnabledStream.collectAsStateWithLifecycle(true)
    val upsertMode = remember { viewModel.upsertMode }

    MainContent(
        name = name,
        amount = amount,
        time = time,
        tags = tags,
        upsertMode = upsertMode,
        isActionEnabled = isActionEnabled,
        onAddTag = viewModel::addTag,
        onRemoveTag = viewModel::removeTag,
        onChangeAmount = viewModel::changeAmount,
        onChangeName = viewModel::changeName,
        onAction = viewModel::performAction
    )

    LaunchedEffect(actioned) {
        if (actioned) {
            onActioned()
        }
    }
}

@Composable
private fun MainContent(
    name: String,
    amount: Int,
    time: LocalDateTime,
    tags: List<String>,
    upsertMode: UpsertMode,
    isActionEnabled: Boolean,
    onAddTag: (String) -> Unit = {},
    onRemoveTag: (String) -> Unit = {},
    onChangeAmount: (Int) -> Unit = {},
    onChangeName: (String) -> Unit = {},
    onAction: () -> Unit = {}
) {
    var newTag by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.naming), fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = name,
                onValueChange = onChangeName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = stringResource(id = R.string.amount), fontWeight = FontWeight.Bold)

                val amountText = remember(amount) { amount.toString() }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, end = 4.dp),
                    value = amountText,
                    onValueChange = { it.toIntOrNull()?.let(onChangeAmount) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(text = stringResource(id = R.string.added_date), fontWeight = FontWeight.Bold)

                val timeText = remember(time) {
                    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                    time.format(formatter)
                }
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 4.dp),
                    value = timeText,
                    onValueChange = {},
                    readOnly = true
                )
            }
        }

        Column(modifier = Modifier.padding(top = 16.dp)) {
            Text(text = stringResource(id = R.string.tags), fontWeight = FontWeight.Bold)

            // Here we could use LazyList, but for simplicity with nested scrolling. I am using repeat
            repeat(tags.size) {
                Row {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp),
                        value = tags[it],
                        onValueChange = {},
                        readOnly = true

                    )

                    Icon(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .align(Alignment.CenterVertically)
                            .clickable { onRemoveTag(tags[it]) },
                        imageVector = Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.delete),
                        tint = Color.Red,
                    )
                }
            }

            Row {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 4.dp),
                    value = newTag,
                    onValueChange = { newTag = it }

                )

                Icon(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            onAddTag(newTag)
                            newTag = ""
                        },
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_tag),
                    tint = colorResource(id = R.color.purple_700),
                )
            }

            Button(
                onClick = onAction,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                enabled = isActionEnabled
            ) {
                Text(
                    text = when (upsertMode) {
                        UpsertMode.Insert -> stringResource(id = R.string.add)
                        is UpsertMode.Update -> stringResource(id = R.string.change)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainContentPreview() {
    MainContent(
        name = "Some name",
        time = LocalDateTime.now(),
        amount = 5, tags = listOf("Tag 1", "Tag 2"),
        upsertMode = UpsertMode.Insert,
        isActionEnabled = false
    )
}