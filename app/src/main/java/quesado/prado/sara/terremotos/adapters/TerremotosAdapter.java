package quesado.prado.sara.terremotos.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import quesado.prado.sara.terremotos.R;
import quesado.prado.sara.terremotos.model.Terremoto;
import quesado.prado.sara.terremotos.utils.QueryUtils;

public class TerremotosAdapter extends ArrayAdapter<Terremoto> {
    List<Terremoto> terremotos;
    Context context;
    final static String SEPARADOR_LOCALIZACION=" of ";
    public TerremotosAdapter(@NonNull Context context,@NonNull List<Terremoto> terremotos) {
        super(context,0,terremotos);
        this.terremotos=terremotos;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView=convertView;

        if (listView==null){
            listView= LayoutInflater.from(getContext()).inflate(R.layout.item_list,parent,false);
        }
        Terremoto terremoto=terremotos.get(position);

        TextView magnitud=listView.findViewById(R.id.magnitud);
        String magnitudFormateada=formatearMagnitud(terremoto.getMagnitud());
        magnitud.setText(magnitudFormateada);

        GradientDrawable magnitudeCircle= (GradientDrawable) magnitud.getBackground();
        int magnitudeColor=getMagnitudeColor(terremoto.getMagnitud());
        magnitudeCircle.setColor(magnitudeColor);


        TextView localizacionTerremoto=listView.findViewById(R.id.localizacionInicial);
        TextView cercaniaTerremoto=listView.findViewById(R.id.cercania);


        String localizacion=terremoto.getLocalizacion();
        String cercania,lugar;
        if (localizacion.contains(SEPARADOR_LOCALIZACION)){
            String[] localizacion_split=localizacion.split(SEPARADOR_LOCALIZACION);
            cercania=localizacion_split[0]+SEPARADOR_LOCALIZACION;
            lugar=localizacion_split[1];
            cercaniaTerremoto.setText(cercania);
            localizacionTerremoto.setText(lugar);
        }else {
            cercaniaTerremoto.setText("NEAR"+SEPARADOR_LOCALIZACION);
            localizacionTerremoto.setText(localizacion);
        }





        TextView fecha=listView.findViewById(R.id.fecha);
        String fechaformateada=formatearFecha(terremoto.getFechaenMilisegundos());
        fecha.setText(fechaformateada);
        TextView horaTerremoto=listView.findViewById(R.id.time);
        String horaterremotoformateada=formatearHora(terremoto.getFechaenMilisegundos());
        horaTerremoto.setText(horaterremotoformateada);

        return listView;

    }

    private int getMagnitudeColor(double magnitud) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitud);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    private String formatearFecha(long fechaenMilisegundos) {
        SimpleDateFormat dateFormat=new SimpleDateFormat("MMM DD,yyyy", Locale.getDefault());
        return dateFormat.format(fechaenMilisegundos);
    }
    private String formatearHora(long fechaenMilisegundos){
        SimpleDateFormat timeformat=new SimpleDateFormat("h:mm a");
        return timeformat.format(fechaenMilisegundos);
    }
    private String formatearMagnitud(double magnitud) {
        DecimalFormat format=new DecimalFormat("0.0");
        return format.format(magnitud);
    }
}
