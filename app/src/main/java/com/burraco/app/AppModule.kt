package com.burraco.app

import android.content.Context
import androidx.room.Room
import com.burraco.app.data.BurracoDatabase
import com.burraco.app.data.GameSessionRepository
import com.burraco.app.data.dao.PlayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Hilt si basa eclusivamente su i tipi di ritorno per trovare le dipendenze

@Module
@InstallIn(SingletonComponent::class) // Dice a Hilt che le dipendenze vivranno quanto l'app
object AppModule {   //rispetto a class può essere creato una sola volta + non è le dipendeze ma ciò che le contiene
    @Provides
    @Singleton // Garantisce che venga creata UNA SOLA istanza del database
    fun provideBurracoDatabase(@ApplicationContext context: Context): BurracoDatabase {
        return Room.databaseBuilder(
            context,
            BurracoDatabase::class.java,
            "burraco.db"
        ).build()
    }

    // Aggiungi un "provider" anche per ogni DAO
    @Provides
    @Singleton
    fun providePlayerDao(database: BurracoDatabase): PlayerDao {
        return database.playerDao()
    }

    @Provides
    @Singleton
    fun providesGameSessionRepository(): GameSessionRepository{
        return GameSessionRepository()
    }
}