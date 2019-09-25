package com.abcsoft.catalogador.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.abcsoft.catalogador.R;
import com.abcsoft.catalogador.model.Local.Media;
import com.abcsoft.catalogador.services.Utilidades;

import java.util.List;

public class MediasAdapter extends BaseAdapter {

    private List<Media> medias;
    private LayoutInflater inflater;

    public MediasAdapter(Context context, List <Media> medias){
        this.medias = medias;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return medias.size();
    }

    @Override
    public Object getItem(int position) {
        return medias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.row_scan, null);

        //Referencio la vista
        TextView title = view.findViewById(R.id.idBookTitle);
        TextView scanDate = view.findViewById(R.id.idScanDate);

        //Construyo el contenido
        title.setText(medias.get(position).getTitle());
        scanDate.setText(Utilidades.getStringFromDate(medias.get(position).getDateModified()));
        return view;
    }
}
