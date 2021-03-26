package edu.temple.assignment7;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends ArrayAdapter {

    Context context;

    public BookAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textViewTit;
        TextView textviewAuth;
        LinearLayout linlay;

        String tit = ((Book)(getItem(position))).getTitle();
        String aut = ((Book)(getItem(position))).getAuthor();
        if(convertView == null){

            linlay = new LinearLayout(context);

            textViewTit = new TextView(context);
            textViewTit.setTextSize(22);
            textViewTit.setPadding(15,20,0,20);

            textviewAuth = new TextView(context);
            textviewAuth.setTextSize(19);
            textviewAuth.setPadding(15,20,0,20);

            linlay.setOrientation(LinearLayout.VERTICAL);
            linlay.addView(textViewTit);
            linlay.addView(textviewAuth);
        }
        else{
            linlay = (LinearLayout) convertView;
            textViewTit = (TextView) linlay.getChildAt(0);
            textviewAuth = (TextView) linlay.getChildAt(1);
        }

        textViewTit.setText(tit);
        textviewAuth.setText(aut);
        return linlay;
    }
}
