// in app/src/androidTest/java/com/burraco/app/DatabaseTest.kt
package com.burraco.app

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.burraco.app.data.BurracoDatabase
import com.burraco.app.data.dao.PlayerDao
import com.burraco.app.data.entity.Player
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class) // Specifica il runner per i test Android
class DatabaseTest {

    private lateinit var db: BurracoDatabase  //lateint = late inizialization (Kotlin obbliga ad inizializzare) dichiari che la varibile non sarà null e che verrà inizializzata più tardi
    private lateinit var playerDao: PlayerDao

    // Questo metodo viene eseguito PRIMA di ogni test (@Test)
    @Before
    fun createDb() {
        // Usiamo un database in-memory, che vive solo finché il test è in esecuzione.
        // Questo garantisce che ogni test sia isolato e non influenzi gli altri e che non venga modificato il vero database
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BurracoDatabase::class.java
        )
            .allowMainThreadQueries() // Permette di eseguire query sul thread principale (solo per i test!)
            .build()

        playerDao = db.playerDao()
    }

    // Questo metodo viene eseguito DOPO ogni test
    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close() // Chiude il database per liberare le risorse
    }

    // Il nostro primo caso di test!
    @Test
    @Throws(Exception::class)
    fun insertPlayerAndReadItBack() = runBlocking {
        // 1. PREPARAZIONE: Crea un oggetto da inserire
        val player = Player(id = 1, name = "Alice", matchedPlayed = 0, matchedWon = 0)

        // 2. AZIONE: Inserisci l'oggetto nel database
        playerDao.insertPlayer(player) // Assumo che il tuo metodo si chiami insertPlayer

        // 3. VERIFICA: Leggi i dati e controlla che siano corretti

        // Usiamo .first() perché getAllPlayers restituisce un Flow.
        // .first() raccoglie il primo valore emesso dal Flow.
        val allPlayers = playerDao.getPlayerByMatchWon().first()  //.first prende il primo valore emesso dal flow e cancella "l'iscrizione"

        // Usiamo la libreria Truth per rendere le asserzioni più leggibili
        assertThat(allPlayers).contains(player)
        assertThat(allPlayers).hasSize(1) // Controlliamo che ci sia un solo giocatore
    }

    @Test
    @Throws(Exception::class)
    fun deletePlayer() = runBlocking {
        // Preparazione
        val player1 = Player(id = 1, name = "Alice")
        val player2 = Player(id = 2, name = "Bob")
        playerDao.insertPlayer(player1)
        playerDao.insertPlayer(player2)

        // Azione
        playerDao.deletePlayer(player1) // Assumo che tu abbia un metodo deletePlayer

        // Verifica
        val allPlayers = playerDao.getAllPlayers().first()
        assertThat(allPlayers).doesNotContain(player1)
        assertThat(allPlayers).contains(player2)
        assertThat(allPlayers).hasSize(1)
    }
}
