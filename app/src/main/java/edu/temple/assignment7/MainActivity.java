package edu.temple.assignment7;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface{

    FragmentManager fm;

    boolean container2present;
    BookDetailsFragment bdf;
    Book selectedBook;
    Button btnSearch;
    private final String KEY_SELECTED_BOOK = "selectedBook";
    BookList bList;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState != null)
            selectedBook = savedInstanceState.getParcelable(KEY_SELECTED_BOOK);

        container2present = findViewById(R.id.container_2) != null;

        bList = new BookList();
        fm = getSupportFragmentManager();


        Fragment fragment1;
        fragment1 = fm.findFragmentById(R.id.container_1);
        if(fragment1 instanceof BookDetailsFragment){
            fm.popBackStack();
        }
        else if(!(fragment1 instanceof BookListFragment)){
            fm.beginTransaction()
                    .add(R.id.container_1, BookListFragment.newInstance(bList))
                    .commit();
        }
        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(MainActivity.this, BookSearchActivity.class);
                startActivity(launchIntent);
            }
        });
        bdf = (selectedBook == null) ? new BookDetailsFragment() : BookDetailsFragment.newInstance(selectedBook);

        intent = getIntent();
        if(container2present){

            if(intent.hasExtra("bookslisted")){
                Bundle extras = getIntent().getExtras();
                bList = extras.getParcelable("bookslisted");
            }
            bdf = new BookDetailsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_2, bdf)
                    .commit();
        }
        else {//if(selectedBook != null){
            if(intent.hasExtra("bookslisted")){
                Bundle extras = getIntent().getExtras();
                bList = extras.getParcelable("bookslisted");
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_1, BookListFragment.newInstance(bList))
                    .commit();
        }

    }

    @Override
    public void bookClicked(int position) {
        selectedBook = bList.get(position);

        if(container2present) {
            bdf.changeBook(selectedBook);
        }
        else{
            fm.beginTransaction()
                    .replace(R.id.container_1, BookDetailsFragment.newInstance(selectedBook))
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SELECTED_BOOK, selectedBook);
    }

    @Override
    public void onBackPressed() {
        // If the user hits the back button, clear the selected book
        selectedBook = null;
        super.onBackPressed();
    }
}