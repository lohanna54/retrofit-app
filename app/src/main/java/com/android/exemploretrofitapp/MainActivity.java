package com.android.exemploretrofitapp;
import com.android.exemploretrofitapp.retrofit.RetrofitInitializer;
import com.android.exemploretrofitapp.model.Cep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText edtCEPinformado;
    TextView txtLogradouro, txtBairro, txtLocalidade, txtUF;
    Button btnPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPesquisar = (Button) findViewById(R.id.btnPesquisar);


        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtCEPinformado = (EditText) findViewById(R.id.edtCEPInformado);
                String cepinformado = edtCEPinformado.getText().toString();
                txtBairro = (TextView) findViewById(R.id.txtBairro);
                txtLocalidade = (TextView) findViewById(R.id.txtLocalidade);
                txtLogradouro = (TextView) findViewById(R.id.txtLogradouro);
                txtUF = (TextView) findViewById(R.id.txtUF);
                Call<Cep> call = new RetrofitInitializer().getCep().select(Integer.parseInt(cepinformado));

                call.enqueue(new Callback<Cep>() {
                    @Override
                    public void onResponse(Call<Cep> call, Response<Cep> response) {

                        Cep cep = response.body();
                        txtUF.setText(cep.getUf());
                        txtBairro.setText(cep.getBairro());
                        txtLogradouro.setText(cep.getLogradouro());
                        txtLocalidade.setText(cep.getLocalidade());
                        Log.i("Retrofit", response.body().getLogradouro());
                    }

                    @Override
                    public void onFailure(Call<Cep> call, Throwable t) {
                        txtLocalidade.setText(t.getMessage());

                    }
                });
            }
        });
    }
}