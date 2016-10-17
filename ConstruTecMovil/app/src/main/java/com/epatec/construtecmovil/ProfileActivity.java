package com.epatec.construtecmovil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ProfileActivity extends ActionBarActivity {

    AsyncTaskGetInfo connector;
    JSONArray userInfo = null;
     /*
      ****************  User Strings Texts **********************
    */

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

    private EditText userNameEDIT;
    private EditText userLN1EDIT;
    private EditText userLN2EDIT;
    private EditText userPassEDIT;
    private EditText userTelEDIT;
    private EditText userMailEDIT;

    private TextView userNickEDIT;
    private TextView userRoleEDIT;
    private TextView userEngCodeEDIT;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);

            userNameEDIT = (EditText)findViewById(R.id.userName);
            userLN1EDIT = (EditText)findViewById(R.id.userLN1);
            userLN2EDIT = (EditText)findViewById(R.id.userLN2);
            userPassEDIT = (EditText)findViewById(R.id.userPass);
            userTelEDIT = (EditText)findViewById(R.id.userTel);
            userMailEDIT = (EditText)findViewById(R.id.userMail);

            userNickEDIT = (TextView)findViewById(R.id.userNick);
            userRoleEDIT = (TextView)findViewById(R.id.userRole);
            userEngCodeEDIT =(TextView)findViewById(R.id.userCode);






            connector = new AsyncTaskGetInfo();
            connector.execute("init");




        }


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

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet("http://cewebserver.azurewebsites.net/Service1.svc/GetProducts?params=all"));

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

            String result = progress[0].toString().substring(1, progress[0].toString().length() - 1);
            result = convertStandardJSONString(result);

            try {
                userInfo = new JSONArray(result);


            }
            catch (JSONException e) {
                txt.setText(e.toString());
            }



            try {


                userName = userInfo.getJSONObject(0).getString("Name");
                userLN1  = userInfo.getJSONObject(0).getString("Lastname_1");
                userLN2  = userInfo.getJSONObject(0).getString("Lastname_2");
                userNick  = userInfo.getJSONObject(0).getString("Username");
                userPass  = userInfo.getJSONObject(0).getString("Password");
                userTel  = userInfo.getJSONObject(0).getString("Phone");
                userMail  = userInfo.getJSONObject(0).getString("Email");

                if (userInfo.getJSONObject(0).length() == Integer.parseInt(getString(R.string.employeeParameters)) )
                {
                    userRole  = userInfo.getJSONObject(0).getString("Role");
                    userEngCode  = userInfo.getJSONObject(0).getString("Eng_Code");

                }

                else
                {
                    userEngCode = "";
                    userRole = getString(R.string.employeeParameters);
                }

                userNameEDIT.setText(userName);
                userLN1EDIT.setText(userLN1);
                userLN2EDIT.setText(userLN2);
                userNickEDIT.setText(userNick);
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



}
