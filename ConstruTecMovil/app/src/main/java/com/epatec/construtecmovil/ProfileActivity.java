package com.epatec.construtecmovil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

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


import cn.pedant.SweetAlert.SweetAlertDialog;


public class ProfileActivity extends ActionBarActivity {

    AsyncTaskGetInfo connector;
    JSONArray userInfo = null;
    String jsonTOsend = "";
    AsyncTaskConnector connectorUpdate;
     /*
      ****************  User Strings Texts **********************
    */


        private String userID ;
        private String userName ;
        private String userLN1 ;
        private String userLN2 ;
        private String userNick ;
        private String userPass ;
        private String userTel ;
        private String userMail ;
        private String userRole ;
        private String userEngCode;

   /* final EditText userNameEDIT = (EditText)findViewById(R.id.userName);
    final EditText userLN1EDIT = (EditText)findViewById(R.id.userLN1);
    final EditText userLN2EDIT = (EditText)findViewById(R.id.userLN2);
    final EditText userPassEDIT = (EditText)findViewById(R.id.userPass);
    final EditText userTelEDIT = (EditText)findViewById(R.id.userTel);
    final EditText userMailEDIT = (EditText)findViewById(R.id.userMail);

    final TextView userNickEDIT = (TextView)findViewById(R.id.userNick);
    final TextView userRoleEDIT = (TextView)findViewById(R.id.userRole);*/

    /*private EditText userNameEDIT;
    private EditText userLN1EDIT;
    private EditText userLN2EDIT;
    private EditText userPassEDIT;
    private EditText userTelEDIT;
    private EditText userMailEDIT;

    private TextView userNickEDIT;
    private TextView userRoleEDIT;
    private TextView userEngCodeEDIT;*/


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            try
            {
                connector = new AsyncTaskGetInfo();
                connector.execute("init");
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }



