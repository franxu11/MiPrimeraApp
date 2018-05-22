package databaseApp;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User WHERE username IS :username")
    User findByUsername(String username);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
