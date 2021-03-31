package edu.temple.assignment7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends BaseAdapter {

    Context context;
    BookList blist;

    public BookAdapter(Context context, BookList blist) {
        this.context = context;
        this.blist = blist;
    }

    @Override
    public int getCount() {
        return blist.size();
    }

    @Override
    public Object getItem(int position) {
        return blist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textViewTit;
        TextView textviewAuth;

        if (!(convertView instanceof LinearLayout)) {
            /*
            Inflate a predefined layout file that includes 2 text views.
            We could do this in code, but this seems a little easier
             */
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_book_details, parent, false);
        }

        textViewTit = convertView.findViewById(R.id.TitleTV);
        textviewAuth = convertView.findViewById(R.id.AuthorTV);

        textViewTit.setText(((Book) getItem(position)).getTitle());
        textviewAuth.setText(((Book) getItem(position)).getAuthor());
        return convertView;
    }
}
