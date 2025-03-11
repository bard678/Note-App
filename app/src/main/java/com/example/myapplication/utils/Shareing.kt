package com.example.myapplication.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.myapplication.data.Note
import createPdfWithHeaderFooter


fun sharePdf(context: Context, fileName: String,note: Note){
    val file=createPdfWithHeaderFooter(context,fileName,note)

    if(file!=null&&file.exists()){
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
        val intent= Intent(Intent.ACTION_SEND).apply {

            type="application/pdf"
            putExtra(Intent.EXTRA_STREAM,uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent,"Share pdf via"))
    }
}