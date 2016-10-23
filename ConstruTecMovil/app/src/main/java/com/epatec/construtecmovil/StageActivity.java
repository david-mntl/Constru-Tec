package com.epatec.construtecmovil;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by David on 22/10/2016.
 */
public class StageActivity extends ActionBarActivity {

    private TabHost.TabSpec tabSpec;
    private ArrayList<Producto> products;
    private JSONArray jsonProducts;
    private JSONArray stageInfoJson;
    private JSONArray allProductsJSON;

    public String firstKeyName;
    public String stageInfo;
    public String location;
    public Spinner dropdown;
    public ArrayAdapter dropDownRole;




    String jsonTOsendDetail;
    String jsonTOsendComment;

    ArrayList<String> dropdownItems;

    ArrayAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        UserDataHolder user = UserDataHolder.getInstance();
        final Button projectInfoButton = (Button) findViewById(R.id.projectInfoButton);

        try
        {
            products = new ArrayList<>();
            AsyncTaskProjects connector = new AsyncTaskProjects();
            connector.execute("init");

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }

        /**
         * RESTRICTION to the user experience
         * Permissions
         */
        if (user.userType== "1")
        {
            EditText detail = (EditText) findViewById(R.id.detailStage);
            detail.setEnabled(true);
            projectInfoButton.setVisibility(View.VISIBLE);
        }
        else if (user.userType == "2")
        {
            EditText detail = (EditText) findViewById(R.id.detailStage);
            detail.setEnabled(true);
            EditText commentText = (EditText) findViewById(R.id.commentText);
            commentText.setEnabled(true);
            projectInfoButton.setVisibility(View.VISIBLE);
        }
        else
        {
            EditText detail = (EditText) findViewById(R.id.detailStage);
            detail.setEnabled(false);
            EditText commentText = (EditText) findViewById(R.id.commentText);
            commentText.setEnabled(false);

            projectInfoButton.setVisibility(View.INVISIBLE);
        }

        /**
         *  GETTING PARAMETERS from Outside
         */
        Intent myIntent = getIntent(); // gets the previously created intent
        firstKeyName = myIntent.getStringExtra("childName");
        stageInfo = "["+myIntent.getStringExtra("stageInfo") +"]";
        location = myIntent.getStringExtra("location");

