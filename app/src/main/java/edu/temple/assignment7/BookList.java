package edu.temple.assignment7;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Stream;

public class BookList extends ArrayList<Parcelable> {
    
    private ArrayList<Book> bList;
    
    public BookList(){
        bList = new ArrayList<Book>();
    }
    public void RemoveBook(Book book){
        bList.remove(book);
    }
    public void AddBook(Book book){
        bList.add(book);
    }
    public Book get(int bookPos){

        Book bookToReturn = bList.get(bookPos);
        return bookToReturn;
    }
    public int size(){
        int size = bList.size();
        return size;
    }

    @NonNull
    @Override
    public Stream<Parcelable> stream() {
        return null;
    }
}
