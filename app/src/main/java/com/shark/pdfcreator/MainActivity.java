package com.shark.pdfcreator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.draekko.libharu.Pdf;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button makepdf = (Button) findViewById(R.id.button);
        makepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Working");
                pDialog.setIndeterminate(true);
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(false);
                pDialog.show();

                new AsyncMakePdf().execute();
            }
        });
    }

    class AsyncMakePdf extends AsyncTask<Void, Void, File> {


        @Override
        protected File doInBackground(Void... params) {
            Pdf pdf = new Pdf(MainActivity.this);
            return pdf.samplePdf(Environment.getExternalStorageDirectory().getAbsolutePath(), "sample.pdf");
        }

        @Override
        protected void onPostExecute(File p) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setType("application/pdf");
            intent.setData(Uri.fromFile(p));
            startActivity(intent);
            pDialog.cancel();
        }
    }
}