        try {
            /**
             * SETTING basic Info to Display
             */
            stageInfoJson = new JSONArray(stageInfo);

            TextView projectName = (TextView)findViewById(R.id.projectNAME);
            projectName.setText( stageInfoJson.getJSONObject(0).getString("stage_name"));

            TextView startDate = (TextView)findViewById(R.id.startDate);
            startDate.setText(stageInfoJson.getJSONObject(0).getString("start_date"));

            TextView endDate = (TextView)findViewById(R.id.endDate);
            endDate.setText(stageInfoJson.getJSONObject(0).getString("end_date"));

            TextView completedStage = (TextView)findViewById(R.id.completedStage);
            completedStage.setText(stageInfoJson.getJSONObject(0).getString("completed"));

            TextView detailStage = (TextView)findViewById(R.id.detailStage);
            detailStage.setText(stageInfoJson.getJSONObject(0).getString("details"));

            TextView ubicacionStage = (TextView)findViewById(R.id.ubicacionStage);
            ubicacionStage.setText(location);

            TextView commentText = (TextView)findViewById(R.id.commentText);
            commentText.setText(stageInfoJson.getJSONObject(0).getString("comments"));

            Log.i("COMENTARIOTEXT", stageInfoJson.getJSONObject(0).getString("comments") + " vacio");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TabHost viewSwitch = (TabHost)findViewById(R.id.selectView);

        viewSwitch.setup();

        //Tab 1
        tabSpec = viewSwitch.newTabSpec("Project");
        tabSpec.setContent(R.id.ProjectInfo);
        tabSpec.setIndicator("Project");
        viewSwitch.addTab(tabSpec);

        //Tab 2
        tabSpec = viewSwitch.newTabSpec("Products");
        tabSpec.setContent(R.id.Products);
        tabSpec.setIndicator("Products");
        viewSwitch.addTab(tabSpec);

        //Tab 2
        tabSpec = viewSwitch.newTabSpec("Ver Más");
        tabSpec.setContent(R.id.addProducts);
        tabSpec.setIndicator("Ver Más");
        viewSwitch.addTab(tabSpec);


        projectInfoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try
                {
                    postInfo();
                    Toast.makeText(getApplicationContext(),"Actualizado", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();

                }
            }
        });






    }

    public void initData()
    {
        dropdown = (Spinner)findViewById(R.id.productsSpinner);

        dropDownRole = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dropdownItems);

        dropdown.setAdapter(dropDownRole);
    }


    public void prepareData()
    {

        ArrayList<String> planetList = new ArrayList<String>();

        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, planetList);

        for (int i =0; i< products.size(); i++)
        {
            Producto temp = products.get(i);
            String productInfo = temp._Name +"     Price: " + temp._Price + "   Quantity: " +
                    temp._Quantity + "    Comprado: " + temp._Purchased ;


            listAdapter.add(productInfo);


        }

        ListView listView = (ListView)findViewById(R.id.productsList);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setAdapter(listAdapter);

    }



    public void postInfo()
    {
        JSONObject jsonObjectComment = new JSONObject();
        JSONObject jsonObjectDetail = new JSONObject();
        try
        {
            TextView detailStage = (TextView) findViewById(R.id.detailStage);
            TextView commentText = (TextView) findViewById(R.id.commentText);
            jsonObjectDetail.put("ID_Project", Integer.parseInt(stageInfoJson.getJSONObject(0).getString("id_project")));
            jsonObjectDetail.put("Details", detailStage.getText());

            jsonObjectComment.put("ID_Project", Integer.parseInt(stageInfoJson.getJSONObject(0).getString("id_project")));
            jsonObjectComment.put("Comments", commentText.getText());

            jsonTOsendComment =jsonObjectComment.toString() ;
            jsonTOsendDetail = jsonObjectDetail.toString() ;

            AsyncTaskUpdateComment postComment = new AsyncTaskUpdateComment();
            postComment.execute("init");

            AsyncTaskUpdateDetail postDetail = new AsyncTaskUpdateDetail();
            postDetail.execute("init");

        }
        catch (JSONException e)
        {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
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
                        +"/"+ getString(R.string.productsStage) + stageID  );
                */
                //TODO
                /*****************CAMBIAR ESTO*************************************/
                String serverRequest = "http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com/"
                        + getString(R.string.productsStage) + (stageInfoJson.getJSONObject(0).getString("id_project_stage")) ;
                /*****************************************************************/


                HttpResponse httpResponse = httpclient.execute(new HttpGet(serverRequest ));


                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if(inputStream != null)
                {
                    result = convertInputStreamToString(inputStream);
                    result = result.toString().substring(1, result.toString().length() - 1);

                    result = convertStandardJSONString(result);
                    jsonProducts = new JSONArray(result);


                    for(int i =0; i < jsonProducts.length(); i++)
                    {
                        String stageResult = "";
                        Producto newProduct = new Producto();
                        newProduct._Name= (jsonProducts.getJSONObject(i).getString("name"));
                        newProduct._Quantity = Integer.parseInt(jsonProducts.getJSONObject(i).getString("quantity"));
                        newProduct._Price = Integer.parseInt(jsonProducts.getJSONObject(i).getString("price"));
                        newProduct._Purchased = (jsonProducts.getJSONObject(i).getString("purchased"));

                        products.add(newProduct);
                    }
                }
                else
                    result = "Did not work!";

                try
                {
                    HttpClient httpclientALLPRODUCTS = new DefaultHttpClient();
                    String resultPRODUCTS;

                    // make GET request to the given URL

                /*String serverRequest = (getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                        +"/"+ getString(R.string.allProducts) );
                */
                    //TODO PEDIR EL LINK quE DEBE DE SER
                    /*****************CAMBIAR ESTO*************************************/
                    String serverRequestALLRPRODUCTS = "http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com/"
                            + getString(R.string.allProducts);
                    /*****************************************************************/


                    HttpResponse httpResponseALLPRODUCTS = httpclientALLPRODUCTS.execute(new HttpGet(serverRequestALLRPRODUCTS ));

                    // receive response as inputStream
                    inputStream = httpResponseALLPRODUCTS.getEntity().getContent();

                    // convert inputstream to string
                    if(inputStream != null)
                    {
                        resultPRODUCTS = convertInputStreamToString(inputStream);
                        resultPRODUCTS = resultPRODUCTS.toString().substring(1, resultPRODUCTS.toString().length() - 1);

                        resultPRODUCTS = convertStandardJSONString(resultPRODUCTS);
                        allProductsJSON = new JSONArray(resultPRODUCTS);

                        dropdownItems = new ArrayList<String>();
                        try
                        {
                            for(int j =0; j < allProductsJSON.length() ;j++)
                            {
                                String itemTOAdd = allProductsJSON.getJSONObject(j).getString("name") + "|Precio: ₡" +
                                        allProductsJSON.getJSONObject(j).getString("price");
                                dropdownItems.add(itemTOAdd);
                            }
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    else
                    {
                        resultPRODUCTS = "Did not work!";
                    }





                }
                catch (Exception e)
                {
                    publishProgress(e.toString());
                }




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
                //stageInfo = new JSONArray(result);
                prepareData();
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













    /************************************************** ASYNC TASK TO SEND PROJECT INFORMATION*****************************************/

    private class AsyncTaskUpdateComment extends AsyncTask<String, String, String> {


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
            /*******************/
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;

            try {
                //constants

                ConnectionDataHolder connClass = ConnectionDataHolder.getInstance();
                URL url;

                /* *************** REGISTER CUSTOMER LINK CONNECTION ****************** */

                /**
                 * TODO CAMBIAR LINK
                 */
                /*url = new URL(getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                            + getString(R.string.updateProjectComment));
                           */

                url = new URL( "http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com"
                        + getString(R.string.updateStageComment) );

                Log.i("LINKLINK", url.toString());

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(jsonTOsendComment.getBytes().length);

                Log.i("JSONNComment", jsonTOsendComment);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(jsonTOsendComment.getBytes());
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
            }

            finally {
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

                new SweetAlertDialog(StageActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Completado")
                        .setContentText("Actualizado")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                               // StageActivity.this.finish();
                            }
                        })
                        .show();
            }
            else {
                new SweetAlertDialog(StageActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops")
                        .setContentText("Ah ocurrido un error")
                        .show();
            }

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


    /********************************Update Detail Info******************************************/


    private class AsyncTaskUpdateDetail extends AsyncTask<String, String, String> {


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
            /*******************/
            OutputStream os = null;
            InputStream is = null;
            HttpURLConnection conn = null;

            try {
                //constants

                ConnectionDataHolder connClass = ConnectionDataHolder.getInstance();
                URL url;

                /* *************** ****************** */

                /**
                 * TODO CAMBIAR LINK
                 */
                /*url = new URL(getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                            + getString(R.string.updateProjectDetail));
                           */

                url = new URL( "http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com"
                        + getString(R.string.updateStageDetail) );

                Log.i("LINKLINK22", url.toString());

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /*milliseconds*/);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(jsonTOsendDetail.getBytes().length);

                Log.i("JSONN", jsonTOsendDetail);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(jsonTOsendDetail.getBytes());
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
            }

            finally {
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

                new SweetAlertDialog(StageActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Completado")
                        .setContentText("Actualizado")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                //StageActivity.this.finish();
                            }
                        })
                        .show();
            }
            else {
                new SweetAlertDialog(StageActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops")
                        .setContentText("Ah ocurrido un error")
                        .show();
            }

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }






















}
