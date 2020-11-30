package quesado.prado.sara.terremotos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quesado.prado.sara.terremotos.adapters.TerremotosAdapter;
import quesado.prado.sara.terremotos.model.Terremoto;
import quesado.prado.sara.terremotos.model.TerremotodViewModel;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetWork;
    ListView terremotosLisview;
    TerremotosAdapter adapter;

    TextView empty;
    ArrayList<Terremoto> terremotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        terremotosLisview=findViewById(R.id.lista);
        empty=findViewById(R.id.empty);
        connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetWork=connectivityManager.getActiveNetworkInfo();
        terremotosLisview.setEmptyView(empty);


        terremotos=new ArrayList<>();
        boolean isConnected= activeNetWork!=null &&activeNetWork.isConnected();
        updateUi(terremotos);
        if (isConnected){

            TerremotodViewModel viewModel=ViewModelProviders.of(this).get(TerremotodViewModel.class);
            viewModel.obtenerTerremotos().observe(this, new Observer<List<Terremoto>>() {
                @Override
                public void onChanged(List<Terremoto> terremotos) {

                    ProgressBar progressBar=findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.GONE);

                    empty.setText("No ha ocurrido ningún terremoto");
                    adapter.clear();
                    if (terremotos!=null){
                        adapter.addAll(terremotos);

                    }
                }
            });
        }else {
            progressBar=findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);
            empty.setText("No ha ocurrido ningún terremoto");

        }
        //terremotos=QueryUtils.extraerTerremotos(SAMPLE_JSON_RESPONSE);




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_map:
                Intent mapIntent=new Intent(this,MapsActivity2.class);
                mapIntent.putExtra("terremotos",terremotos);
                startActivity(mapIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateUi(ArrayList<Terremoto> terremotos) {
        adapter=new TerremotosAdapter(getApplicationContext(),terremotos);
        terremotosLisview.setAdapter(adapter);
        terremotosLisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Terremoto terremoto=terremotos.get(position);
                String url=terremoto.getUrl();
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                //para mandar un objeto por intent tienes que implementarle serializable
                intent.putExtra("terremoto",terremoto);
                startActivity(intent);
            }
        });
    }
    /*
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
*/

}