package com.example.nobre.n_track;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nobre.n_track.DAO.MotoDAO;
import com.example.nobre.n_track.modelo.Moto;

import java.lang.reflect.Array;

public class FormularioCadastroMoto extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CadastroMotoHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cadastro_moto);

        helper = new CadastroMotoHelper(this);
        Spinner spinner = (Spinner)findViewById(R.id.spnMarcaMoto);
        String[] marcas = {"Marca", "Suzuki", "Yamaha", "Honda", "Kawazaki"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, marcas);
        spinner.setAdapter(adapter);

        Intent intent = getIntent(); // pega os dados que vem da lista
        Moto moto = (Moto)intent.getSerializableExtra("moto");
        if(moto != null){
            helper.preencheFormulario(moto);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnSalvar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FormularioCadastroMoto.this, "Moto salva!", Toast.LENGTH_SHORT).show();
                Moto moto = helper.pegaMoto();
                MotoDAO dao = new MotoDAO(FormularioCadastroMoto.this);
                if(moto.getId() != null){
                    dao.altera(moto);
                }else {
                    dao.insert(moto);
                }
                dao.close();
                Intent intent = new Intent(FormularioCadastroMoto.this, ListaMoto.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cadastrar_moto_menu) {
            // Já esta aqui
        } else if(id == R.id.lista_moto) {
            Intent intent = new Intent(this, ListaMoto.class);
            startActivity(intent);
        } else if (id == R.id.treinos_menu) {
            Toast.makeText(this, "Clicou no Treino", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.cronometragem_menu) {
            Toast.makeText(this, "Clicou no Cronometragem", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.configuracoes_menu) {
            Toast.makeText(this, "Clicou no Configurações", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.sair_menu) {
            finish();
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Clicou no Compartilhar", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Clicou no Enviar", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
