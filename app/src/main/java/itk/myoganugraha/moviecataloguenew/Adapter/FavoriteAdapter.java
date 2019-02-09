package itk.myoganugraha.moviecataloguenew.Adapter;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import itk.myoganugraha.moviecataloguenew.Activity.DetailActivity;
import itk.myoganugraha.moviecataloguenew.Model.Movie;
import itk.myoganugraha.moviecataloguenew.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.CustomViewHolder> {

    private Cursor list;

    public FavoriteAdapter(Cursor items) {
        replaceAll(items);
    }

    public void replaceAll(Cursor items) {
        list = items;
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

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.getCount();
    }

    private Movie getItem(int i){
        if (!list.moveToPosition(i)) {
            throw new IllegalStateException("Position invalid!");
        }
        return new Movie(list);
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

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvSynopsis.setText(movie.getOverview());
            tvReleaseDate.setText(getStringFormatted(movie.getReleaseDate()));

            Glide.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                    .into(ivPoster);

           cardViewAdapter.setOnClickListener(new View.OnClickListener() {
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
