package edu.temple.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface{

    BookList bl;
    BookDetailsFragment bdf;
    boolean container2present;
    int bookPosSelected;
    Parcelable parcy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container2present = findViewById(R.id.container_2) != null;
        bl = new BookList();
        bl.AddBook(new Book("Mieko Kawakami", "Breasts and Eggs"));
        bl.AddBook(new Book("Aoko Matsuda", "Where the Wild Ladies Are"));
        bl.AddBook(new Book("James McBride", "Deacon King Kong"));
        bl.AddBook(new Book("Megha Majumdar", "A Burning"));
        bl.AddBook(new Book("Laura van den Berg", "I Hold a Wolf by the Ears"));
        bl.AddBook(new Book("Ayad Akhtar", "Homeland Elegies"));
        bl.AddBook(new Book("Lydia Millet", "A Children's Bible"));
        bl.AddBook(new Book("Hilary Mantel", "The Mirror & the Light"));
        bl.AddBook(new Book("Douglas Stuart", "Shuggie Bain"));
        bl.AddBook(new Book("Brit Bennett", "The Vanishing Half"));

        if(!container2present){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_1, BookListFragment.newInstance(bl))
                    .commit();
        }
        else if(container2present){
            bdf = new BookDetailsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_2, bdf)
                    .commit();
        }


        //==============================
        // used for trying to save from orientation
        //==============================
        if(savedInstanceState != null){
            if(!container2present) {
                BookDetailsFragment bookloader = BookDetailsFragment.newInstance(bl.get(bookPosSelected));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_1, bookloader)
                        .commit();
            }
            else{
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_1, BookListFragment.newInstance(bl))
                        .commit();
                BookDetailsFragment bookloader = BookDetailsFragment.newInstance(bl.get(bookPosSelected));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_2, bookloader)
                        .commit();
            }
        }
    }

    @Override
    public void bookClicked(int position) {
        if(!container2present) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_1, BookDetailsFragment.newInstance(bl.get(position)))
                    .addToBackStack(null)
                    .commit();
        }
        else{
            bdf.changeBook(bl.get(position));
            bookPosSelected = position;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putInt("trial", bookPosSelected);
        super.onSaveInstanceState(outState);
    }
}