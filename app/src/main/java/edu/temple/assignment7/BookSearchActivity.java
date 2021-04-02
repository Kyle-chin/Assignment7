package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BookSearchActivity extends AppCompatActivity {

    Button btnSearch;
    Button btnCancel;
    EditText editSearch;
    BookList blist = new BookList();
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        editSearch = findViewById(R.id.editSearchText);
        btnSearch = findViewById(R.id.btnSearchInside);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*final EditText prompt = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find A Book");
        builder.setView(prompt);
        String searchText = prompt.getText().toString();
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
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
        */
        requestQueue = Volley.newRequestQueue(this);

        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String urlString = "https://kamorris.com/lab/cis3515/search.php?term=" + editSearch.getText().toString();

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlString, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response.length() > 0){
                            for(int i = 0; i < response.length(); i++){
                                try{
                                    JSONObject book_JSON;;
                                    book_JSON = response.getJSONObject(i);
                                    blist.AddBook(new Book(book_JSON.getString("title"),
                                            book_JSON.getString("author"),
                                            book_JSON.getInt("id"),
                                            book_JSON.getString("cover_url")));
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                        else{

                        }
                        Intent launchIntent = new Intent(BookSearchActivity.this, MainActivity.class);
                        launchIntent.putExtra("bookslisted", blist);

                        startActivity(launchIntent);
                    }
                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError err){

                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
        });
    }
}