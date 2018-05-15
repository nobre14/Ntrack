package com.example.nobre.n_track.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nobre.n_track.modelo.Moto;

import java.util.ArrayList;
import java.util.List;

public class MotoDAO extends SQLiteOpenHelper{


    public MotoDAO(Context context) {
        super(context, "NTrack", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Moto(id INTEGER PRIMARY KEY, marca TEXT NOT NULL," +
                " modelo TEXT NOT NULL, ano INTEGER NOT NULL, cilindrada INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Moto;";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(Moto moto){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosMoto(moto);
        db.insert("Moto", null, dados);
    }

    private ContentValues pegaDadosMoto(Moto moto) {
        ContentValues dados = new ContentValues();
        dados.put("marca", moto.getMarca());
        dados.put("modelo", moto.getModelo());
        dados.put("cilindrada", moto.getCilndrada());
        dados.put("ano", moto.getAno());
        return dados;
    }

    public List<Moto> buscaMotos() {
        String sql = "SELECT * FROM Moto;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Moto> motos = new ArrayList<>();
        while (c.moveToNext()){
            Moto moto = new Moto();
            moto.setId(c.getLong(c.getColumnIndex("id")));
            moto.setMarca(c.getString(c.getColumnIndex("marca")));
            moto.setModelo(c.getString(c.getColumnIndex("modelo")));
            moto.setCilndrada(c.getInt(c.getColumnIndex("cilindrada")));
            moto.setAno(c.getInt(c.getColumnIndex("ano")));
            motos.add(moto);
        }
        c.close();
        return motos;
    }

    public void deleta(Moto moto){
        SQLiteDatabase db = getReadableDatabase();
        String[] parametros = {moto.getId().toString()};
        db.delete("Moto", "id = ?", parametros);
    }

    public void altera(Moto moto){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = pegaDadosMoto(moto);
        String[] parametros = {moto.getId().toString()};
        db.update("Moto", dados, "id = ?", parametros);
    }
}
