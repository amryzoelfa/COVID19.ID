package com.amry.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amry.covid19.Model.ModelDataIndonesia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tSembuh,tPositif,tMeninggal;
    RecyclerView list;
    ProgressDialog dialog;
    private Button btnProv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tSembuh = findViewById(R.id.tSembuh);
        tPositif = findViewById(R.id.tPositif);
        tMeninggal = findViewById(R.id.tMeninggal);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        getSupportActionBar().setTitle("COVID19.ID");

        initControl();
        getData();
        //getProvinsi();
    }

    private void initControl() {
        btnProv = (Button) findViewById(R.id.btnProv);
        btnProv.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_oke,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh:
                getData();
                //getProvinsi();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        Call<List<ModelDataIndonesia>> call = Api.service().getData();
        call.enqueue(new Callback<List<ModelDataIndonesia>>() {
            @Override
            public void onResponse(Call<List<ModelDataIndonesia>> call, Response<List<ModelDataIndonesia>> response) {
                tSembuh.setText(response.body().get(0).getSembuh());
                tPositif.setText(response.body().get(0).getPositif());
                tMeninggal.setText(response.body().get(0).getMeninggal());
            }

            @Override
            public void onFailure(Call<List<ModelDataIndonesia>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProv:
                Intent provinsi = new Intent(MainActivity.this, ProvinsiActivity.class);
                startActivity(provinsi);
                break;
        }
    }

//    public void getProvinsi(){
//        dialog.show();
//        Call<List<ModelObject>> call = Api.service().getProvinsi();
//        call.enqueue(new Callback<List<ModelObject>>() {
//            @Override
//            public void onResponse(Call<List<ModelObject>> call, Response<List<ModelObject>> response) {
//                list.setAdapter(new ProvinsiAdapter(MainActivity.this,response.body()));
//                dialog.cancel();
//            }
//
//            @Override
//            public void onFailure(Call<List<ModelObject>> call, Throwable t) {
//                Toast.makeText(MainActivity.this,t.getMessage() , Toast.LENGTH_SHORT).show();
//                dialog.cancel();
//            }
//        });
//    }
}