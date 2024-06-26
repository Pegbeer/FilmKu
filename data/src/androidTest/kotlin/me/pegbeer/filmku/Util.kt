import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import me.pegbeer.filmku.local.Database

fun createInMemoryDatabase():Database{
    return Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        Database::class.java
    ).build()
}