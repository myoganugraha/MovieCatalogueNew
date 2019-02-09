package itk.myoganugraha.moviecataloguenew.Activity;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import itk.myoganugraha.moviecataloguenew.Adapter.FavoriteAdapter;
import itk.myoganugraha.moviecataloguenew.Adapter.MovieAdapter;
import itk.myoganugraha.moviecataloguenew.R;

import static itk.myoganugraha.moviecataloguenew.Provider.DBContract.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_activity_favourite)
    RecyclerView rvFavouriteActivity;

    @BindView(R.id.toolbarFavorite)
    Toolbar toolbarFavorite;

    private Context mContext;
    private FavoriteAdapter favoriteAdapter;
    private Cursor list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        mContext = this;
        ButterKnife.bind(this);

        setSupportActionBar(toolbarFavorite);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            toolbarFavorite.setTitle("Favorite");
        }
        initComponents();
        new loadDataAsync().execute();
    }

    private void initComponents() {
        favoriteAdapter = new FavoriteAdapter(list);
        final LinearLayoutManager llm = new LinearLayoutManager(mContext);
        rvFavouriteActivity.setLayoutManager(llm);
        rvFavouriteActivity.setAdapter(favoriteAdapter);
    }

    private class loadDataAsync extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mContext.getContentResolver().query(
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

            list = cursor;
            favoriteAdapter.replaceAll(list);
        }
    }
}
