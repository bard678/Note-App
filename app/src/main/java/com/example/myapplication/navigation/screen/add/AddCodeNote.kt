package com.example.myapplication.navigation.screen.add

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.CodeBlock
import com.example.myapplication.data.Note
import com.example.myapplication.data.NoteType
import com.example.myapplication.data.models.ColorObject
import com.example.myapplication.ui.colors.ColorServices
import com.example.myapplication.ui.components.CodeBlockWidget
import com.example.myapplication.ui.components.ColorDropdownScreen
import com.example.myapplication.ui.components.dialogs.ConfirmAlertDialog
import com.example.myapplication.ui.fonts.TextServices
import com.example.myapplication.viewmodel.NoteViewModel

@Composable
fun AddCodeNoteScreen(
    onSave: (Note) -> Unit, // Callback to handle saving the note
    navController: NavController,
    viewModel: NoteViewModel
) {
    val selectedColor= remember { mutableStateOf( ColorObject(Color(0xFF002F50), "Deep Navy")) }

    var codeList= remember { mutableStateListOf<CodeBlock>() }
    var title= remember { mutableStateOf("title") }
    var showSaveDialog= remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .background(selectedColor.value.color)
            .fillMaxSize()
            .padding(top = 20.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(top = 30.dp).background(Color.Black.copy(0.1f))
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
                    navController.popBackStack()
                    //TODO insert code note

                    viewModel.addCodeNote(
                        title = title.value,
                        content = "",
                        color = selectedColor.value.color.toArgb(),

                        codeBlocks = codeList
                    )
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
        TextField(

            modifier = Modifier.padding(start = 0.dp),

            value = title.value,
            onValueChange = {
                title.value=it
            }
        )

        if(showSaveDialog.value){
            var context= LocalContext.current
            ConfirmAlertDialog(
                onConfirm = {


                    //TODO insert code note

                    viewModel.addCodeNote(
                        title = title.value,
                        content = "",
                        color = selectedColor.value.color.toArgb(),

                        codeBlocks = codeList
                    )
                    navController.popBackStack()

                    showSaveDialog.value=false
                    Toast.makeText(context,"Saved successfully",Toast.LENGTH_SHORT).show()
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

                    var description = remember { mutableStateOf("Description") }
                    var language = remember { mutableStateOf("Java") }
                    var code = remember { mutableStateOf("Java()") }
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


