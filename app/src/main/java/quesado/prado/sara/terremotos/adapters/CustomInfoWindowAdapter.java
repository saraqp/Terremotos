package quesado.prado.sara.terremotos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import quesado.prado.sara.terremotos.R;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private View view;
    private Context context;

    public CustomInfoWindowAdapter(Context context) {
        this.view= LayoutInflater.from(context).inflate(R.layout.custom_info_windows,null);
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView titulo=view.findViewById(R.id.titulo);
        titulo.setText(marker.getTitle());

        TextView descripcion=view.findViewById(R.id.descipcion);
        descripcion.setText(marker.getSnippet());

        return view;
    }
}
