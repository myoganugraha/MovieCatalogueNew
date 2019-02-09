package itk.myoganugraha.moviecataloguenew.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import itk.myoganugraha.moviecataloguenew.Provider.FavColumns;


public class DBHelper extends SQLiteOpenHelper {
    public static int DB_VERSION = 1;
    public static String DB_NAME = "movie_catalogue";

    public DBHelper(Context mContext) {
        super(mContext,DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_MOVIE = "create table " + FavColumns.TB_NAME + " (" +
                FavColumns.COLUMN_ID + " integer primary key autoincrement, " +
                FavColumns.COLUMN_TITLE + " text not null, " +
                FavColumns.COLUMN_BACKDROP + " text not null, " +
                FavColumns.COLUMN_POSTER + " text not null, " +
                FavColumns.COLUMN_RELEASE_DATE + " text not null, " +
                FavColumns.COLUMN_VOTE + " text not null, " +
                FavColumns.COLUMN_OVERVIEW + " text not null " +
                ");";

        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE MOVIE IF EXISTS " + FavColumns.TB_NAME);
        onCreate(sqLiteDatabase);
    }
}
