package com.burraco.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.burraco.app.data.dao.PlayerDao
import com.burraco.app.data.entity.Player

@Database(
    entities = [Player::class],
    version = 1
)
abstract class BurracoDatabase: RoomDatabase() {
    // Questo metodo è FONDAMENTALE.
    // Il suo nome "playerDao" deve corrispondere a quello usato in AppModule.
    // Room e Hilt lo useranno per creare e fornire l'istanza del DAO.
    abstract fun playerDao(): PlayerDao
}