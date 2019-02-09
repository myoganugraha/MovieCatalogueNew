package itk.myoganugraha.moviecataloguenew.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import itk.myoganugraha.moviecataloguenew.Provider.FavColumns;

import static android.provider.BaseColumns._ID;

public class FavoriteHelper {
    private static String TABLE_NAME = FavColumns.TB_NAME;

    private Context mContext;
    private DBHelper dbHelper;

    private SQLiteDatabase database;

    public FavoriteHelper(Context mContext) {
        this.mContext = mContext;
    }

    public FavoriteHelper open() throws SQLException {
        dbHelper = new DBHelper(mContext);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
       dbHelper.close();
    }

    public Cursor queryProvider() {
        return database.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(
                TABLE_NAME,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long insertProvider(ContentValues values) {
        return database.insert(
                TABLE_NAME,
                null,
                values
        );
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(
                TABLE_NAME,
                values,
                _ID + " = ?",
                new String[]{id}
        );
    }

    public int deleteProvider(String id) {
        return database.delete(
                TABLE_NAME,
                _ID + " = ?",
                new String[]{id}
        );
    }
}
