package edu.temple.assignment7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookDetailsFragment extends Fragment {

    private static final String ARG_BOOK = "book";
    TextView Titletext;
    TextView Authortext;
    ImageView imageView;
    private Book book;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View l = inflater.inflate(R.layout.fragment_book_details, container, false);

        Titletext = l.findViewById(R.id.TitleTV);
        Authortext = l.findViewById(R.id.AuthorTV);
        imageView = l.findViewById(R.id.imageViewid);

        if(book != null)
            changeBook(book);

        return l;
    }

    public void changeBook(Book book){
        Titletext.setText(book.getTitle());
        Authortext.setText(book.getAuthor());
        Picasso.get().load(book.getCoverURL()).into(imageView);
    }

}