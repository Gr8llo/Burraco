package com.burraco.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.burraco.app.data.dao.PlayerDao
import com.burraco.app.data.entity.Player

@Database(
    entities = [Player::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class BurracoDatabase: RoomDatabase() {
    // Questo metodo è FONDAMENTALE.
    // Il suo nome "playerDao" deve corrispondere a quello usato in AppModule.
    // Room e Hilt lo usano per creare e fornire l'istanza del DAO.
    abstract fun playerDao(): PlayerDao
}