            final Button updateProfile = (Button) findViewById(R.id.updateProfile);
            updateProfile.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    sendUpdate();
                }
            });
        }

    public void sendUpdate ()
    {
        JSONObject jsonObject = new JSONObject();

        /*
        ****************  User Edit Texts **********************
        */

        final EditText userNameEDIT = (EditText)findViewById(R.id.userName);
        final EditText userLN1EDIT = (EditText)findViewById(R.id.userLN1);
        final EditText userLN2EDIT = (EditText)findViewById(R.id.userLN2);
        final EditText userPassEDIT = (EditText)findViewById(R.id.userPass);
        final EditText userTelEDIT = (EditText)findViewById(R.id.userTel);
        final EditText userMailEDIT = (EditText)findViewById(R.id.userMail);

        final TextView userEngCodeEDIT = (TextView)findViewById(R.id.userCode);

        final TextView userNickEDIT = (TextView)findViewById(R.id.userNick);
        final TextView userRoleEDIT = (TextView)findViewById(R.id.userRole);

        /*
        ****************  Asigning User Strings **********************
        */

        userName = userNameEDIT.getText().toString() ;
        userLN1 = userLN1EDIT.getText().toString();
        userLN2 = userLN2EDIT.getText().toString();
        userTel = userTelEDIT.getText().toString() ;
        userMail= userMailEDIT.getText().toString() ;
        userPass = userPassEDIT.getText().toString();
        userEngCode = userEngCodeEDIT.getText().toString();

            try {
                UserDataHolder userInfo = UserDataHolder.getInstance();
                if (userRoleEDIT.getText().toString().compareTo(getString(R.string.roleClient)) ==0)
                {

                    jsonObject.put("ID_Customer", userID);
                    jsonObject.put("Name", userName);
                    jsonObject.put("Lastname_1", userLN1);
                    jsonObject.put("Lastname_2", userLN2);
                    jsonObject.put("Phone", userTel);
                    jsonObject.put("Email", userMail);
                    jsonObject.put("Username", userInfo.user);
                    jsonObject.put("Password", userPass);

                    jsonTOsend = jsonObject.toString();
                }

                else
                {
                    jsonObject.put("ID_Engineer", userID);
                    jsonObject.put("Name", userName);
                    jsonObject.put("Lastname_1", userLN1);
                    jsonObject.put("Lastname_2", userLN2);
                    jsonObject.put("Phone", userTel);
                    jsonObject.put("Email", userMail);
                    jsonObject.put("Eng_Code", userEngCode);
                    jsonObject.put("Username", userInfo.user);
                    jsonObject.put("Password", userPass);
                    jsonObject.put("Role", userEngCode);

                    jsonTOsend = jsonObject.toString();

                }

                connectorUpdate = new AsyncTaskConnector();
                connectorUpdate.execute("init");


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }







/*************************************** GET USER INFO ASYNC TASK ******************************************/

    private class AsyncTaskGetInfo extends AsyncTask<String, String, String> {

        // convert inputstream to String
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        public String convertStandardJSONString(String data_json) {
            data_json = data_json.replaceAll("\\\\r\\\\n", "");
            data_json = data_json.replace("\\", "");
            return data_json;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {
            InputStream inputStream = null;
            String result = "";
            try {

                ConnectionDataHolder connClass = ConnectionDataHolder.getInstance();
                UserDataHolder user = UserDataHolder.getInstance();

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet
                        (getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                                + getString(R.string.getCustomerInfo) + user.user ));

                Log.i("LINKUSER****",getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                        + getString(R.string.getCustomerInfo) + user.user );

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";

            } catch (Exception e) {
                publishProgress(e.toString());
            }

            publishProgress(result);

            return "";
        }
        @Override
        protected void onProgressUpdate(String... progress) {

            TextView txt = (TextView)findViewById(R.id.textView3);
            UserDataHolder user = UserDataHolder.getInstance();

            String result = progress[0].toString().substring(1, progress[0].toString().length() - 1);
            result = convertStandardJSONString(result);

            try {
                userInfo = new JSONArray(result);
            }

            catch (JSONException e) {
                txt.setText(e.toString());
            }



            try {


                userName = userInfo.getJSONObject(0).getString("name");
                userLN1  = userInfo.getJSONObject(0).getString("lastname1");
                userLN2  = userInfo.getJSONObject(0).getString("lastname2");
                userNick  = userInfo.getJSONObject(0).getString("username");
                userPass  = userInfo.getJSONObject(0).getString("password");
                userTel  = userInfo.getJSONObject(0).getString("phone");
                userMail  = userInfo.getJSONObject(0).getString("email");

                if (user.userROLE.compareTo(  getString(R.string.roleClient)) ==0)
                {
                    userEngCode = "";
                    userRole = getString(R.string.roleClient);
                    userID = userInfo.getJSONObject(0).getString("id_customer");
                }

                else
                {
                    userRole  = userInfo.getJSONObject(0).getString("role");
                    userEngCode  = userInfo.getJSONObject(0).getString("eng_code");
                    userID = userInfo.getJSONObject(0).getString("id_engineer");
                }

                final EditText userNameEDIT;
                final EditText userLN1EDIT;
                final EditText userLN2EDIT;
                final EditText userPassEDIT;
                final EditText userTelEDIT;
                final EditText userMailEDIT;

                final TextView userNickEDIT;
                final TextView userRoleEDIT;
                final TextView userEngCodeEDIT;


                userNameEDIT = (EditText)findViewById(R.id.userName);
                userLN1EDIT = (EditText)findViewById(R.id.userLN1);
                userLN2EDIT = (EditText)findViewById(R.id.userLN2);
                userPassEDIT = (EditText)findViewById(R.id.userPass);
                userTelEDIT = (EditText)findViewById(R.id.userTel);
                userMailEDIT = (EditText)findViewById(R.id.userMail);

                userNickEDIT = (TextView)findViewById(R.id.userNick);
                userRoleEDIT = (TextView)findViewById(R.id.userRole);
                userEngCodeEDIT =(TextView)findViewById(R.id.userCode);

                userNameEDIT.setText(userName);
                userLN1EDIT.setText(userLN1);
                userLN2EDIT.setText(userLN2);
                userNickEDIT.setText(userNick);
                userPassEDIT.setText(userPass);
                userTelEDIT.setText(userTel);
                userMailEDIT.setText(userMail);
                userRoleEDIT.setText(userRole);
                userEngCodeEDIT.setText(userEngCode);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


    /*************************************** UPDATE USER INFO ASYNC TASK ******************************************/


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

                UserDataHolder user = UserDataHolder.getInstance();

                ConnectionDataHolder connClass = ConnectionDataHolder.getInstance();
                URL url;

                /* *************** REGISTER CUSTOMER LINK CONNECTION ****************** */

                if (user.userROLE.compareTo(  getString(R.string.roleClient)) ==0) {

                    url = new URL(getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                            + getString(R.string.updateCustomerInfo));

                    Log.i("******CUSTOMER*******:", jsonTOsend.toString());
                }
                else
                {
                    url = new URL(getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                            + getString(R.string.updateEngineerInfo));

                    Log.i("*******EMPLOYEE*******:", jsonTOsend.toString());

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

                new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Completado")
                        .setContentText("Información Actualizada")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                ProfileActivity.this.finish();
                            }
                        })
                        .show();
            }
            else {
                new SweetAlertDialog(ProfileActivity.this, SweetAlertDialog.ERROR_TYPE)
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
