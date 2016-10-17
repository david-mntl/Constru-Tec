package com.epatec.construtecmovil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.widget.Spinner;
import android.widget.TabHost;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class RegisterActivity extends ActionBarActivity  {

    String userNickname = "";
    String userPassword = "";

    /*AsyncTaskConnector connector;*/

    private TabHost tabHost;
    private TabHost.TabSpec spec;

    private String tabIndicator = "";

    private ArrayAdapter<String> dropDownRole;
    private Spinner dropdown;

    /*
      ****************  Client Strings Texts **********************
    */

    private String nameClient ;
    private String LN1Client ;
    private String LN2Client ;
    private String passClient ;
    private String telClient ;
    private String mailClient ;
    private String usrClient ;
    private String idClient;


     /*
      ****************  Employee Strings Texts **********************
    */

    private String nameEmployee ;
    private String LN1Employee ;
    private String LN2Employee ;
    private String passEmployee ;
    private String telEmployee ;
    private String mailEmployee ;
    private String usrEmployee ;
    private String idEmployee;
    private String engCode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TabHost selectRole = (TabHost)findViewById(R.id.selectRole);
        selectRole.setup();

        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Ingeniero", "Arquitecto"};

        dropDownRole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(dropDownRole);

        //Tab 1
        spec = selectRole.newTabSpec("Cliente");
        spec.setContent(R.id.Cliente);
        spec.setIndicator("Cliente");
        selectRole.addTab(spec);

        //Tab 2
        spec = selectRole.newTabSpec("Empleado");
        spec.setContent(R.id.Empleado);
        spec.setIndicator("Empleado");
        selectRole.addTab(spec);



        final Button registerClient = (Button) findViewById(R.id.registerClient);
        registerClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                sendRegister();
            }
        });

    }

    public void sendRegister ()
    {

        if (spec.getTag().compareTo("Cliente")==0)
        {
        /*
        ****************  Client Edit Texts **********************
        */

            tabIndicator = "Cliente";

            final EditText nameClientEDIT = (EditText)findViewById(R.id.nameClient);
            final EditText LN1ClientEDIT = (EditText)findViewById(R.id.LN1Client);
            final EditText LN2ClientEDIT = (EditText)findViewById(R.id.LN2Client);
            final EditText passClientEDIT = (EditText)findViewById(R.id.passClient);
            final EditText telClientEDIT = (EditText)findViewById(R.id.telClient);
            final EditText mailClientEDIT = (EditText)findViewById(R.id.mailClient);
            final EditText usrClientEDIT = (EditText)findViewById(R.id.usrClient);
            final EditText idClientEDIT = (EditText)findViewById(R.id.idClient);

            /*
            ****************  Asigning Client Strings **********************
            */
            nameClient = nameClientEDIT.getText().toString();
            LN1Client = LN1ClientEDIT.getText().toString();
            LN2Client = LN2ClientEDIT.getText().toString();
            passClient = passClientEDIT.getText().toString();
            telClient = telClientEDIT.getText().toString();
            mailClient = mailClientEDIT.getText().toString();
            usrClient = usrClientEDIT.getText().toString();
            idClient = idClientEDIT.getText().toString();
        }

        if (spec.getTag().compareTo("Empleado")==0)
        {

            tabIndicator = "Empleado";
        /*
        ****************  Employee Edit Texts **********************
        */
            final EditText nameEmployeeEDIT = (EditText)findViewById(R.id.nameEmployee);
            final EditText LN1EmployeeEDIT = (EditText)findViewById(R.id.LN1Employee);
            final EditText LN2EmployeeEDIT = (EditText)findViewById(R.id.LN2Employee);
            final EditText passEmployeeEDIT = (EditText)findViewById(R.id.passEmployee);
            final EditText telEmployeeEDIT = (EditText)findViewById(R.id.telEmployee);
            final EditText mailEmployeeEDIT = (EditText)findViewById(R.id.mailEmployee);
            final EditText usrEmployeeEDIT = (EditText)findViewById(R.id.usrEmployee);
            final EditText idEmployeeEDIT = (EditText)findViewById(R.id.idEmployee);
            final EditText engCodeEDIT = (EditText)findViewById(R.id.engCode);

        /*
        ****************  Asigning Employee Strings **********************
        */
            nameEmployee = nameEmployeeEDIT.getText().toString();
            LN1Employee = LN1EmployeeEDIT.getText().toString();
            LN2Employee = LN2EmployeeEDIT.getText().toString();
            passEmployee = passEmployeeEDIT.getText().toString();
            telEmployee = telEmployeeEDIT.getText().toString();
            mailEmployee = mailEmployeeEDIT.getText().toString();
            usrEmployee = usrEmployeeEDIT.getText().toString();
            idEmployee = idEmployeeEDIT.getText().toString();
            engCode = engCodeEDIT.getText().toString();

        }


        /*connector = new AsyncTaskConnector();
        connector.execute("init");*/

    }

}



