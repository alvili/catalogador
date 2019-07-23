package com.abcsoft.catalogador.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.modelo.Book;

import java.util.List;

public class BooksAdapter extends BaseAdapter {

    private List<Book> books;
    private LayoutInflater inflater;

    public CountriesAdapters(Context context, List <Book> books){
        this.books = books;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.row_book, null);


        return view;
    }
}
