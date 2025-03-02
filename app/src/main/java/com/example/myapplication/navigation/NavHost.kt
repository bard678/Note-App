package com.example.myapplication.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Note
import com.example.myapplication.navigation.screen.HomeScreen
import com.example.myapplication.navigation.screen.NoteReadScreen
import com.example.myapplication.ui.NoteAddScreen
import com.example.myapplication.viewmodel.NoteViewModel

@Composable
fun AppNavHost(viewModel: NoteViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen( viewModel = viewModel,navController = navController) }
        composable("add") { NoteAddScreen(viewModel = viewModel, navController = navController) }
        composable("read") { NoteReadScreen(viewModel = viewModel, navController = navController) }
    }
}

//region App Bar
@OptIn(ExperimentalMaterial3Api::class)
@Composable

 fun AppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Note ++ 📝",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate("add")
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add aa note", modifier = Modifier.size(30.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2575FC),
            titleContentColor = Color.White
        )
    )
}
//endregion
