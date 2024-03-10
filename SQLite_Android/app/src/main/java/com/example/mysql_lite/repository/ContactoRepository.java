package com.example.mysql_lite.repository;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.mysql_lite.model.Contacto;

import java.util.Optional;

/**
 * Clase AdminSQLiteOpenHelper
 * gestiona el repositorio
 */
public class ContactoRepository extends SQLiteOpenHelper {
    //incializar varibles que define la base de datos y su tabla
    private static final int DB_VERSION = 2;
    private static final String DB_NOMBRE = "db_agenda";
    private static final String TABLE_CONTACTO = "contacto";
    private static final String ID = "id";
    private static final String NOMBRE = "nombre";
    private static final String MOVIL = "movil";
    private static final String MAIL = "mail";

    public ContactoRepository(@Nullable Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //sentencia para crear la base de datos
        String sql = "CREATE TABLE " + TABLE_CONTACTO + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NOMBRE + " TEXT NOT NULL, "
                + MOVIL + " TEXT NOT NULL,"
                + MAIL + " TEXT NOT NULL)";

        //crear tabla
        database.execSQL(sql);

    }

    public Optional<Contacto> buscarContactoPorNombre(String nombre){
        Optional<Contacto> contactoEncontrado = Optional.empty();
        //instancia base de datos
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = {NOMBRE, MOVIL, MAIL};
        String selection = NOMBRE + " = ?";
        String[] selectionArgs = {nombre};
        //crear el curso para leer en la BBDD
        Cursor cursor = database.query(TABLE_CONTACTO,columns,selection, selectionArgs,null,null,null);

        //comprobar si existe el contacto
        if(cursor.moveToFirst()){
            contactoEncontrado = Optional.of(new Contacto(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)));
        }
        //cerrar lectura
        cursor.close();
        return contactoEncontrado;

    }

    //añadir contacto
    public long añadirContacto(Contacto nuevoContacto){
        long contactoRegistrado = 0;
        //instancia base de datos
        SQLiteDatabase database = ContactoRepository.this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NOMBRE,nuevoContacto.getNombre());
        values.put(MOVIL,nuevoContacto.getMovil());
        values.put(MAIL,nuevoContacto.getMail());

        //insertar valores en la BBDD
        contactoRegistrado = database.insert(TABLE_CONTACTO,null,values);

        //cerar BBDD
        database.close();

        return contactoRegistrado;
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        //eliminar la tabla existente
        database.execSQL("DROP TABLE IF EXISTS " + DB_NOMBRE);
        //crear nueva tabla
        onCreate(database);
    }
}
