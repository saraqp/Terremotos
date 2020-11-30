package quesado.prado.sara.terremotos.model;

import android.app.Application;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import quesado.prado.sara.terremotos.R;
import quesado.prado.sara.terremotos.utils.QueryUtils;

public class TerremotodViewModel extends AndroidViewModel {
    private static MutableLiveData<List<Terremoto>> terremotos;
    private Application application;//el contexto
    private static String SAMPLE_JSON_RESPONSE ="https://earthquake.usgs.gov/fdsnws/event/1/query";
    private SharedPreferences sharedPrefs;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;
    public TerremotodViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(application);
        sharedPreferencesListener();
    }

    public LiveData<List<Terremoto>> obtenerTerremotos(){
        if (terremotos==null){
            terremotos=new MutableLiveData<>();
            cargarTerremotos();
        }
        return terremotos;
    }
    //el shared preferences llama  al viewmodel cada vez que cambia
    private void sharedPreferencesListener() {
        listener=new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                cargarTerremotos();
            }
        };
        sharedPrefs.registerOnSharedPreferenceChangeListener(listener);
    }

    private void cargarTerremotos() {
        String minMagnitude = sharedPrefs.getString(
                application.getString(R.string.settings_min_magnitude_key),
                application.getString(R.string.settings_min_magnitude_default));
        String orderby=sharedPrefs.getString(
                application.getString(R.string.settings_order_by_key),
                application.getString(R.string.settings_order_by_default));

        Uri baseUri = Uri.parse(SAMPLE_JSON_RESPONSE);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "20");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderby);

        SAMPLE_JSON_RESPONSE=uriBuilder.toString();


        RequestQueue requestQueue= Volley.newRequestQueue(application);
        StringRequest request=new StringRequest(Request.Method.GET, SAMPLE_JSON_RESPONSE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                List<Terremoto>terremotosList= QueryUtils.extraerTerremotos(response);
                terremotos.setValue(terremotosList);
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Volley", error.toString());
            }
        });
        requestQueue.add(request);
    }

}
