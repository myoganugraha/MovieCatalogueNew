package itk.myoganugraha.moviecataloguenew.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itk.myoganugraha.moviecataloguenew.API.MovieRepository;
import itk.myoganugraha.moviecataloguenew.Adapter.MovieAdapter;
import itk.myoganugraha.moviecataloguenew.BuildConfig;
import itk.myoganugraha.moviecataloguenew.Model.Movie;
import itk.myoganugraha.moviecataloguenew.Model.MovieResponse;
import itk.myoganugraha.moviecataloguenew.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {

    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = BuildConfig.ApiKey;

    private List<Movie> movieList = new ArrayList<>();

    @BindView(R.id.recyclerView_fragment_nowPlaying)
    RecyclerView rvNowPlaying;

    private Context mContext;
    private Unbinder unbinder;

    private Call<MovieResponse> apiCall;
    private MovieRepository movieRepository = new MovieRepository();

    private MovieAdapter movieAdapter;


    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        mContext = view.getContext();
        unbinder = ButterKnife.bind(this, view);

        setRetainInstance(true);
        setupList();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (apiCall != null) apiCall.cancel();
    }

    private void setupList() {
        movieAdapter = new MovieAdapter(mContext);

        final LinearLayoutManager llm = new LinearLayoutManager(mContext);
        rvNowPlaying.setLayoutManager(llm);
        rvNowPlaying.setAdapter(movieAdapter);
    }

    private void loadDataFromAPI() {
        apiCall = movieRepository.getService().getNowPlayingMovies(LANGUAGE);

        apiCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {

                    movieAdapter.replaceAll(response.body().getMovies());
                }
                else {loadFailed();}
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                loadFailed();
            }
        });
    }

    private void loadFailed() {
        Toast.makeText(mContext, "Load Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            movieList = savedInstanceState.getParcelableArrayList("movies");
            movieAdapter.replaceAll(movieList);
        } else {
            loadDataFromAPI();
        }
    }

    @Override public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", new ArrayList<>(movieAdapter.getMovieList()));
    }
}
