package edu.temple.assignment7;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class BookListFragment extends Fragment {

    private static final String ARG_BOOKLIST = "booklist";

    private BookList bl;

    BookListFragmentInterface parentActivity;
    public BookListFragment() {}

    public static BookListFragment newInstance(BookList bl) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOKLIST, bl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof BookListFragmentInterface) {
            parentActivity = (BookListFragmentInterface) context;
        } else {
            throw new RuntimeException("Please implement the required interface(s)");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bl = getArguments().getParcelable(ARG_BOOKLIST);
        }
        else{
            bl = new BookList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_book_list, container, false);

        listView.setAdapter(new BookAdapter(getContext(), bl));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentActivity.bookClicked(position);
            }
        });
        return listView;
    }

    interface BookListFragmentInterface{
        public void bookClicked(int position);
    }

}