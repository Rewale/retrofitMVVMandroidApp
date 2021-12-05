//package com.example.movieappmvvmretrofit2;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
//import androidx.appcompat.widget.Toolbar;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.Button;
//
//import android.widget.Toast;
//
//
//import com.example.movieappmvvmretrofit2.adapters.MovieRecyclerView;
//import com.example.movieappmvvmretrofit2.adapters.OnMovieListener;
//import com.example.movieappmvvmretrofit2.models.MovieModel;
//import com.example.movieappmvvmretrofit2.request.Service;
//import com.example.movieappmvvmretrofit2.response.MovieSearchResponse;
//import com.example.movieappmvvmretrofit2.utlis.Credentials;
//import com.example.movieappmvvmretrofit2.utlis.MovieApi;
//import com.example.movieappmvvmretrofit2.viewModels.MovieListViewModel;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MovieListActivity extends AppCompatActivity implements OnMovieListener {
//
//    //RecyclerView
//    private RecyclerView recyclerView;
//    private MovieRecyclerView adapter;
//
//    //ViewModel
//    private MovieListViewModel movieListViewModel;
//    // TODO:не отображаются тосты
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        recyclerView = findViewById(R.id.recyclerView);
//        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        ConfigureRecyclerView();
//
//        // Calling the observer
//        ObserverAnyChange();
//
//        // SearchView
//        SetupSearchView();
//
//
//        //Toast.makeText(this, "123ввавваапк4пп", Toast.LENGTH_SHORT).show();
//
//    }
//
//    // Get data from searchView & query
//    private void SetupSearchView() {
//        final SearchView searchView = findViewById(R.id.search_view);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                movieListViewModel.searchMovieApi(query,1);
//                searchView.clearFocus();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//    }
//
//
//    // Observing any data change
//    private void ObserverAnyChange(){
//        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
//            @Override
//            public void onChanged(List<MovieModel> movieModels) {
//                // Слушатель изменений
//                if(movieModels != null){
//                    for(MovieModel movieModel: movieModels){
//                        // Getting data in the log
//                        Log.v("Tagy", "OnChanged: "+movieModel.getTitle());
//                    }
//                    adapter.setMovies(movieModels);
//
//                }
//            }
//        });
//    }
//
//    // 4- Calling the method in MainActivity
//    private void searchMovieApi(String query, int pageNumber){
//        movieListViewModel.searchMovieApi(query, pageNumber);
//    }
//    // 5
//    private void ConfigureRecyclerView(){
//        adapter = new MovieRecyclerView(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        // pagination
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                if(!recyclerView.canScrollVertically(1)){
//                    // Here we need to display next page
//                    movieListViewModel.searchNextPage();
//                }
//            }
//        });
//
//
//
//
//    }
//
//
//
//
//    @Override
//    public void onMovieClick(int position) {
//        Intent intent = new Intent(this, MovieDetails.class);
//        //intent.putExtra("movie", movieListViewModel.getMovies().getValue().get(position));
//        intent.putExtra("movie", adapter.getSelectedMovie(position));
//        startActivity(intent);
//    }
//
//    @Override
//    public void onCategoryClick(String category) {
//
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    private void GetRetrofitResponse() {
//        MovieApi movieApi = Service.getMovieApi();
//
//        Call<MovieSearchResponse> responseCall = movieApi
//                .searchMovie(Credentials.API_KEY,
//                        "Action",
//                        1);
//
//
//
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if(response.code() == 200){
//
//                    Log.v("Tag", "the response"+ response.body().toString());
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//
//                    for (MovieModel movieModel:movies) {
//                        Log.v("Tag", movieModel.getTitle());
//                    }
//                }
//                else
//                {
//                    try{
//                        Log.v("Tag", "Error"+response.errorBody().string());
//                    }
//                    catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//
//            }
//        });
//    }
//
//    private void GetRetrofitResponseAccordingToID(){
//
//        MovieApi movieApi = Service.getMovieApi();
//        Call<MovieModel> responseCall = movieApi.getMovie(550, Credentials.API_KEY);
//
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if(response.code() == 200){
//                    MovieModel movie = response.body();
//                    Log.v("Tag", "The response "+ movie.getTitle());
//                }
//                else{
//                    try {
//                        Log.v("Tag", "Error"+ response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//    }
//}