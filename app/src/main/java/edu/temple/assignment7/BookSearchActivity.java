package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BookSearchActivity extends AppCompatActivity {

    Button btnSearch;
    Button btnClear;
    EditText editTextUrl;


    Handler downloadHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        btnSearch = findViewById(R.id.btnSearch);
        //btnClear = findViewById(R.id.btnClear);
        //editTextUrl = findViewById(R.id.editTextUrl);

        /*btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            URL url = new URL(editTextUrl.getText().toString());
                            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                            Message msg = Message.obtain();
                            StringBuilder sb = new StringBuilder();
                            String str;

                            while ((str = reader.readLine()) != null) {
                                sb.append(str);
                            }

                            msg.obj = reader.readLine();
                            downloadHandler.sendMessage(msg);

                        } catch (Exception e ) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });*/

        final EditText prompt = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("My fancy title");
        builder.setView(prompt);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String myText = prompt.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog promptDialog = builder.create();
        promptDialog.show();
    }
    /*private String sanitizeURL(String url) {
        if (url.startsWith("http")) {
            return url;
        }else {
            editTextUrl.setText("https://" + url);
            return "https://" + url;
        }
    }*/
}