package com.example.tpdoor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import kotlin.properties.Delegates


class MainActivity : AppCompatActivity()  {

    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private var nombre by Delegates.notNull<String>()

    private lateinit var mProgressBar : ProgressDialog
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtName: EditText
    private lateinit var mAuth: FirebaseAuth
    private lateinit var forgotPassword: TextView


    private lateinit var btnRegistrar: Button
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mProgressBar = ProgressDialog(this)
        btnRegistrar = findViewById(R.id.btnRegister)
        btnLogin = findViewById(R.id.btnRecover)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)
        txtName = findViewById(R.id.txtName)
        forgotPassword = findViewById(R.id.txtForgot)

        mAuth = FirebaseAuth.getInstance()

        btnRegistrar.setOnClickListener()
        {
            if(txtName.visibility == View.INVISIBLE)
            {
                txtName.visibility = View.VISIBLE
                btnLogin.visibility = View.INVISIBLE
            }
            else
            {
                //txtName.visibility= View.VISIBLE
                //Toast.makeText(this,"Envia a Registrar",Toast.LENGTH_LONG).show()
                email = txtEmail.text.toString().trim()
                password = txtPassword.text.toString().trim()
                nombre = txtName.text.toString().trim()

                var usuario = persona(nombre,email,password,"")

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(this,"Se debe ingresar un Email Valido",Toast.LENGTH_LONG).show();
                    return@setOnClickListener
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(this,"Se debe ingresar un Password",Toast.LENGTH_LONG).show();
                    return@setOnClickListener
                }
                if(TextUtils.isEmpty(nombre))
                {
                    Toast.makeText(this,"Se debe ingresar un Nombre",Toast.LENGTH_LONG).show();
                    return@setOnClickListener
                }

                mProgressBar.setMessage("Registrando Usuario...")
                mProgressBar.show()

                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this)
                    {
                        //Verificamos que la tarea se ejecutó correctamente
                            task ->
                        if (task.isSuccessful) {
                            // Si se inició correctamente la sesión vamos a la vista Home de la aplicación
                            usuario._key= mAuth!!.currentUser!!.uid

                            //Toast.makeText(this,usuario.nombre+" "+usuario._key,Toast.LENGTH_LONG).show()
                            //Verify Email
                            verifyEmail()
                            goHome(usuario) // Creamos nuestro método en la parte de abajo
                        }
                        else {
                            mProgressBar.hide()
                            if(task.exception is FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(this, "Usuario ya esta Registrado, por Favor Logearse .",Toast.LENGTH_SHORT).show()
                            }
                            else
                            {
                                Toast.makeText(this, "Register failed.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
            }
        }

        btnLogin.setOnClickListener()
        {
            email = txtEmail.text.toString().trim()
            password = txtPassword.text.toString().trim()
            var usuario = persona("",email,password,"")

            if(TextUtils.isEmpty(email))
            {
                Toast.makeText(this,"Se debe ingresar un Email Valido",Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(password))
            {
                Toast.makeText(this,"Se debe ingresar un Password",Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }
            mProgressBar.setMessage("Autenticando Usuario...")
            mProgressBar.show()

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {

                    //Verificamos que la tarea se ejecutó correctamente
                        task ->
                    if (task.isSuccessful) {
                        // Si se inició correctamente la sesión vamos a la vista Home de la aplicación
                        usuario._key = mAuth!!.currentUser!!.uid
                        //Toast.makeText(this,usuario.email+" "+usuario._key,Toast.LENGTH_LONG).show()
                        goHome(usuario) // Creamos nuestro método en la parte de abajo
                    } else {
                        // sino le avisamos el usuairo que orcurrio un problema
                        mProgressBar.hide()
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }

        }

        forgotPassword!!
            .setOnClickListener { startActivity(Intent(this,
                HomeActivity::class.java)) }
    }

    private fun goHome(persona: persona) {
        //Ocultamos el progress
        mProgressBar.hide()
        //Nos vamos a Home
        val intent = Intent(this, AdminActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("key",persona._key)
        intent.putExtra("email",persona.email)
        startActivity(intent)
        //Toast.makeText(this,persona.email+" "+persona.key,Toast.LENGTH_LONG).show()
        inscribirUsuario(persona)
        //finish()
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Verification email sent to " + mUser.getEmail(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Failed to send verification email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun inscribirUsuario(persona: persona)
    {
        val queue = Volley.newRequestQueue(this)
        val URL_ROOT ="http://192.168.0.21/tpdoor/webServiceDevice.php?action=InsertarCliente&key="
        val url= URL_ROOT + persona._key+"&nombre="+persona.nombre+"&email="+persona.email+"&rol=1&gestor="+persona._key
        //Toast.makeText(this, url,Toast.LENGTH_SHORT).show()
        val stringRequest = StringRequest(Request.Method.GET,url,
            Response.Listener<String>{response ->
                //forgotPassword.setText(url)
                //Toast.makeText(this,"Response is: ${response.substring(0, 500)}",Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error -> error.printStackTrace() })
        queue.add(stringRequest)
    }
}


