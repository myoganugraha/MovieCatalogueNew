package itk.myoganugraha.favorite.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import itk.myoganugraha.favorite.DetailActivity;
import itk.myoganugraha.favorite.Model.Movie;
import itk.myoganugraha.favorite.R;

import static itk.myoganugraha.favorite.Provider.DBContract.getColumnString;
import static itk.myoganugraha.favorite.Provider.FavColumns.COLUMN_OVERVIEW;
import static itk.myoganugraha.favorite.Provider.FavColumns.COLUMN_POSTER;
import static itk.myoganugraha.favorite.Provider.FavColumns.COLUMN_RELEASE_DATE;
import static itk.myoganugraha.favorite.Provider.FavColumns.COLUMN_TITLE;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.CustomViewHolder>{

    private Cursor cursor;

    public FavoriteAdapter(Cursor cursor){
        replaceAll(cursor);
    }

    public void replaceAll(Cursor cursor) {
        this.cursor = cursor;
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
        customViewHolder.bind(getItem(i));
    }

    private Movie getItem(int position){
        if(!cursor.moveToPosition(position)){
            throw new IllegalStateException("Position Invalid");
        }
        return new Movie(cursor);
    }

    @Override
    public int getItemCount() {
        if (cursor == null) return 0;
        else return cursor.getCount();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_poster)
        ImageView ivPoster;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_synopsis)
        TextView tvSynopsis;

        @BindView(R.id.tv_release_date)
        TextView tvReleaseDate;

        @BindView(R.id.cardViewList)
        CardView cardView;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvSynopsis.setText(movie.getOverview());
            tvReleaseDate.setText(getStringFormatted(movie.getReleaseDate()));

            Glide.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(ivPoster);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    intent.putExtra("movieData", movie);
                    view.getContext().startActivity(intent);
                }
            });
        }

        public String getStringFormatted(String datestring) {
            String format = "MMM dd, yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            if(datestring.equalsIgnoreCase("") ) {
                return datestring;
            }
            return sdf.format(new Date(datestring.replaceAll("-", "/")));
        }
    }
}
