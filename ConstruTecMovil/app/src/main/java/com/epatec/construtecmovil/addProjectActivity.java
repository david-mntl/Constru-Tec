package com.epatec.construtecmovil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
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
import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by David on 23/10/2016.
 */
public class addProjectActivity extends ActionBarActivity {

    private TabHost.TabSpec tabProjects;
    public ArrayAdapter dropDownAdapter;

    HashMap<String,String> arrayInfoProjects;
    ArrayList<String> arrayNameProjects;
    ArrayList<String> stagesArray;

    public Spinner listDropDown;
    public Projects newProject;

    public String tabIndicator;

    public ArrayList<Projects> projectList = new ArrayList<>();


    public JSONArray jsonTOSend;
    public JSONArray jsonProjects;

    public String toAsyncJSON;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproject);

        final TabHost viewSwitch = (TabHost)findViewById(R.id.managerHost);

        viewSwitch.setup();



        //Tab 1
        tabProjects = viewSwitch.newTabSpec("Añadir Proyecto");
        tabProjects.setContent(R.id.añadirProjecto);
        tabProjects.setIndicator("Añadir Proyecto");
        viewSwitch.addTab(tabProjects);

        //Tab 2
        tabProjects = viewSwitch.newTabSpec("Añadir Etapa");
        tabProjects.setContent(R.id.añadirStage);
        tabProjects.setIndicator("Añadir Etapa");
        viewSwitch.addTab(tabProjects);

        //Tab 2
        tabProjects = viewSwitch.newTabSpec("Añadir Etapa al Proyecto");
        tabProjects.setContent(R.id.linearLayout3);
        tabProjects.setIndicator("Añadir Etapa al Proyecto");
        viewSwitch.addTab(tabProjects);

        EditText ID = (EditText)findViewById(R.id.newIdProject);
        ID.setEnabled(false);

        try {

            AsyncTaskProjects getInfo = new AsyncTaskProjects();
            getInfo.execute("init");

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }


        final Button addStageButton = (Button) findViewById(R.id.addStageButton);
        addStageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final TabHost managerHost = (TabHost) findViewById(R.id.managerHost);
                tabIndicator = managerHost.getCurrentTab() + "";

                createButtonSend();
            }
        });

        final Button addProjButton = (Button) findViewById(R.id.addProjButton);
        addProjButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                final TabHost managerHost = (TabHost) findViewById(R.id.managerHost);
                tabIndicator = managerHost.getCurrentTab() + "";

                Log.i("INDICATORTAB" , tabIndicator);

                createButtonSend();
            }
        });



    }

    public void initData()
    {
        listDropDown = (Spinner)findViewById(R.id.projectsSpinner);

        dropDownAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayNameProjects);

        listDropDown.setAdapter(dropDownAdapter);

        Spinner namesSpinner = (Spinner) findViewById(R.id.namesSpinner);

        ArrayAdapter<String> stagesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, stagesArray);

        namesSpinner.setAdapter(stagesAdapter);
    }


    public void createButtonSend()
    {
        JSONObject jsonObject = new JSONObject();

        Log.i("TABINDICATOR", tabIndicator);

        if (tabIndicator.compareTo( 0+"") ==0)
        {

            final EditText projectName = (EditText)findViewById(R.id.projectName);
            final EditText newProLocation = (EditText)findViewById(R.id.newProLocation);
            final EditText newEmpID = (EditText)findViewById(R.id.newEmpID);
            final EditText newClientID = (EditText)findViewById(R.id.newClientID);
            final EditText newCommProj = (EditText)findViewById(R.id.newCommProj);
            final EditText newDetailProj = (EditText)findViewById(R.id.newDetailProj);


            try {
                jsonObject.put("Name",projectName.getText());
                jsonObject.put("Location",newProLocation.getText());
                jsonObject.put("ID_Engineer",newEmpID.getText());
                jsonObject.put("ID_Customer",newClientID.getText());
                jsonObject.put("Comments", newCommProj.getText());
                jsonObject.put("Details",newDetailProj.getText());


                toAsyncJSON = jsonObject.toString();


            } catch (JSONException e) {
                e.printStackTrace();
            }





        }

        else if (tabIndicator.compareTo( 1+"") ==0)
        {

            final EditText startDate = (EditText)findViewById(R.id.startDate);
            final EditText endDate = (EditText)findViewById(R.id.endDate);
            final EditText newComment = (EditText)findViewById(R.id.newComment);
            final EditText newDetailStage = (EditText)findViewById(R.id.newDetailStage);
            final EditText newIdProject = (EditText)findViewById(R.id.newIdProject);

            listDropDown = (Spinner)findViewById(R.id.projectsSpinner);
            Spinner stagesDropDown = (Spinner)findViewById(R.id.namesSpinner);

            String projectName = listDropDown.getSelectedItem().toString();
            String stagesName = stagesDropDown.getSelectedItem().toString();

            try {

                jsonObject.put("ID_Project", arrayInfoProjects.get(projectName));

                jsonObject.put("Stage_Name",stagesName);
                jsonObject.put("Start_Date",startDate.getText());
                jsonObject.put("End_Date",endDate.getText());
                jsonObject.put("Details", newDetailStage.getText());
                jsonObject.put("Comments", newComment.getText());


                newIdProject.setText(arrayInfoProjects.get(projectName));






                toAsyncJSON = jsonObject.toString();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        else
        {

        }
        try
        {
            AsyncTaskADDProjects addProjects = new AsyncTaskADDProjects();
            addProjects.execute("init");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }



    /************************************************** ASYNC TASK TO GET INFORMATION*****************************************/
    private class AsyncTaskProjects extends AsyncTask<String, String, String> {


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
            InputStream inputStreamStage = null;

            String result = "";
            ConnectionDataHolder connClass = ConnectionDataHolder.getInstance();
            UserDataHolder user = UserDataHolder.getInstance();

            try {

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL

                /*String serverRequest = (getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                        + getString(R.string.allProjects)   );
                */
                //TODO
                /*****************CAMBIAR ESTO*************************************/
                String serverRequest = "http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com"
                        + getString(R.string.allProjects)+ user.userType + getString(R.string.projectUserID) + user.userID; ;
                /*****************************************************************/

                Log.i("******url*******", serverRequest);

                HttpResponse httpResponse = httpclient.execute(new HttpGet(serverRequest ));


                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null)
                {
                    result = convertInputStreamToString(inputStream);
                    result = result.toString().substring(1, result.toString().length() - 1);

                    result = convertStandardJSONString(result);
                    Log.i("JSONJSON", result);
                    jsonProjects = new JSONArray(result);

                    projectList = new ArrayList<>();
                    arrayInfoProjects = new HashMap<String,String>();
                    arrayNameProjects = new ArrayList<String>();

                    for(int i =0; i < jsonProjects.length(); i++)
                    {
                        String stageResult = "";
                        Projects newProject = new Projects();


                        newProject.project_ID= (jsonProjects.getJSONObject(i).getString("id_project"));
                        newProject.project_Name = jsonProjects.getJSONObject(i).getString("project_name");
                        //newProject.details = jsonProjects.getJSONObject(i).getString("details");
                        newProject.location = (jsonProjects.getJSONObject(i).getString("location"));


                        Log.i("OBJECTOBJ", jsonProjects.getJSONObject(i).getString("project_name"));
                        projectList.add(newProject);
                        arrayInfoProjects.put(newProject.project_Name, newProject.project_ID);
                        arrayNameProjects.add(newProject.project_Name);
                    }
                }
                else
                    result = "Did not work!";

            } catch (Exception e) {
                publishProgress(e.toString());
            }

            try {
                /**************************************************************************
                 * TO GET ALL STAGES
                 ****************************************************************************/


                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL

                /*String serverRequest = (getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                        + getString(R.string.allStagesAvailable)   );
                */
                //TODO
                /*****************CAMBIAR ESTO*************************************/
                String serverRequest = "http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com"
                        + getString(R.string.allStagesAvailable) ;
                /*****************************************************************/

                Log.i("******url*******", serverRequest);

                HttpResponse httpResponse = httpclient.execute(new HttpGet(serverRequest ));


                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null)
                {
                    result = convertInputStreamToString(inputStream);
                    result = result.toString().substring(1, result.toString().length() - 1);

                    result = convertStandardJSONString(result);
                    Log.i("JSONJSON", result);
                    JSONArray stagesJsonArray = new JSONArray(result);
                    stagesArray = new ArrayList<String>();

                    for(int i =0; i < jsonProjects.length(); i++)
                    {
                        String stage = stagesJsonArray.getJSONObject(i).getString("name");

                        stagesArray.add(stage);
                    }
                }
                else
                    result = "Did not work!";

            } catch (Exception e) {
                publishProgress(e.toString());
            }


            publishProgress(result);
            return "";
        }


        /***************** ON PROGRESS UPDATE **************************/
        /**
         * Method to process the data received froom the server
         * @param progress
         */
        @Override
        protected void onProgressUpdate(String... progress ) {

            try {

                initData();
            }
            catch (Exception e) {
                Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


    /*******************************************************************************************************/
    /************************************** Register Stage and Project ****************************************/
    /*******************************************************************************************************/
    /*******************************************************************************************************/



    private class AsyncTaskADDProjects extends AsyncTask<String, String, String> {
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

                if ( tabIndicator.compareTo( 0+"" ) ==0) {

                    /**url = new URL(getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                            + getString(R.string.PostProject));*/

                    url = new URL("http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com"
                            + getString(R.string.PostProject));

                    Log.i("******CUSTOMER*******:", toAsyncJSON.toString());
                }
                else
                {
                    /**url = new URL(getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                            + getString(R.string.CreateStage));*/

                    url = new URL("http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com"
                            + getString(R.string.CreateStage));

                    Log.i("*******LINK*******:", url.toString());
                    Log.i("*******EMPLOYEE*******:", toAsyncJSON.toString());



                }
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(toAsyncJSON.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(toAsyncJSON.getBytes());
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

                new SweetAlertDialog(addProjectActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Completado")
                        .setContentText("Registro Finalizado")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                //addProjectActivity.this.finish();
                            }
                        })
                        .show();
            }
            else {
                new SweetAlertDialog(addProjectActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops")
                        .setContentText(response)
                        //.setContentText("Porfavor verifique los datos proporcionados")
                        .show();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }







}
