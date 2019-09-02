package com.abcsoft.catalogador.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.abcsoft.catalogador.R;

public class ScannerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        Button scan = findViewById(R.id.id_BtnScanCode);
        final TextView code = (TextView) findViewById(R.id.idTextCode);

//        code.setText("9788408085614");
        code.setText("9781101965481");
//        code.setText("8439596065");

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScannerActivity.this, BookDetailsActivity.class);
                intent.putExtra("isbn", String.valueOf(code.getText()));
                startActivity(intent);
            }
        });

    }
}
