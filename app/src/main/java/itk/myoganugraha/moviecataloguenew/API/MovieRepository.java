package itk.myoganugraha.moviecataloguenew.API;

import java.io.IOException;

import itk.myoganugraha.moviecataloguenew.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    private static final String BASE_URL_API = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = BuildConfig.ApiKey;

    private BaseAPIService baseAPIService;

    public MovieRepository(){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl httpUrl = request.url()
                                .newBuilder()
                                .addQueryParameter("api_key",  API_KEY)
                                .build();

                        request = request.newBuilder()
                                .url(httpUrl)
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        baseAPIService = retrofit.create(BaseAPIService.class);
    }

    public BaseAPIService getService(){
        return baseAPIService;
    }
}
