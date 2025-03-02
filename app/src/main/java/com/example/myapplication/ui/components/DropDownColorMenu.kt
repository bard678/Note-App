package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.models.ColorObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorDropdownScreen(selectedColor:MutableState<ColorObject>) {
    // List of deep colors
    val deepColors = listOf(
        ColorObject(Color(0xFF002F50), "Deep Navy"),
        ColorObject(Color(0xFF003D32), "Forest Green"),
        ColorObject(Color(0xFF4A4A4A), "Charcoal Gray"),
        ColorObject(Color(0xFF4B0032), "Burgundy Red"),
        ColorObject(Color(0xFFD78815), "Golden Amber"),
        ColorObject(Color(0xFF8C3417), "Rusty Red"),
        ColorObject(Color(0xFF560622), "Crimson Red")
    )

    // State to hold selected color

    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {




        // Dropdown button
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value =selectedColor.value.name ,
                onValueChange = { },
                label = { Text("Select Option") },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                },
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Dropdown menu items
              deepColors.forEach { option ->
                    DropdownMenuItem(
                        text = {

                           Row (
                               modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                           ) {
                               Text(text = option.name)
                               Box(
                                   modifier = Modifier.size(20.dp).background(option.color)
                               )
                           }
                               },
                        onClick = {
                            selectedColor.value = option
                            println(selectedColor.value.color.toArgb())
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}



// Extension function to convert Color to Hex string
fun Color.toHex(): String {
    return String.format("%06X", 0xFFFFFF and toArgb())
}