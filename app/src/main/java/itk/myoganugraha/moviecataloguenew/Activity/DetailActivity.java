package itk.myoganugraha.moviecataloguenew.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import itk.myoganugraha.moviecataloguenew.Database.FavoriteHelper;
import itk.myoganugraha.moviecataloguenew.Model.Movie;
import itk.myoganugraha.moviecataloguenew.Provider.FavColumns;
import itk.myoganugraha.moviecataloguenew.R;

import static itk.myoganugraha.moviecataloguenew.Provider.DBContract.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_ITEM = "movie_item";

    @BindView(R.id.detailPoster)
    ImageView backdrop;

    @BindView(R.id.fav_btn)
    ImageView favBtn;

    @BindView(R.id.ratingBar_detail)
    RatingBar movieRating;

    @BindView(R.id.movieSynopsis_detail)
    TextView synopsis;

    @BindView(R.id.movieTitle_detail)
    TextView movieTitle;

    @BindView(R.id.movieRelease_detail)
    TextView movieRelease;
    
    private Context mContext;
    private boolean isFavorite = false;

    private FavoriteHelper favoriteHelper;

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);
        mContext = this;
        
        initComponents();
    }

    private void initComponents() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorite) RemoveFav();
                else SaveFav();

                isFavorite = !isFavorite;
                favoriteSet();

            }
        });
        getMovieDetail();
    }

    private void getMovieDetail() {
        loadDataSQLite();
        movie = getIntent().getParcelableExtra("movieData");

        movieRelease.setText(getStringFormatted(movie.getReleaseDate()));
        movieTitle.setText(movie.getTitle());
        movieRating.setVisibility(View.VISIBLE);//set rating visible
        movieRating.setRating(movie.getVoteAverage()/2);//set rating value
        synopsis.setText(movie.getOverview()); //Set


        Glide.with(getApplicationContext()) //Load image on backdrop with glide
                .load( "http://image.tmdb.org/t/p/w780" + movie.getBackdropPath())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(backdrop);

    }

    public String getStringFormatted(String datestring) {
        String format = "MMM dd, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date(datestring.replaceAll("-", "/")));
    }

    private void favoriteSet() {
        if (isFavorite) favBtn.setImageResource(R.drawable.ic_favorite_24dp);
        else favBtn.setImageResource(R.drawable.ic_favorite_border_24dp);
    }

    private void RemoveFav() {
        getContentResolver().delete(
                Uri.parse(CONTENT_URI + "/" + movie.getId()),
                null,
                null
        );
        Toast.makeText(DetailActivity.this, "Removed from Favorite", Toast.LENGTH_SHORT).show();
    }


    private void SaveFav() {
        //Log.d("TAG", "FavoriteSave: " + item.getId());
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavColumns.COLUMN_ID, movie.getId());
        contentValues.put(FavColumns.COLUMN_TITLE, movie.getTitle());
        contentValues.put(FavColumns.COLUMN_BACKDROP, movie.getBackdropPath());
        contentValues.put(FavColumns.COLUMN_POSTER, movie.getPosterPath());
        contentValues.put(FavColumns.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(FavColumns.COLUMN_VOTE, movie.getVoteAverage());
        contentValues.put(FavColumns.COLUMN_OVERVIEW, movie.getOverview());

        getContentResolver().insert(CONTENT_URI, contentValues);
        Toast.makeText(DetailActivity.this, "Added to Favorite", Toast.LENGTH_SHORT).show();
    }

    private void loadDataSQLite() {
        favoriteHelper = new FavoriteHelper(this);
        favoriteHelper.open();

        movie = getIntent().getParcelableExtra("movieData");
        Cursor cursor = getContentResolver().query(
                Uri.parse(CONTENT_URI + "/" + movie.getId()),
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) isFavorite = true;
            cursor.close();
        }
        favoriteSet();
    }

}
