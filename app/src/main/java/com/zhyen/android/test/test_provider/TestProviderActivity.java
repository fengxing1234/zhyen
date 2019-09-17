package com.zhyen.android.test.test_provider;


import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zhyen.android.R;

import static android.widget.Toast.LENGTH_LONG;

public class TestProviderActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "TestProviderActivity";
    private EditText etTitle;
    private EditText etAuthor;
    private ContentResolver mContentResolver;

    private String[] mProjection = {
            TestPictureContract.IPicture.ID,
            TestPictureContract.IPicture.TITLE,
            TestPictureContract.IPicture.AUTHOR};
    private TestProviderAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_provider);
        Button btnInsert = findViewById(R.id.test_btn_insert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doInsert();
            }
        });

        etTitle = findViewById(R.id.test_et_title);
        etAuthor = findViewById(R.id.test_et_author);
        mContentResolver = getContentResolver();

        RecyclerView recyclerView = findViewById(R.id.test_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter = new TestProviderAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TestProviderActivity.this);
                final String author = ((TextView) view.findViewById(R.id.test_tv_author)).getText().toString();
                final String title = ((TextView) view.findViewById(R.id.test_tv_title)).getText().toString();
                View myView = getLayoutInflater().inflate(R.layout.test_provider_dialog, null);
                final EditText mDialogAuthor = (EditText) myView.findViewById(R.id.test_et_author);
                mDialogAuthor.setText(author);
                final EditText mDialogTitle = (EditText) myView.findViewById(R.id.test_et_title);
                mDialogTitle.setText(title);
                builder.setTitle("Picture")
                        .setView(myView)
                        //delete
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int count = mContentResolver.delete(
                                        TestPictureContract.IPicture.CONTENT_URI,
                                        TestPictureContract.IPicture.TITLE + "= ?",
                                        new String[]{title}
                                );
                                if (count > 0) {
                                    Toast.makeText(TestProviderActivity.this, "Delete Successfully", LENGTH_LONG).show();
                                }
                            }
                        })
                        //update
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContentValues mUpdateValue = new ContentValues();
                                mUpdateValue.put(TestPictureContract.IPicture.TITLE, mDialogTitle.getText().toString());
                                mUpdateValue.put(TestPictureContract.IPicture.AUTHOR, mDialogAuthor.getText().toString());
                                int count = mContentResolver.update(
                                        TestPictureContract.IPicture.CONTENT_URI,
                                        mUpdateValue,
                                        TestPictureContract.IPicture.TITLE + "=?",
                                        new String[]{title}
                                );
                                if (count > 0) {
                                    Log.d(TAG, "Update, count = " + count);
                                    Toast.makeText(TestProviderActivity.this, "Update Successfully", LENGTH_LONG).show();
                                }
                            }
                        })
                        //nothing
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }
        }));
        getLoaderManager().initLoader(0, null, this);
    }

    private void doInsert() {
        String title = etTitle.getText().toString();
        String author = etAuthor.getText().toString();
        ContentValues mNewValues = new ContentValues();
        mNewValues.put(TestPictureContract.IPicture.TITLE, title);
        mNewValues.put(TestPictureContract.IPicture.AUTHOR, author);
        Log.d(TAG, "title = " + title + " ,author = " + author);
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(author)) {
            Uri mNewUri = mContentResolver.insert(TestPictureContract.IPicture.CONTENT_URI, mNewValues);
            if (mNewUri != null) {
                String id = mNewUri.getPathSegments().get(1);
                long id1 = ContentUris.parseId(mNewUri);
                Log.d(TAG, "Insert , id = " + id + " ,id1 = " + String.valueOf(id1));
                etTitle.setText(null);
                etAuthor.setText(null);
            }
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = new CursorLoader(getApplicationContext(),
                TestPictureContract.IPicture.CONTENT_URI,
                mProjection,
                null,
                null,
                TestPictureContract.IPicture.DEFAULT_SORT
        );
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
