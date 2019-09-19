package com.abcsoft.catalogador.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.model.Book.Book;
import com.abcsoft.catalogador.services.Utilidades;

import java.util.List;

public class BooksAdapter extends BaseAdapter {

    private List<Book> books;
    private LayoutInflater inflater;

    public BooksAdapter(Context context, List <Book> books){
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

        //Referencio la vista
        TextView title = view.findViewById(R.id.idBookTitle);
        TextView scanDate = view.findViewById(R.id.idScanDate);

        //Construyo el contenido
        title.setText(books.get(position).getTitle());
//        scanDate.setText(Utilidades.getStringFromDate(books.get(position).getDateCreation()));
        return view;
    }
}
