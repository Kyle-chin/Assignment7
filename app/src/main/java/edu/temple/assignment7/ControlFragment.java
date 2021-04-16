package edu.temple.assignment7;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import edu.temple.audiobookplayer.AudiobookService;

import static android.content.Context.BIND_AUTO_CREATE;

public class ControlFragment extends Fragment {

    private static final String ARG_p1 = "param1";

    private Book book;
    TextView nowPlaying;
    Button btnPlay, btnPause, btnStop;
    SeekBar seekBar;

    ControlFragmentInterface parentActivity;
    interface ControlFragmentInterface{
        void playBook(int i);
        void pauseBook(int i);
        void stopBook(int i);
    }

    public ControlFragment() {
    }
    public static ControlFragment newInstance(Book book){
        ControlFragment fragment = new ControlFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_p1, book);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            book = getArguments().getParcelable(ARG_p1);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_control, container, false);

        btnPlay = v.findViewById(R.id.btnPlay);
        btnPause = v.findViewById(R.id.btnPause);
        btnStop = v.findViewById(R.id.btnStop);
        nowPlaying = v.findViewById(R.id.txtNowPlaying);
        seekBar = v.findViewById(R.id.seekBar);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.playBook(book.getID());
                if(book != null){
                    changeBook(book);
                }
            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.pauseBook(book.getID());
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.stopBook(book.getID());
            }
        });
        return v;
    }

    void changeBook(Book book){
        nowPlaying.setText("Now Playing: " + book.getTitle());
    }
    public void updatePlayStatus(BookDetailsFragment detailsFragment, boolean playing){
        nowPlaying.setText("Now Playing: " + book.getTitle());
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof BookListFragment.BookListFragmentInterface){
            parentActivity = (ControlFragment.ControlFragmentInterface) context;
        }
        else{
            throw new RuntimeException("Please implement the required interface");
        }
    }
}