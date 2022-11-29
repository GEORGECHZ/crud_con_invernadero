package com.example.crud_con_invernadero

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.crud_con_invernadero.databinding.ActivityUsuarioBinding

@Suppress("NAME_SHADOWING")
class ActivityUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityUsuarioBinding

    var idUser:Int = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recibirExtras = intent.extras
        val user = recibirExtras?.getString("user")
        val pass = recibirExtras?.getString("password")
        val imagen = binding.imagenUser

        val dbConexion = DBusuario(this)
        val db = dbConexion.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${FeedReaderContract.FeedEntry.nombreTabla}",null)

        if (cursor.moveToFirst()){
            do {
                idUser = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                val itemUser = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.usuario))
                val itemContra = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.contrasena))
                val itemImagen = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.imagen))
                val itemNombre = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.nombre))
                val itemApellido = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.apellido))
                if (user == itemUser && pass == itemContra){
                    Glide.with(this).load(itemImagen).into(imagen)
                    binding.infoUser.text = "$itemNombre $itemApellido"
                }
            }while (cursor.moveToNext())
        }
        cursor.close()

        binding.botonEditar.setOnClickListener(){
            val intento2 = Intent(this,ActivityCRUD::class.java)
            val cursor = db.rawQuery("SELECT * FROM ${FeedReaderContract.FeedEntry.nombreTabla}",null)

            if (cursor.moveToFirst()){
                do {
                    idUser = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                    val itemUser = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.usuario))
                    val itemContra = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.contrasena))
                    val itemImagen = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.imagen))
                    val itemNombre = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.nombre))
                    val itemApellido = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.apellido))
                    if (user == itemUser && pass == itemContra){
                        intento2.putExtra("mensaje","aQlR21z")
                        intento2.putExtra("usuario",itemUser)
                        intento2.putExtra("nombre",itemNombre)
                        intento2.putExtra("apellido",itemApellido)
                        intento2.putExtra("contra",itemContra)
                        intento2.putExtra("imagen",itemImagen)
                        intento2.putExtra("id", "$idUser")
                        cursor.close()
                        db.close()
                        dbConexion.close()
                        startActivity(intento2)
                        return@setOnClickListener
                    }
                }while (cursor.moveToNext())
            }
        }

        binding.botonCerrar.setOnClickListener(){
            val intento1 = Intent(this,MainActivity::class.java)
            db.close()
            dbConexion.close()
            finish()
            startActivity(intento1)
        }

        binding.botonEliminar.setOnClickListener(){
            val dialogo = AlertDialog.Builder(this)
                .setTitle("Eliminar Usuario")
                .setMessage("¿Seguro que quieres eliminar este usuario?")
                .setNegativeButton(android.R.string.cancel) {dialog, _ ->
                    Toast.makeText(this,"Cancelaste",Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setPositiveButton(android.R.string.ok) {dialog, _ ->
                    val selection = "${FeedReaderContract.FeedEntry.usuario} LIKE ?"
                    val argumento = arrayOf(user)
                    db.delete(FeedReaderContract.FeedEntry.nombreTabla,selection,argumento)
                    Toast.makeText(this,"Usuario Eliminado",Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                    finish()
                    startActivity(Intent(this,MainActivity::class.java))
                    db.close()
                    dbConexion.close()
                }
                .setCancelable(false)
            dialogo.show()
        }

        binding.buttonMonitoreo.setOnClickListener(){
            startActivity(Intent(this,ActivityMonitoreo::class.java))
        }

    }

}