package com.example.myapplication.ui.components.dialogs


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShareDialog(
    onDismiss: () -> Unit,
    shareText: String
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Share") },
        text = { Text("Select an option to share") },
        confirmButton = {
            TextButton(onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, shareText)
                }
                context.startActivity(Intent.createChooser(intent, "Share via"))
                onDismiss()
            }) {
                Text("Share")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun PreviewShareDialog() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        ShareDialog(
            onDismiss = { showDialog = false },
            shareText = "Check out this cool app! 🚀"
        )
    }
}
