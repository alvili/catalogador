package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.abcsoft.catalogador.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra("isbn", "9788408085614" );
        startActivity(intent);

    }
}
