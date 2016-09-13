package com.fathy.themovieapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.omar.themovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener {

    GridView moviesGrid;
    List<MyMovie> movies;

    FetchMovie fetchMovie;
    Communicator communicator;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        communicator = (Communicator) getActivity();
        setHasOptionsMenu(true);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            getMovies("popular");

            beginWithTheFirstFilm();
        } else if (item.getItemId() == R.id.top_rated) {
            getMovies("top_rated");
            beginWithTheFirstFilm();
        } else if (item.getItemId() == R.id.favourite) {
            getFavourites();
        }

        return true;
    }

    public void getFavourites() {
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        movies = db.getmovie();
        MyAdapter adapter = new MyAdapter(getActivity(), movies);
        moviesGrid.setAdapter(adapter);
        MainActivity mainActivity = (MainActivity) communicator;
        MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment) mainActivity.manager.findFragmentById(R.id.detailsFragment);
        if(movieDetailsFragment != null && movieDetailsFragment.isVisible()) {
            if (movies != null && movies.size() > 0) {
                beginWithTheFirstFilm();
            } else {
                communicator.hideFragment();
            }
        }
    }


    private void getMovies(String type) {
        fetchMovie = new FetchMovie(getActivity(), movies);
        movies = null;
        try {
            movies = fetchMovie.execute(type).get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        MyAdapter adapter = new MyAdapter(getActivity(), movies);
        moviesGrid.setAdapter(adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        moviesGrid = (GridView) root.findViewById(R.id.grid);
        moviesGrid.setOnItemClickListener(this);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String type = "popular";

            getMovies(type);

    }

    @Override
    public void onStart() {
        super.onStart();
        beginWithTheFirstFilm();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        FetchMovie fetchMovie = new FetchMovie(getActivity(), movies);
        try {
            movies = fetchMovie.execute(movies.get(position).id + "", position + "").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        communicator.response(movies.get(position));
    }

    public void beginWithTheFirstFilm() {
        communicator.showFragment();
        FetchMovie fetchMovie = new FetchMovie(getActivity(), movies);

        try {
            movies = fetchMovie.execute(movies.get(0).id + "", 0 + "").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        communicator.sendOnfirst(movies.get(0));
    }
}


