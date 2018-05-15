package com.example.nobre.n_track;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.ContextMenu;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nobre.n_track.DAO.MotoDAO;
import com.example.nobre.n_track.modelo.Moto;

import java.util.List;

public class ListaMoto extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listaMotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_moto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaMotos = (ListView) findViewById(R.id.lista_motos);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaMoto.this, FormularioCadastroMoto.class);
                startActivity(intent);
            }
        });

        listaMotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Moto moto = (Moto) listaMotos.getItemAtPosition(position);
                Intent intentDetalhemoto = new Intent(ListaMoto.this, DetalheMoto.class);
                intentDetalhemoto.putExtra("moto", moto);
                startActivity(intentDetalhemoto);
            }
        });

        /*
                Editar pode ser nesse método ou no onCreateContextMenu
        listaMotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Moto moto = (Moto) listaMotos.getItemAtPosition(position);
                Intent intent = new Intent(ListaMoto.this, FormularioCadastroMoto.class);
                intent.putExtra("moto", moto);
                startActivity(intent);
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        registerForContextMenu(listaMotos);
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
            Intent vaiParaOForumlarioCadastroMoto = new Intent(ListaMoto.this, FormularioCadastroMoto.class);
            startActivity(vaiParaOForumlarioCadastroMoto);
        } else if (id == R.id.lista_moto) {
            // Já está aqui
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

    private void carregaListaMotos() {
        MotoDAO dao = new MotoDAO(this);
        List<Moto> motos = dao.buscaMotos();
        dao.close();
        ArrayAdapter<Moto> adapter = new ArrayAdapter<Moto>(this, android.R.layout.simple_list_item_1, motos);
        listaMotos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaListaMotos();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        MenuItem editar = menu.add("Editar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Moto moto = (Moto) listaMotos.getItemAtPosition(info.position);
                MotoDAO dao = new MotoDAO(ListaMoto.this);
                dao.deleta(moto);
                dao.close();
                Toast.makeText(ListaMoto.this, "Moto " + moto.getMarca() + " " +
                        moto.getModelo() + " excluida ", Toast.LENGTH_SHORT).show();
                carregaListaMotos();
                return false;
            }
        });

        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Moto moto = (Moto) listaMotos.getItemAtPosition(info.position);
                Intent intent = new Intent(ListaMoto.this, FormularioCadastroMoto.class);
                intent.putExtra("moto", moto);
                startActivity(intent);
                return false;
            }
        });
    }
}
