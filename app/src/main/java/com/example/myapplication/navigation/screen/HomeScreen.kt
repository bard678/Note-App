package com.example.myapplication.navigation.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.Note
import com.example.myapplication.navigation.AppBar
import com.example.myapplication.viewmodel.NoteViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: NoteViewModel,navController: NavController) {
    val allNote: List<Note> by viewModel.allNote.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A11CB), Color(0xFF2575FC))
                )
            )
    ) {
        AppBar(navController)
        TopBodyBar()

        // Content list of notes
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(allNote) { note ->
                NoteItem(
                    modifier = Modifier.animateItemPlacement(),
                    note = note,
                    onDelete = { viewModel.deleteNote(note) },
                    onClick = {
                        viewModel.selectedNote=note
                       navController.navigate("read")

                    }
                )
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: () -> Unit, onClick: () -> Unit,  modifier :Modifier) {
    // Card container to hold the note
    Card(

        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.2f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
        ,
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        // Column to vertically align title, content, and delete button
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(), // Make the row fill the width of the screen
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // This ensures spacing between content and delete button
        ) {
            Column(
                modifier = Modifier
                    .weight(1f) // This makes sure the column takes up the remaining space
            ) {
                // Note title
                Text(
                    text = note.title, // Replace with note.title when you have data
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White, // Text color is white for contrast
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis // Truncate long titles
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Note content
                Text(
                    modifier = Modifier
                        .width(200.dp) // Limit the width of the content
                        .padding(5.dp),
                    text =note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f), // Lighter text color
                    maxLines = 4,
                    fontWeight = FontWeight.W500,
                    overflow = TextOverflow.Ellipsis // Truncate long content
                )
            }

            // Delete button
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Note",
                    tint = Color.White
                )
            }
        }
    }
}

//TIP <b>Run</b>

//region Top Body Bar
@Composable
private fun TopBodyBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp,5.dp)
            .background(Color.White.copy(alpha = 0.8f),)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "All Notes",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF2575FC)
        )
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = "List View",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFF2575FC)
            )
        }
    }
}
//endregion
