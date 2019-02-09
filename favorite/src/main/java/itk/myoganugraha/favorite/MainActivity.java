package itk.myoganugraha.favorite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import itk.myoganugraha.favorite.Adapter.FavoriteAdapter;
import itk.myoganugraha.favorite.Provider.DBContract;

import static itk.myoganugraha.favorite.Provider.DBContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.favorite_recyclerview)
    RecyclerView recyclerViewFavorite;

    private Cursor cursors;
    private FavoriteAdapter favoriteAdapter;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        MainActivity.this.setTitle("Favorite Movies");
        mContext = this;
        
        initComponents();
        new loadDataAsync().execute();
    }

    private void initComponents() {
        favoriteAdapter = new FavoriteAdapter(cursors);
        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewFavorite.setAdapter(favoriteAdapter);
    }

    private class loadDataAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            );
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            cursors= cursor;
            favoriteAdapter.replaceAll(cursors);
        }
    }
}
