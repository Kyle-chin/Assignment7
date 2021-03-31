package edu.temple.assignment7;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String author, title, coverURL;
    private int ID;

    Book(String auth, String titl, String coverurl, int id){
        author = auth;
        title = titl;
        coverURL = coverurl;
        ID = id;
    }

    protected Book(Parcel in) {
        author = in.readString();
        title = in.readString();
        coverURL = in.readString();
        ID = in.readInt();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
    }
}
