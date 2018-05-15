package com.example.nobre.n_track;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nobre.n_track.modelo.Moto;

public class CadastroMotoHelper {

    private final Spinner campoMarca;
    private final EditText campoModelo;
    private final EditText campoAno;
    private final EditText campoCilindrada;
    private Moto moto;

    public CadastroMotoHelper(FormularioCadastroMoto activity) {
        campoMarca = (Spinner) activity.findViewById(R.id.spnMarcaMoto);
        campoModelo = (EditText) activity.findViewById(R.id.txtModelo);
        campoAno = (EditText) activity.findViewById(R.id.txtAno);
        campoCilindrada = (EditText) activity.findViewById(R.id.txtCilindrada);
        moto = new Moto();
    }

    public Moto pegaMoto() {
        moto.setMarca(campoMarca.getSelectedItem().toString());
        moto.setModelo(campoModelo.getText().toString());
        moto.setAno(Integer.parseInt(campoAno.getText().toString()));
        moto.setCilndrada(Integer.parseInt(campoCilindrada.getText().toString()));
        return moto;
    }

    public void preencheFormulario(Moto moto) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) campoMarca.getAdapter();
        int posicaoNoAdapter = adapter.getPosition(moto.getMarca());
        campoMarca.setSelection(posicaoNoAdapter);
        campoModelo.setText(moto.getModelo());
        campoAno.setText(String.valueOf(moto.getAno()));
        campoCilindrada.setText(String.valueOf(moto.getCilndrada()));
        this.moto = moto;
    }
}
