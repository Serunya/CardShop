package com.tailspin.presentation.product

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tailspin.domain.model.Item
import com.tailspin.domain.model.Tag
import com.tailspin.presentation.utils.DateFormatter


@Composable
fun ProductScreen(productViewModel: ProductViewModel = hiltViewModel()) {
    val products by productViewModel.products.collectAsState()
    val searchQuery by productViewModel.searchQuery.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditAmountDialog by remember { mutableStateOf(false) }
    var editableItem by remember { mutableStateOf<Item?>(null) }
    Column(modifier = Modifier.fillMaxSize()) {
        Header()
        SearchField(value = searchQuery) {
            productViewModel.updateQuery(it)
        }
        ItemList(
            items = products,
            onEditAmount = {
                editableItem = it
                showEditAmountDialog = true
            },
            onDeleteItem = {
                editableItem = it
                showDeleteDialog = true
            })
    }
    if (showDeleteDialog) {
        DeleteConfirmationDialog(onDismiss = { showDeleteDialog = false })
        {
            editableItem?.let { productViewModel.deleteItem(it) }
            showDeleteDialog = false
        }
    }
    if (showEditAmountDialog) {
        editableItem?.let { item ->
            AmountEditDialog(currentAmount = item.amount, onDismiss = {
                showEditAmountDialog = false
            }, onConfirm = { newAmount ->
                productViewModel.editItemAmount(item, newAmount)
                showEditAmountDialog = false
            }
            )
        }
    }

}

@Composable
private fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.primary
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Список Товаров",
            modifier = Modifier.padding(0.dp, 16.dp),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Composable
private fun SearchField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
        },
        label = { Text(text = "Поиск Товаров") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        )
}

@Composable
private fun ItemList(
    items: List<Item>,
    onEditAmount: (Item) -> Unit,
    onDeleteItem: (Item) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) {
            ItemCard(item = it, edit = { onEditAmount(it) }, delete = { onDeleteItem(it) })
        }
    }
}


@Composable
private fun ItemCard(item: Item, edit: () -> Unit, delete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = MaterialTheme.shapes.small,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            ItemHeader(item = item, edit = edit, delete = delete)
            ItemTags(tags = item.tags)
            ItemInfo(amount = item.amount, time = item.time)
        }
    }
}

@Composable
private fun ItemHeader(item: Item, edit: () -> Unit, delete: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            item.name,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            style = MaterialTheme.typography.headlineSmall
        )
        IconButton(onClick = edit) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "edit count ${item.name}"
            )
        }
        IconButton(onClick = delete) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "delete ${item.name}"
            )
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ItemTags(tags: List<Tag>) {
    FlowRow {
        tags.forEach { tag ->
            AssistChip(
                modifier = Modifier.padding(4.dp, 0.dp),
                onClick = {},
                label = { Text(text = tag.name) }
            )
        }
    }
}

@Composable
private fun ItemInfo(amount: Int, time: Long) {
    Row {
        Column(Modifier.weight(1f)) {
            Text(text = "На Складе", style = MaterialTheme.typography.bodyMedium)
            Text(text = amount.toString())
        }

        Column(Modifier.weight(1f)) {
            Text(text = "Дата Добавления", style = MaterialTheme.typography.bodyMedium)
            Text(text = DateFormatter.formatDate(time))
        }
    }
}

@Composable
fun DeleteConfirmationDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Удаление товара") },
        text = { Text("Вы действительно хотите удалить выбранный товар?") },
        confirmButton = {
            Button(onClick = {
                onConfirm()
            }) {
                Text("Удалить")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Отменить")
            }
        }
    )
}

@Composable
fun AmountEditDialog(
    currentAmount: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var amount by remember { mutableIntStateOf(currentAmount) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Количество Товара") },
        text = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { amount = (amount - 1).coerceAtLeast(0) }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = ""
                    )
                }
                Text(text = "$amount", style = MaterialTheme.typography.headlineSmall)
                IconButton(onClick = { amount += 1 }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                        contentDescription = ""
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(amount) }) {
                Text("Принять")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

