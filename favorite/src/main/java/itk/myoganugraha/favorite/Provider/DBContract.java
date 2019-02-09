package itk.myoganugraha.favorite.Provider;

import android.database.Cursor;
import android.net.Uri;

public class DBContract {
    public static final String AUTHOR = "itk.myoganugraha.moviecataloguenew";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHOR)
            .appendPath(FavColumns.TB_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    public static float getColumnFloat(Cursor cursor, String columnName) {
        return cursor.getFloat(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
