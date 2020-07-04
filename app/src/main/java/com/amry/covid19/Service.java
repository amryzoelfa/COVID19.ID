package com.amry.covid19;

import com.amry.covid19.Model.ModelDataIndonesia;
import com.amry.covid19.Model.ModelObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("indonesia")
    Call<List<ModelDataIndonesia>> getData();

    @GET("indonesia/provinsi")
    Call<List<ModelObject>> getProvinsi();
}
