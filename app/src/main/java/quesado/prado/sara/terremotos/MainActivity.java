package quesado.prado.sara.terremotos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import quesado.prado.sara.terremotos.adapters.TerremotosAdapter;
import quesado.prado.sara.terremotos.model.Terremoto;
import quesado.prado.sara.terremotos.utils.QueryUtils;

public class MainActivity extends AppCompatActivity {
    private static final String SAMPLE_JSON_RESPONSE =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-12-31&minmagnitude=6";
    TerremotosAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //terremotos=QueryUtils.extraerTerremotos(SAMPLE_JSON_RESPONSE);

        GetTerremotosAsynTask asynTask=new GetTerremotosAsynTask();
        asynTask.execute();


    }
    private void updateUi(ArrayList<Terremoto> terremotos) {
        ListView terremotosLisview=findViewById(R.id.lista);
        adapter=new TerremotosAdapter(getApplicationContext(),terremotos);
        terremotosLisview.setAdapter(adapter);
        terremotosLisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Terremoto terremoto=terremotos.get(position);
                String url=terremoto.getUrl();
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
    }
    class GetTerremotosAsynTask extends AsyncTask<URL,Void,ArrayList<Terremoto>>{

        @Override
        protected ArrayList<Terremoto> doInBackground(URL... urls) {
            URL url = createUrl(SAMPLE_JSON_RESPONSE);
            String jsonResponse = "";
            try {
                jsonResponse = QueryUtils.makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
            }
            ArrayList<Terremoto> terremotos = null;
            if (jsonResponse!=null){
                terremotos=QueryUtils.extraerTerremotos(jsonResponse);
            }
            return terremotos;
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e("CREATEURL", "Error with creating URL", exception);
                return null;
            }
            return url;
        }
        @Override
        protected void onPostExecute(ArrayList<Terremoto> terremotos) {
            super.onPostExecute(terremotos);
            if (terremotos!=null){
                updateUi(terremotos);
            }
        }
    }


}