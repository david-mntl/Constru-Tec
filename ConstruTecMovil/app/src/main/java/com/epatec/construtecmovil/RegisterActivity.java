package com.epatec.construtecmovil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
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

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class RegisterActivity extends ActionBarActivity  {

    String jsonTOsend = "";

    String userNickname = "";

    AsyncTaskConnector connector;
    JSONArray userInfo = null;

    private TabHost tabHost;
    private TabHost.TabSpec spec;

    private String tabIndicator ;

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

        final TabHost selectRole = (TabHost)findViewById(R.id.selectRole);

        selectRole.setup();

        /**
         ************** DROPDOWN Settings*******************************************************************
         */

        dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Ingeniero", "Arquitecto"};

        dropDownRole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(dropDownRole);
        /***************************************************************************************************/


        //Tab 1
        spec = selectRole.newTabSpec("Cliente");
        spec.setContent(R.id.scrollViewClient);
        spec.setIndicator("Cliente");
        selectRole.addTab(spec);

        //Tab 2
        spec = selectRole.newTabSpec("Empleado");
        spec.setContent(R.id.scrollViewEmpleado);
        spec.setIndicator("Empleado");
        selectRole.addTab(spec);





        final Button registerClient = (Button) findViewById(R.id.registerClient);
        registerClient.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final TabHost selectRole = (TabHost) findViewById(R.id.selectRole);
                tabIndicator = selectRole.getCurrentTab()+"";
                sendRegister();
            }
        });

        final Button registerEmployee = (Button) findViewById(R.id.registerEmployee);
        registerEmployee.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final TabHost selectRole = (TabHost)findViewById(R.id.selectRole);
                tabIndicator = selectRole.getCurrentTab()+"";

                sendRegister();
            }
        });

    }

    public void sendRegister ()
    {
        JSONObject jsonObject = new JSONObject();

        Log.e("*********TAB****", tabIndicator.toString() );

        if (tabIndicator.compareTo(  getString(R.string.tipoRegisterClient)) ==0)
        {
        /*
        ****************  Client Edit Texts **********************
        */




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

            Log.e("******name********",nameClient);


            try {
                jsonObject.put("ID_Customer", idClient);
                jsonObject.put("Name", nameClient);
                jsonObject.put("Lastname_1", LN1Client);
                jsonObject.put("Lastname_2", LN2Client);
                jsonObject.put("Phone", telClient);
                jsonObject.put("Email", mailClient);
                jsonObject.put("Username", usrClient);
                jsonObject.put("Password", passClient);

                userNickname = usrClient;
                jsonTOsend = jsonObject.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        else if (tabIndicator.compareTo(  getString(R.string.tipoRegisterEmployee)) ==0)
        {


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

            try {
                jsonObject.put("ID_Engineer", idEmployee);
                jsonObject.put("Name", nameEmployee);
                jsonObject.put("Lastname_1", LN1Employee);
                jsonObject.put("Lastname_2", LN2Employee);
                jsonObject.put("Phone", telEmployee);
                jsonObject.put("Email", mailEmployee);
                jsonObject.put("Eng_Code", engCode);
                jsonObject.put("Username", usrEmployee);
                jsonObject.put("Password", passEmployee);
                jsonObject.put("Role",  dropdown.getSelectedItem().toString());

                jsonTOsend = jsonObject.toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        connector = new AsyncTaskConnector();
        connector.execute("init");

    }



    private class AsyncTaskConnector extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            /*******************/
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;


            try {
                //constants

                ConnectionDataHolder connClass = ConnectionDataHolder.getInstance();
                URL url;

                /* *************** REGISTER CUSTOMER LINK CONNECTION ****************** */

                if ( tabIndicator.compareTo(  getString(R.string.tipoRegisterClient)) ==0) {

                    if(connClass.online)
                    {
                        url = new URL("http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com"
                                + getString(R.string.customerRegister));


                    }
                    else
                    {

                        url = new URL(getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                                + getString(R.string.customerRegister));

                    }


                }
                else
                {
                    if(connClass.online)
                    {
                        url = new URL("http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com"
                                + getString(R.string.employeeRegister));
                    }
                    else
                    {
                        url = new URL(getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                                + getString(R.string.employeeRegister));
                    }


                }
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(jsonTOsend.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(jsonTOsend.getBytes());
                //clean up
                os.flush();

                //do somehting with response
                is = conn.getInputStream();

                StringBuffer response;


                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();


                publishProgress(response.toString());





            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //clean up
                try {
                    os.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                conn.disconnect();
            }

            return "";
        }
        @Override
        protected void onProgressUpdate(String... progress) {

            String response = progress[0].split("\"")[1];
            if(response.compareTo("Ok") == 0 ) {

                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Completado")
                        .setContentText("Registro Finalizado")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                RegisterActivity.this.finish();
                            }
                        })
                        .show();
            }
            else {
                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops")
                        .setContentText("Porfavor verifique los datos proporcionados")
                        .show();
            }

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}



