package com.example.myapplication.TEST


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.RoomDb.Note
import com.example.myapplication.RoomDb.NoteType
import com.example.myapplication.RoomDb.TaskItem
import com.example.myapplication.RoomDb.models.ColorObject
@Preview
@Composable
fun TestTaskManagementScreen(
   // onSaveNote: (Note) -> Unit // Callback to save the note
) {
    // List of tasks that will be added to the note
    var tasks by remember { mutableStateOf(listOf<TaskItem>()) }
    val selectedColor= remember { mutableStateOf( ColorObject(Color(0xFF002F50), "Deep Navy")) }

    // To handle new task description input
    var taskDescription by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(top = 40.dp).fillMaxHeight().padding(16.dp)) {
        Text("Create Task Management Note", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Task Description Input
        OutlinedTextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            label = { Text("Task Description") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Add Task Button
        Button(
            onClick = {
                if (taskDescription.isNotBlank()) {
                    // Add the task to the list of tasks
                    tasks = tasks + TaskItem(taskDescription, isCompleted = false)
                    taskDescription = "" // Clear input field
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Task List
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            itemsIndexed(tasks) { index, task ->
                TaskItemRow(
                    task = task,
                    onTaskToggle = {
                        // Toggle task completion status
                        tasks = tasks.toMutableList().apply {
                            this[index] = this[index].copy(isCompleted = !task.isCompleted)
                        }
                    },
                    onRemoveTask = {
                        // Remove task from the list
                        tasks = tasks.toMutableList().apply {
                            removeAt(index)
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save Note Button
        Button(
            onClick = {
                // Create a new TASK_MANAGEMENT note with the tasks list
                val note = Note(
                    title = "New Task Management Note",
                    type = NoteType.TASK_MANAGEMENT,
                    tasks = tasks,
                    color =selectedColor.value.color.toArgb()
                )
                //onSaveNote(note) // Pass the note to be saved
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Note")
        }
    }

}

@Composable
fun TaskItemRow(
    task: TaskItem,
    onTaskToggle: () -> Unit, // Callback to toggle completion
    onRemoveTask: () -> Unit // Callback to remove the task
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // CheckBox for task completion
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onTaskToggle() }
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Task Description
        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f),
            textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Remove Task Button
        IconButton(onClick = { onRemoveTask() }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove Task")
        }
    }
}

