package itk.myoganugraha.moviecataloguenew.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import itk.myoganugraha.moviecataloguenew.Activity.DetailActivity;
import itk.myoganugraha.moviecataloguenew.Model.Movie;
import itk.myoganugraha.moviecataloguenew.R;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CustomViewHolder>{

    private List<Movie> movieList = new ArrayList<>();
    private Context mContext;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<Movie> getMovieList(){
        return movieList;
    }

    public void clearAll() {
        movieList.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(List<Movie> movieList) {
        this.movieList.clear();
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public void updateData(List<Movie> movieList) {
        this.movieList.addAll(movieList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, viewGroup, false);

        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        final Movie movie = movieList.get(i);
        Log.d("check movie", movie.toString());

        customViewHolder.tvTitle.setText(movie.getTitle());
        customViewHolder.tvSynopsis.setText(movie.getOverview());
        customViewHolder.tvReleaseDate.setText(getStringFormatted(movie.getReleaseDate()));

        Glide.with(mContext)
                .load("http://image.tmdb.org/t/p/w500"  + movie.getPosterPath())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(customViewHolder.ivPoster);

        customViewHolder.cardViewAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("movieData", movie);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public String getStringFormatted(String datestring) {
        String format = "MMM dd, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        if(datestring.equalsIgnoreCase("") ) {
            return datestring;
        }
        return sdf.format(new Date(datestring.replaceAll("-", "/")));
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster)
        ImageView ivPoster;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_release_date)
        TextView tvReleaseDate;

        @BindView(R.id.tv_synopsis)
        TextView tvSynopsis;

        @BindView(R.id.cardViewList)
        CardView cardViewAdapter;


        public CustomViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);

        }




    }
}
