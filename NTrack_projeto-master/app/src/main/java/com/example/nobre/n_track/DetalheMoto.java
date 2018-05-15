package com.example.nobre.n_track;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nobre.n_track.modelo.Moto;

public class DetalheMoto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_moto);

        Intent intent = getIntent();
        final Moto moto = (Moto) intent.getSerializableExtra("moto");

        TextView marca = (TextView) findViewById(R.id.txtMarcaDetalheMoto);
        TextView modelo = (TextView) findViewById(R.id.txtModeloDetalheMoto);
        TextView ano = (TextView) findViewById(R.id.txtAnoDetalheMoto);
        TextView cilindrada = (TextView) findViewById(R.id.txtCilindradaDetalheMoto);

        marca.setText(moto.getMarca());
        modelo.setText(moto.getModelo());
        ano.setText(String.valueOf(moto.getAno()));
        cilindrada.setText(String.valueOf(moto.getCilndrada()));

        FloatingActionButton botaoCompartilha = (FloatingActionButton) findViewById(R.id.btnCompartilharDetalhes);
        botaoCompartilha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_TEXT, "Dados da Motocicleta:       " +
                        " Modelo: " + moto.getModelo() + ", Marca: " + moto.getMarca()+ " Cilindrada: " +
                                moto.getCilndrada()+ ", Ano: " + moto.getAno() + ".")
                        .setType("text/plain");
                dispararIntent(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalhe_moto, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_retorna_principal:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dispararIntent(Intent intent){
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            Toast.makeText(this, "Erro", Toast.LENGTH_SHORT).show();
        }
    }
}
