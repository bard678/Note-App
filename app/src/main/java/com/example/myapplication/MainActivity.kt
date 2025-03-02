package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.TEST.FakeNoteReadScreen
import com.example.myapplication.data.Note
import com.example.myapplication.data.NoteDatabase
import com.example.myapplication.navigation.AppNavHost
import com.example.myapplication.ui.NoteAddScreen
import com.example.myapplication.viewmodel.NoteViewModel
import com.example.myapplication.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val database = remember { NoteDatabase.getDatabase(this) }

            val viewModelFactory = ViewModelFactory(database)

            val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)
         // viewModel.addNote(title = "Java","Hello",0x002F50)
         //AppNavHost(viewModel)
          // FakeNoteReadScreen()
            AppNavHost(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun HomeScreen(viewModel: NoteViewModel){
   val allNote:List<Note> by viewModel.allNote.collectAsState(initial = emptyList())
    Column {

        AppBar()
      Column (
          modifier = Modifier
              .fillMaxHeight()
              .background(color = Color.LightGray)
              .fillMaxWidth()
      ){
          TopBodyBar()
        //  Content
          LazyColumn(modifier = Modifier.padding(16.dp)) {
              items(allNote) { note ->
                NoteItem(
                    note =note,
                    onDelete = {
                      viewModel.deleteNote(note)
                    }
                )
              }
          }
      }
    }
}

@Composable
fun NoteItem(note: Note, onDelete: () -> Unit) {
    // Card container to hold the note
    Card(

        colors = CardColors(
            //   containerColor = Color(note.color),
            containerColor = Color.Blue,
            contentColor = Color.White,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(150.dp)
        ,
        shape = RoundedCornerShape(12.dp), // Rounded corners for the card
//        elevation = CardElevation(
//            defaultElevation = 4.dp,
//            pressedElevation = 4.dp,
//            focusedElevation = 4.dp,
//            hoveredElevation = 4.dp,
//            draggedElevation = 4.dp,
//            disabledElevation = 4.dp
//        ) // Card elevation to give a shadow effect
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
                    text = "Java Course", // Replace with note.title when you have data
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
                    text = "Java is a high-level, object-oriented programming language used for building a wide range of applications, from mobile apps to web servers. It is known for its portability, scalability, and security, as it can run on any device that has a Java Virtual Machine (JVM).",
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


//region App Bar
@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AppBar() {
    TopAppBar(
        title = { Text(text = "Note ++ \uD83D\uDE03") },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add aa note", modifier = Modifier.size(30.dp)
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = Color.Blue,
            scrolledContainerColor = Color.Blue,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
        )
    )
}
//endregion


//region Top Body Bar
@Composable
private fun TopBodyBar() {
    Row(

        modifier = Modifier.fillMaxWidth().background(Color.White).padding(10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "All Notes ",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Outlined.List,
                contentDescription = "Add aa note", modifier = Modifier.size(30.dp)
            )
        }
    }
}
//endregion
