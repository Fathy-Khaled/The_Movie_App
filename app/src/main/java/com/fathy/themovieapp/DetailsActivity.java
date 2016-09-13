package com.fathy.themovieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.omar.themovieapp.R;

/**
 * Created by fathy
 */
public class DetailsActivity extends AppCompatActivity {
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        MyMovie movie = (MyMovie) bundle.getSerializable("movie");
        manager = getSupportFragmentManager();
        MovieDetailsFragment movieDetailsFragment = (MovieDetailsFragment) manager.findFragmentById(R.id.detailsFragment);
        movieDetailsFragment.change(movie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
