package itk.myoganugraha.moviecataloguenew.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import itk.myoganugraha.moviecataloguenew.API.MovieRepository;
import itk.myoganugraha.moviecataloguenew.Adapter.MovieAdapter;
import itk.myoganugraha.moviecataloguenew.Model.MovieResponse;
import itk.myoganugraha.moviecataloguenew.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.query_searchResult)
    TextView searchResult;

    @BindView(R.id.searcResult_recycler_view)
    RecyclerView rvSearchResult;


    private Context mContext;

    private Call<MovieResponse> apiCall;
    private MovieRepository movieRepository = new MovieRepository();

    private MovieAdapter movieAdapter;

    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        mContext = this;
        ButterKnife.bind(this);

        initComponents();
        loadData();
    }

    private void initComponents() {

        query = getIntent().getStringExtra("query");
        searchResult.setText(query);
        movieAdapter = new MovieAdapter(mContext);

        final LinearLayoutManager llm = new LinearLayoutManager(mContext);
        rvSearchResult.setLayoutManager(llm);
        rvSearchResult.setAdapter(movieAdapter);


    }

    private void loadData() {
        apiCall = movieRepository.getService().searchMovie(query);

        apiCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {

                    movieAdapter.replaceAll(response.body().getMovies());
                } else {
                    loadFailed();
                }
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
}