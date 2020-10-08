package ar.edu.ort.instituto.echeff

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    companion object CompanionObject {

    }

/*    override suspend fun add(instrument: Instrument) {

        val questionRef = db.collection("instruments")
        val query = questionRef

        try {
            val data = query
                .add(instrument.toFirebaseInstrument())
                .await()
        } catch (e: Exception) {
        }
    }*/

/*    override suspend fun getAll(): List<Instrument> {

        var instrumentList = arrayListOf<Instrument>()

        val questionRef = db.collection("instruments")
        val query = questionRef

        try {
            val data = query
                .get()
                .await()
            for (document in data) {
                Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                instrumentList.add(document.toObject<FirebaseInstrument>().toInstrument())
            }
        } catch (e: Exception) {
        }
        return instrumentList
    }
}*/

}