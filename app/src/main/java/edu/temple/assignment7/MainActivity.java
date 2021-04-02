package edu.temple.assignment7;

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
    BookList bList = new BookList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null)
            selectedBook = savedInstanceState.getParcelable(KEY_SELECTED_BOOK);

        container2present = findViewById(R.id.container_2) != null;

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

        bdf = (selectedBook == null) ? new BookDetailsFragment() : BookDetailsFragment.newInstance(selectedBook);
        Intent intent = getIntent();
        if(container2present){
            if(intent.hasExtra("bookslisted")){
                Bundle extras = getIntent().getExtras();
                bList = extras.getParcelable("bookslisted");
            }
            fm.beginTransaction()
                    .replace(R.id.container_2, bdf)
                    .commit();
        }
        else if(selectedBook != null){
            if(intent.hasExtra("bookslisted")){
                Bundle extras = getIntent().getExtras();
                bList = extras.getParcelable("bookslisted");
            }
            fm.beginTransaction()
                    .replace(R.id.container_1, bdf)
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
    }

    private BookList getTestBooks(){
        BookList bl= new BookList();
        /*bl.AddBook(new Book("Mieko Kawakami", "Breasts and Eggs"));
        bl.AddBook(new Book("Aoko Matsuda", "Where the Wild Ladies Are"));
        bl.AddBook(new Book("James McBride", "Deacon King Kong"));
        bl.AddBook(new Book("Megha Majumdar", "A Burning"));
        bl.AddBook(new Book("Laura van den Berg", "I Hold a Wolf by the Ears"));
        bl.AddBook(new Book("Ayad Akhtar", "Homeland Elegies"));
        bl.AddBook(new Book("Lydia Millet", "A Children's Bible"));
        bl.AddBook(new Book("Hilary Mantel", "The Mirror & the Light"));
        bl.AddBook(new Book("Douglas Stuart", "Shuggie Bain"));
        bl.AddBook(new Book("Brit Bennett", "The Vanishing Half"));*/
        return bl;
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