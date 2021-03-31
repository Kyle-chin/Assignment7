package edu.temple.assignment7;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BookList implements Parcelable{
    
    private ArrayList<Book> bList;
    
    public BookList(){
        bList = new ArrayList<>();
    }
    protected BookList(Parcel in){
        bList = in.createTypedArrayList(Book.CREATOR);
    }
    public static final Parcelable.Creator<BookList> CREATOR = new Parcelable.Creator<BookList>() {
        @Override
        public BookList createFromParcel(Parcel in) {
            return new BookList(in);
        }
        @Override
        public BookList[] newArray(int size){
            return new BookList[size];
        }
    };
    public void RemoveBook(Book book){
        bList.remove(book);
    }
    public void AddBook(Book book){
        bList.add(book);
    }
    public Book get(int bookPos){
        return bList.get(bookPos);
    }
    public int size(){
        return bList.size();
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(bList);
    }
}
