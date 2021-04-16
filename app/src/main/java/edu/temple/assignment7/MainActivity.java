package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.service.controls.Control;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import edu.temple.audiobookplayer.AudiobookService;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface{

    FragmentManager fm;

    BookList bList;
    Book selectedBook;
    BookDetailsFragment bdf;
    ControlFragment controlFragment;
    boolean container2present;
    int bookIndex;

    AudiobookService.MediaControlBinder mediaControlBinder;
    boolean connected = true;
    int duration;
    int progress;
    Uri bookUri;
    SeekBar seekBar;
    boolean playWasClicked;

    Intent intent;
    private static final String ARG_BOOKLIST = "books";
    private static final String SEEKBAR_PROGRESS = "sbProgress";
    private static final String DURATION = "bookDuration";

    ServiceConnection bookServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mediaControlBinder = (AudiobookService.MediaControlBinder) service;
            mediaControlBinder.setProgressHandler(mediaControlHandler);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };

    Handler mediaControlHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            final AudiobookService.BookProgress bookProgress = (AudiobookService.BookProgress) msg.obj;
            seekBar = findViewById(R.id.seekBar);
            seekBar.setMax(duration);
            if(mediaControlBinder.isPlaying()){
                seekBar.setProgress(bookProgress.getProgress());
                progress = selectedBook.getDuration();
                bookUri = bookProgress.getBookUri();

            }
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser){
                        mediaControlBinder.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            selectedBook = savedInstanceState.getParcelable(ARG_BOOKLIST);
            duration = savedInstanceState.getInt(DURATION);
            progress = savedInstanceState.getInt(SEEKBAR_PROGRESS);
        }

        container2present = findViewById(R.id.container_2) != null;
        seekBar = findViewById(R.id.seekBar);
        bList = new BookList();

        Fragment fraged;
        fm = getSupportFragmentManager();
        fraged = fm.findFragmentById(R.id.container_1);
        if(fraged instanceof BookDetailsFragment){
            fm.popBackStack();
        }
        else if(!(fraged instanceof BookListFragment)){
            fm.beginTransaction()
                    .add(R.id.container_1, BookListFragment.newInstance(bList))
                    .commit();
        }

        Button btnSearchMain = findViewById(R.id.btnSearchMain);
        btnSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(MainActivity.this, BookSearchActivity.class);
                startActivity(launchIntent);
            }
        });

        bdf = (selectedBook == null) ? new BookDetailsFragment() : BookDetailsFragment.newInstance(selectedBook);
        controlFragment = (selectedBook == null) ? new ControlFragment() : ControlFragment.newInstance(selectedBook);

        intent = getIntent();
        if(container2present){
            bdf = new BookDetailsFragment();
            controlFragment = new ControlFragment();

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