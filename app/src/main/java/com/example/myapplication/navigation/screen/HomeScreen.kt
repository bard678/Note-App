package com.example.myapplication.navigation.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.data.Note
import com.example.myapplication.data.NoteType
import com.example.myapplication.ui.components.AppBar
import com.example.myapplication.ui.components.dialogs.ConfirmAlertDialog
import com.example.myapplication.ui.components.dialogs.OptionsDialog
import com.example.myapplication.viewmodel.NoteViewModel


//region home ui
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: NoteViewModel, navController: NavController) {
   val notes by viewModel.allNote.collectAsState(initial = emptyList())
    var allNote by remember { mutableStateOf<List<Note>>(emptyList()) }
    var showOptionDialog = remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf<NoteType?>(null) }
    var showDeleteDialog= remember { mutableStateOf(false) }
    var deletedNote= remember { mutableStateOf<Note?>(null) }
    // Update allNote when selectedFilter changes
    LaunchedEffect(notes, selectedFilter) {
        allNote = when (selectedFilter) {
            null -> notes
            else -> notes.filter { it.type == selectedFilter }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E3C72),
                        Color(0xFF2A5298)
                    )
                )
            )
    ) {
        //TODO App Bar
        AppBar( showOptionDialog = showOptionDialog)

        //TODO Top Body Bar
        TopBodyBar(
            selectedFilter = selectedFilter,
            onFilterSelected = {
                noteType -> println(noteType?.name)
                selectedFilter = noteType

                println(selectedFilter?.name)
                println(allNote.size)
            }
        )
   //TODO show option dialog before add note
        if(showOptionDialog.value){
            OptionsDialog(
                onDismiss = {showOptionDialog.value=false},
                onOptionSelected = {
                    //TODO Manage navigation for Add screens
                    noteType ->
                     //TODO Set the selected note type in viewModel class
                    viewModel.selectedNoteType=noteType
                    println("${ viewModel?.selectedNoteType?.name }")
                    showOptionDialog.value=false
                    when(viewModel.selectedNoteType){
                        NoteType.CODE->  navController.navigate("addCode")
                        NoteType.TASK_MANAGEMENT -> navController.navigate("addTask")
                        NoteType.MIND_MAP ->  navController.navigate("addMindMap")
                        null -> {}
                    }

                },

            )
        }
        if(showDeleteDialog.value){
            ConfirmAlertDialog(
                onDismiss = {showDeleteDialog.value=false },
                onConfirm = {
                   if(deletedNote.value!=null){
                       viewModel.deleteNote(deletedNote.value)
                   }
                    showDeleteDialog.value=false
                            },
                msg = "Do you want to delete it.",
                title = "Confirm delete"
            )
        }
        //TODO Display list of notes
        if (allNote.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(allNote, key = { it.id }) { note ->
                    NoteItem(
                        note = note,
                        onEdit = {
                            //TODO Go to Edit Screen
                            viewModel.selectedNote = note

                            when(note.type){
                                NoteType.CODE -> navController.navigate("editCodeNote")
                                NoteType.TASK_MANAGEMENT -> navController.navigate("editCodeNote")
                                NoteType.MIND_MAP -> navController.navigate("editCodeNote")
                                null -> {}
                            }
                        },
                        onDelete = {
                            showDeleteDialog.value=true
                            deletedNote.value=note
                                   },
                        onClick = {
                            //TODO Go to Read Screen
                            viewModel.selectedNote = note
                            when(note.type){
                                NoteType.CODE -> navController.navigate("readCodeNote")
                                NoteType.TASK_MANAGEMENT -> navController.navigate("read")
                                NoteType.MIND_MAP -> navController.navigate("read")
                                null -> {}
                            }
                        },
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }
        } else {
            // No Notes UI
            EmptyStateUI(navController,showOptionDialog)
        }
    }
}

//TODO Note item (Card)
@Composable
fun NoteItem(note: Note,onEdit: ()->Unit, onDelete: () -> Unit, onClick: () -> Unit, modifier: Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f)),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(160.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Type Indicator (Left Side)
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .clip(CircleShape)
                    .background(getNoteTypeColor(note.type)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier =Modifier.size(30.dp),
                    painter = painterResource(getNoteTypeIcon(note.type)),
                    contentDescription = note.type?.name ?: "Note Type",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(8.dp)) // Spacing between icon and text

            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Type Label Above Title
                Text(
                    text = note.type?.name ?: "Unknown",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = getNoteTypeColor(note.type),
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = note.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }
//TODO delete button
            Column(
                verticalArrangement = Arrangement.SpaceBetween
                , modifier = Modifier.fillMaxHeight()
            ){
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Note",
                        tint = Color(0xFFFF6B6B)
                    )
                }
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Note",
                        tint = Color(0xFFFF6B6B)
                    )
                }
            }
        }
    }
}

// Function to get color based on note type
@Composable
fun getNoteTypeColor(type: NoteType?): Color {
    return when (type) {
        NoteType.CODE -> Color(0xFF4CAF50) // Green for Code
        NoteType.MIND_MAP -> Color(0xFF03A9F4) // Blue for Mind Map
        NoteType.TASK_MANAGEMENT -> Color(0xFFFFC107) // Yellow for Tasks
        else -> Color.Gray
    }
}

// Function to get icon based on note type
@Composable
fun getNoteTypeIcon(type: NoteType?): Int {
    return when (type) {
        NoteType.CODE -> R.drawable.code // Code icon
        NoteType.MIND_MAP -> R.drawable.map// Mind Map icon
        NoteType.TASK_MANAGEMENT ->R.drawable.check_circle_outline// Task icon
        else -> R.drawable.description
    }
}

// Empty State UI
@Composable
fun EmptyStateUI(navController: NavController,showOptionDialog:MutableState<Boolean>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.event_note),
                contentDescription = "No notes",
                modifier = Modifier.size(80.dp),
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "No notes found",
                fontSize = 18.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    showOptionDialog.value=true
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text(text = "Add a Note", color = Color.White)
            }
        }
    }
}

//TODO Top Body Bar

@Composable
fun TopBodyBar(
    selectedFilter: NoteType?,
    onFilterSelected: (NoteType?) -> Unit
) {
    var scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // All Notes Button
        FilterButton(
            label = "All",
            isSelected = selectedFilter == null,
            color = Color.Gray,
            onClick = { onFilterSelected(null) }
        )

        // Code Notes Button
        FilterButton(
            label = "Code",
            isSelected = selectedFilter == NoteType.CODE,
            color = Color(0xFF4CAF50),
            onClick = { onFilterSelected(NoteType.CODE) }
        )

        // Task Management Button
        FilterButton(
            label = "Tasks",
            isSelected = selectedFilter == NoteType.TASK_MANAGEMENT,
            color = Color(0xFFFFC107),
            onClick = { onFilterSelected(NoteType.TASK_MANAGEMENT) }
        )

        // Mind Map Button
        FilterButton(
            label = "Mind Map",
            isSelected = selectedFilter == NoteType.MIND_MAP,
            color = Color(0xFF03A9F4),
            onClick = { onFilterSelected(NoteType.MIND_MAP) }
        )
    }
}

// Reusable Filter Button
@Composable
fun FilterButton(label: String, isSelected: Boolean, color: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) color else color.copy(alpha = 0.5f),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
    }
}

//endregion