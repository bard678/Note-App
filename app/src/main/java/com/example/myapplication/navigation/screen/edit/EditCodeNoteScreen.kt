package com.example.myapplication.navigation.screen.edit

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.data.CodeBlock
import com.example.myapplication.data.models.ColorObject
import com.example.myapplication.ui.colors.ColorServices
import com.example.myapplication.ui.components.CodeBlockWidget
import com.example.myapplication.ui.components.dialogs.ConfirmAlertDialog
import com.example.myapplication.viewmodel.NoteViewModel

@Composable

fun EditCodeNote(
    navController: NavController,
    viewModel: NoteViewModel
)
{
    val selectedColor= remember { mutableStateOf( ColorObject(Color(0xFF002F50), "Deep Navy")) }
     var title= remember { mutableStateOf("title") }
    var showSaveDialog= remember { mutableStateOf(false) }
    var currentNote=viewModel.selectedNote

    var codeList= remember { mutableStateListOf<CodeBlock>() }
  LaunchedEffect(currentNote) {
      currentNote?.codeBlocks?.let { codeList.addAll(it.toList()) }
  }
    Column (
        modifier = Modifier
            .background(Color.Blue.copy(alpha = 0.9f))
            .fillMaxSize()
            .padding(top = 20.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            IconButton(
                onClick = {
                    showSaveDialog.value=true

                },
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
            IconButton(
                onClick = {
                    //TODO update code note

                    viewModel.updateCodeNote(
                        note=currentNote?.copy(
                            title = title.value,
                            codeBlocks = codeList
                        ))

                    navController.popBackStack()

                    showSaveDialog.value=false
                },
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
                    .size(50.dp)
                    .background(Color.White.copy(alpha = 0.2f), CircleShape)
            ) {
                Icon(Icons.Default.Check, contentDescription = "ADD", tint = Color.White)
            }
        }
        if (currentNote != null) {
            TextField(

                modifier = Modifier.padding(start = 10.dp),

                value = title.value,
                onValueChange = {
                    title.value=it
                }
            )
        }

        if(showSaveDialog.value){
            var context= LocalContext.current
            ConfirmAlertDialog(
                onConfirm = {


                    //TODO update code note

                    viewModel.updateCodeNote(
                      note=currentNote?.copy(
                          title = title.value,
                          codeBlocks = codeList
                      )
                    )
                    navController.popBackStack()

                    showSaveDialog.value=false
                    Toast.makeText(context,"Saved successfully", Toast.LENGTH_SHORT).show()
                },
                onDismiss = {
                    showSaveDialog.value=false
                    navController.navigate("home")

                },
                msg = "Do you want to save it.",
                title = "Confirm save"
            )
        }
        Column (){
            LazyColumn {
                itemsIndexed(codeList)
                {
                        i, codeBlok->

                    var description = remember { mutableStateOf(codeBlok.description) }
                    var language = remember { mutableStateOf(codeBlok.language) }
                    var code = remember { mutableStateOf(codeBlok.code) }
                    codeList.set(index = i, element =  CodeBlock(
                        description = description.value,
                        code = code.value,
                        language = language.value
                    )
                    )
                    CodeBlockWidget(
                        editable = true,
                        description = description,
                        language = language,

                        code = code,
                    )


                }
            }
            Button(
                onClick = {
                    println(codeList)
                    codeList.add(

                        CodeBlock(
                            description = "TODO()",
                            code = "TODO()",
                            language = "TODO()"
                        )
                    )
                },
                shape = RectangleShape,
                modifier = Modifier.padding(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorServices().darkBlue
                )
            ) {
                Text(
                    text = "Add Code Block"
                )
            }
        }
    }
}
