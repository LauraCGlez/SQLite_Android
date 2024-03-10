package com.example.mysql_lite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mysql_lite.model.Contacto;
import com.example.mysql_lite.repository.ContactoRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Optional;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText buscarContacto;
    private EditText nombre, movil, mail;
    private ContactoRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //incializar variables
        this.buscarContacto = findViewById(R.id.buscarContacto);
        this.nombre = findViewById(R.id.nombre);
        this.movil = findViewById(R.id.movil);
        this.mail = findViewById(R.id.mail);

        //incializar repositorio
        this.repository = new ContactoRepository(MainActivity.this);

    }

    /**
     * Metodo para buscar un contacto
     * almacenado en la base de datos
     * @param view
     */
    public void buscarContacto(View view) {
        //almacenar el nombre a buscar
        String buscarNombre = this.buscarContacto.getEditableText().toString();

        //comprobar si estan los datos
        if(buscarNombre.isEmpty()){
            Toast.makeText(this,"Por favor ingresar el nombre a buscar ",Toast.LENGTH_LONG).show();
            return;
        }

        //comprobar en la BBDD
        Optional<Contacto> contactoEncontrado = this.repository.buscarContactoPorNombre(buscarNombre);

        if(contactoEncontrado.isEmpty()){
            Toast.makeText(this,"Contacto: " + buscarNombre + ", no esta registrado",Toast.LENGTH_LONG).show();
            return;
        }

        //mostrar los datos del contacto encontrado
        this.nombre.setText(contactoEncontrado.get().getNombre());
        this.movil.setText(contactoEncontrado.get().getMovil());
        this.mail.setText(contactoEncontrado.get().getMail());

        //limpiar buscador
        this.buscarContacto.setText("");


    }

    /**
     * Metodo para almacenar un contacto
     * en la base de datos
     * @param view
     */
    public void guardarContacto(View view){
        //almacenar los datos ingresados en variables local
        String nNombre = this.nombre.getText().toString();
        String nMovil = this.movil.getText().toString();
        String nMail = this.mail.getText().toString();

        //comprobar si están los datos
        if(nNombre.isEmpty() && nMovil.isEmpty() && nMail.isEmpty()){
            Toast.makeText(this,"Por favor ingresar todo los datos ",Toast.LENGTH_LONG).show();
            return;
        }
        //crear nuevo contacto
        Contacto nuevoContacto = new Contacto(nNombre,nMovil,nMail);

        //alamcenar nuevoContacto
        long contactoGuardado = this.repository.añadirContacto(nuevoContacto);

        //comprobar que no este guardado
        if(contactoGuardado > 0){
            limpiarDatos(view);
            Toast.makeText(this,"¡Contacto: " + nuevoContacto.getNombre() + " ha sido registrado!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"¡Contacto: " + nuevoContacto.getNombre() + " ya existe!",Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void limpiarDatos(View view) {
        //limpiar los valores almacenados
        this.nombre.setText("");
        this.movil.setText("");
        this.mail.setText("");

    }



}