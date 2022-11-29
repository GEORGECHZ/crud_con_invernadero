package com.example.crud_con_invernadero

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.text.Editable
import android.widget.Toast
import com.example.crud_con_invernadero.databinding.ActivityCrudBinding

class ActivityCRUD : AppCompatActivity() {

    private lateinit var binding: ActivityCrudBinding

    var idUser:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recibirExtras = intent.extras
        val message = recibirExtras?.getString("mensaje")

        binding.botonGuardar.setOnClickListener(){
            val dbConexion = DBusuario(this)
            val db = dbConexion.writableDatabase

            val tUsuario = binding.textNick.text.toString()
            val tNombre = binding.textNombre.text.toString()
            val tApellido = binding.editApellido.text.toString()
            val tContra = binding.editContra.text.toString()
            val tImagen = binding.editImagen.text.toString()
            val codigo = binding.textCodigo.text.toString()

            val newReg = ContentValues().apply {
                put(FeedReaderContract.FeedEntry.usuario,tUsuario)
                put(FeedReaderContract.FeedEntry.nombre,tNombre)
                put(FeedReaderContract.FeedEntry.apellido,tApellido)
                put(FeedReaderContract.FeedEntry.contrasena,tContra)
                put(FeedReaderContract.FeedEntry.imagen,tImagen)
            }

            if (message == "aQlR21z"){
                if (tUsuario == recibirExtras.getString("usuario")){
                    val selection = "${BaseColumns._ID} LIKE ?"
                    val id = recibirExtras.getString("id")
                    val argumento = arrayOf("$id")
                    db.update(FeedReaderContract.FeedEntry.nombreTabla,newReg,selection,argumento)
                    Toast.makeText(this, "Registro Aactualizado con Exito", Toast.LENGTH_SHORT).show()
                    val intento1 = Intent(this,ActivityUsuario::class.java)
                    intento1.putExtra("user",tUsuario)
                    intento1.putExtra("password",tContra)
                    db.close()
                    dbConexion.close()
                    finish()
                    startActivity(intento1)
                } else {
                    Toast.makeText(this, "No Modificar la Casilla de Usuario", Toast.LENGTH_SHORT).show()
                    db.close()
                    dbConexion.close()
                }
            } else {
                val cursor = db.rawQuery("SELECT * FROM ${FeedReaderContract.FeedEntry.nombreTabla}",null)
                if (cursor.moveToFirst()){
                    do {
                        idUser = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                        val itemUser = cursor.getString(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.usuario))
                        if (tUsuario == itemUser){
                            Toast.makeText(this, "El Usuario ya Existe", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                    }while (cursor.moveToNext())
                }
                cursor.close()

                if (codigo == "3223"){
                    if (tUsuario != "" && tContra != "") {
                        val resultado = db.insert(FeedReaderContract.FeedEntry.nombreTabla,null,newReg)
                        if (resultado.toInt() == -1){
                            Toast.makeText(this, "ERROR AL INSERTAR", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Datos Guardados", Toast.LENGTH_SHORT).show()
                            val intento1 = Intent(this,MainActivity::class.java)
                            startActivity(intento1)
                        }
                    } else {
                        Toast.makeText(this, "Se Necesita Usuario y Contraseña", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Código de Verificación No Válido", Toast.LENGTH_SHORT).show()
                }
                db.close()
                dbConexion.close()
            }
        }

        if (message == "aQlR21z"){
            val user = recibirExtras.getString("usuario")
            val name = recibirExtras.getString("nombre")
            val lastname = recibirExtras.getString("apellido")
            val password = recibirExtras.getString("contra")
            val image = recibirExtras.getString("imagen")
            binding.textNick.text = Editable.Factory.getInstance().newEditable(user)
            binding.textNombre.text = Editable.Factory.getInstance().newEditable(name)
            binding.editApellido.text = Editable.Factory.getInstance().newEditable(lastname)
            binding.editContra.text = Editable.Factory.getInstance().newEditable(password)
            binding.editImagen.text = Editable.Factory.getInstance().newEditable(image)
            binding.textCodigo.text = Editable.Factory.getInstance().newEditable("000000")
        }
    }
}