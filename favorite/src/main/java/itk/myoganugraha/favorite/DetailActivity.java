package itk.myoganugraha.favorite;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import itk.myoganugraha.favorite.Model.Movie;
import itk.myoganugraha.favorite.R;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.detailPoster)
    ImageView backdrop;


    @BindView(R.id.ratingBar_detail)
    RatingBar movieRating;

    @BindView(R.id.movieSynopsis_detail)
    TextView synopsis;

    @BindView(R.id.movieTitle_detail)
    TextView movieTitle;

    @BindView(R.id.movieRelease_detail)
    TextView movieRelease;

    private Context mContext;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        mContext = this;

        loadData();
        placeData();
    }

    private void placeData() {
        movie = getIntent().getParcelableExtra("movieData");
        if(movie == null) return;

        movieRelease.setText(getStringFormatted(movie.getReleaseDate()));
        movieTitle.setText(movie.getTitle());
        movieRating.setVisibility(View.VISIBLE);//set rating visible
        movieRating.setRating(movie.getVoteAverage()/2);//set rating value
        synopsis.setText(movie.getOverview()); //Set


        Glide.with(mContext) //Load image on backdrop with glide
                .load( "http://image.tmdb.org/t/p/w780" + movie.getBackdropPath())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(backdrop);

    }

    public String getStringFormatted(String datestring) {
        String format = "MMM dd, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(new Date(datestring.replaceAll("-", "/")));
    }

    private void loadData() {
        Uri uri = getIntent().getData();
        if (uri == null) return;
        Cursor cursor = getContentResolver().query(
                uri,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) movie = new Movie(cursor);
            cursor.close();
        }
    }
}
