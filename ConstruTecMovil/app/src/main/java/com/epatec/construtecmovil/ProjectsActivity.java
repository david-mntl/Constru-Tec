package com.epatec.construtecmovil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

public class ProjectsActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    JSONArray stageInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.ExpandableList);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();

                Intent stageIntent = new Intent(ProjectsActivity.this, StageActivity.class);
                stageIntent.putExtra("childName", listDataHeader.get(groupPosition)
                        + " : "
                        + listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));
                //stageIntent.putExtra("secondKeyName","SecondKeyValue");
                startActivity(stageIntent);


                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }














    /* ASYNC TASK TO GET PROJECT INFORMATION*/
    private class AsyncTaskConnector extends AsyncTask<String, String, String> {


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


        /***************** ON PROGRESS UPDATE **************************/
        /**
         * Method to process the data received froom the server
         * @param progress
         */
        @Override
        protected void onProgressUpdate(String... progress) {


            String result = progress[0].toString().substring(1, progress[0].toString().length() - 1);
            result = convertStandardJSONString(result);

           

            try {
                stageInfo = new JSONArray(result);
                //txt.setText(obj.getJSONObject(0).get("ID_Product").toString());

            }
            catch (JSONException e) {
                Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
            }

            for(int x = 0; x < stageInfo.length(); x++) {

                String pName = "";
                String pIDStage = "";
                String pState = "";

                int currentID = 0;
                try {
                    /**
                     * TO BE DEFINED los tags
                     */
                    pName = stageInfo.getJSONObject(x).getString("stage_name");
                    pIDStage = stageInfo.getJSONObject(x).getString("id_project_stage");
                    pState = stageInfo.getJSONObject(x).getString("completed");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                }

                TextView productNameTxt = new TextView(ProjectsActivity.this);
                productNameTxt.setText(pName);
                TextView productPriceTxt = new TextView(ProjectsActivity.this);
                //productPriceTxt.setText("Precio: " + "₡" + pPrice + " | " + "Stock: " + pStock);


                Button addToCartButton = new Button(ProjectsActivity.this);
                addToCartButton.setText("Añadir");
                addToCartButton.setId(currentID);
                addToCartButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        final TextView texx = (TextView) findViewById(R.id.productsQuantityTxt);
                        final TextView texxTotal = (TextView) findViewById(R.id.totaltxt);
                        try {

                            UserDataHolder holder = UserDataHolder.getInstance();
                            //holder.addProductToShoppingCart(v.getId(), 1, getProductPrice(v.getId()),getProductName(v.getId()));
                            texx.setText("(" + String.valueOf(holder.shoppingcart.size()) + ")");
                            texxTotal.setText(String.valueOf(holder.getTotal()));
                        }catch (Exception e){
                            texx.setText(e.toString());
                        }
                    }
                });

                //linearLayout1.addView(productNameTxt);
                //linearLayout1.addView(productPriceTxt);
                //linearLayout1.addView(addToCartButton);
            }

        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }











}