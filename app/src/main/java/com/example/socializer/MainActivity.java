package com.example.socializer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.socializer.following.FollowingPreferenceActivity;
import com.example.socializer.provider.SocializerContract;
import com.example.socializer.provider.SocializerProvider;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {
    private static final String TAG = "MainActivity";

    //loader id
    private static final int LOADER_ID_POSTS = 0;

    //views
    RecyclerView mRecyclerView;
    LinearLayoutManager mLinearLayoutManager;
    SocializerAdapter mAdapter;

    //DATABASE vars
    static final String[] POSTS_PROJECTION = {
            SocializerContract.COLUMN_AUTHOR,
            SocializerContract.COLUMN_MESSAGE,
            SocializerContract.COLUMN_DATE,
            SocializerContract.COLUMN_AUTHOR_KEY,
    };
    static final int COL_NUM_AUTHOR = 0;
    static final int COL_NUM_MESSAGE = 1;
    static final int COL_NUM_DATE = 2;
    static final int COL_NUM_AUTHOR_KEY = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // rv
        mRecyclerView = findViewById(R.id.socializer_rv);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // divider
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation()
        );
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        // adapter
        mAdapter = new SocializerAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    // ==========================MENU========================


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menue,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_following_prefs)
        {
            Intent intent = new Intent(this, FollowingPreferenceActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String selection = SocializerContract.createdSelectionForCurrentFollowers(
                PreferenceManager.getDefaultSharedPreferences(this));
        Log.e(TAG, "onCreateLoader: Selection is"+selection );
        return new CursorLoader(
                this,
                SocializerProvider.SocializerPosts.CONTENT_URI,
                POSTS_PROJECTION,selection,
                null,
                SocializerContract.COLUMN_DATE + " DESC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}