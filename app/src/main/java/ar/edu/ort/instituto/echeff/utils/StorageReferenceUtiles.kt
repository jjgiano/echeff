package ar.edu.ort.instituto.echeff.utils

import android.net.Uri
import android.os.Environment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

interface StorageReferenceUtiles {

    fun buscarReferencia(dato: String): StorageReference {

        val storage = FirebaseStorage.getInstance()
        var ref: StorageReference
        var url = ""
        //busco la referencia por el URL

        if (dato.isNotEmpty()) {
            url = dato
        } else {
            url = EcheffUtilities.SIN_FOTO.valor
        }

        if (url.startsWith("gs://", 0, true)) {
            ref = storage.getReferenceFromUrl(url)
        } else {
            ref = storage.getReference(url)
        }
        return ref
    }

     fun uploadImage(uri: Uri, folder: String): String {
         val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val referenceName = "$folder/$timeStamp.jpg"
        val imageRef = storageRef.child(referenceName)
        imageRef.putFile(uri)
        return imageRef.path
    }
}