package quesado.prado.sara.terremotos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import quesado.prado.sara.terremotos.adapters.TerremotosAdapter;
import quesado.prado.sara.terremotos.model.Terremoto;
import quesado.prado.sara.terremotos.utils.QueryUtils;

public class MainActivity extends AppCompatActivity {
    List<Terremoto> terremotos;
    TerremotosAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        terremotos=QueryUtils.extraerTerremotos();
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

}