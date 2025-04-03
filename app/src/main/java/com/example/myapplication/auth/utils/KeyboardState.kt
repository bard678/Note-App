package com.example.myapplication.auth.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun rememberKeyboardVisibility(): Boolean {
    val view = LocalView.current
    val density = LocalDensity.current
    var isKeyboardVisible by remember { mutableStateOf(false) }

    DisposableEffect(view) {
        val listener = ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            val imeHeight = with(density) { insets.getInsets(WindowInsetsCompat.Type.ime()).bottom.toDp() }
            isKeyboardVisible = imeHeight > 0.dp
            insets
        }
        onDispose { listener?.let { ViewCompat.setOnApplyWindowInsetsListener(view, null) } }
    }

    return isKeyboardVisible
}