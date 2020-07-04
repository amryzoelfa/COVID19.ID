package com.amry.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.amry.covid19.Adapter.ProvinsiAdapter;
import com.amry.covid19.Model.ModelObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProvinsiActivity extends AppCompatActivity {

    TextView tSembuh,tPositif,tMeninggal;
    RecyclerView list;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provinsi);

        tSembuh = findViewById(R.id.tSembuh);
        tPositif = findViewById(R.id.tPositif);
        tMeninggal = findViewById(R.id.tMeninggal);

        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        getSupportActionBar().setTitle("COVID19.ID");

        getProvinsi();
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
                getProvinsi();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getProvinsi() {
        dialog.show();
        Call<List<ModelObject>> call = Api.service().getProvinsi();
        call.enqueue(new Callback<List<ModelObject>>() {
            @Override
            public void onResponse(Call<List<ModelObject>> call, Response<List<ModelObject>> response) {
                list.setAdapter(new ProvinsiAdapter(ProvinsiActivity.this,response.body()));
                dialog.cancel();
            }

            @Override
            public void onFailure(Call<List<ModelObject>> call, Throwable t) {
                Toast.makeText(ProvinsiActivity.this,t.getMessage() , Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
    }
}