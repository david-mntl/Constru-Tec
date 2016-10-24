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
import android.util.Log;
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
    ArrayList<Projects> allProjects;
    ArrayList<Stages> allStages;

    String stageJSONString;


    JSONArray stageInfo = null;
    JSONArray projects = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.ExpandableList);

        try
        {
            allStages = new ArrayList<Stages>();
            AsyncTaskProjects projectsServer = new AsyncTaskProjects();
            projectsServer.execute("init");

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }





    }

    private void initData()
    {

        // preparing list data
        //prepareListData();

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


                stageIntent.putExtra("stageInfo", findStage(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)).json);
                stageIntent.putExtra("idStage", findStage(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition)).ID_Stage);

                stageIntent.putExtra("location", findProject(listDataHeader.get(groupPosition)).location);

                startActivity(stageIntent);
                return false;
            }
        });

    };

    public Stages findStage(String pName)
    {
        for(int i = 0; i < allStages.size();i++)
        {
            Stages temp = allStages.get(i);
            if ( temp.Stage_Name.compareTo(pName)==0)
            {
                return temp;
            }
        }

        return null;
    }

    public Projects findProject(String pName)
    {
        for(int i = 0; i < allProjects.size();i++)
        {
            Projects temp = allProjects.get(i);
            if ( temp.project_Name.compareTo(pName)==0)
            {
                return temp;
            }
        }

        return null;
    }





    /*
     * Preparing the list data
     */
    private void prepareListData() {


        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        for (int i=0;i < allProjects.size() ; i++)
        {
            List<String> newHeaderList = new ArrayList<String>();
            listDataHeader.add(allProjects.get(i).getProject_Name());

            ArrayList<String> stagesList =allProjects.get(i).getStages();

            for (int j=0; j < stagesList.size(); j++)
            {
                newHeaderList.add(stagesList.get(j));
            }

            listDataChild.put(listDataHeader.get(i), stagesList); // Header, Child data
        }
        /*
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
        */

        /*List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
        */
    }














    /************************************************** ASYNC TASK TO GET PROJECT INFORMATION*****************************************/
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

            allProjects = new ArrayList<>();


            String result = "";
            ConnectionDataHolder connClass = ConnectionDataHolder.getInstance();
            UserDataHolder user = UserDataHolder.getInstance();

            try {

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL

                /*String serverRequest = (getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection
                        +"/"+ getString(R.string.allProjects) + user.userType + getString(R.string.projectUserID) + user.userID   );
                */
                //TODO
                /*****************CAMBIAR ESTO*************************************/
                String serverRequest = "http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com/"
                        + getString(R.string.allProjects) + user.userType + getString(R.string.projectUserID) + user.userID;
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
                    projects = new JSONArray(result);
                    Log.i("PROJECT", result);


                    for(int i =0; i < projects.length(); i++)
                    {
                        String stageResult = "";
                        Projects newProject = new Projects();
                        newProject.setProject_Name(projects.getJSONObject(i).getString("project_name"));
                        newProject.setProjectID(projects.getJSONObject(i).getString("id_project"));
                        newProject.location = projects.getJSONObject(i).getString("location");

                        try
                        {
                            HttpClient httpclientStages = new DefaultHttpClient();

                            /*String urlStage = getString(R.string.domain) + connClass.ipConnection + ":" + connClass.portConnection + "/"
                                    + getString(R.string.projectsStages) + newProject.getProjectID();
                            */

                            //TODO
                            /*****************CAMBIAR ESTO*************************************/
                            String urlStage ="http://cewebserver.tyhmn8q9pa.us-west-2.elasticbeanstalk.com/"
                                    + getString(R.string.projectsStages) + newProject.getProjectID();
                            /******************************************************************/



                            // make GET request to the given URL
                            HttpResponse httpResponseStages = httpclientStages.execute(new HttpGet (urlStage));

                            // receive response as inputStream
                            inputStreamStage = httpResponseStages.getEntity().getContent();

                            if (inputStreamStage != null)
                            {

                                stageResult = convertInputStreamToString(inputStreamStage);
                                stageResult = stageResult.toString().substring(1, stageResult.toString().length() - 1);
                                stageResult = convertStandardJSONString(stageResult);
                                stageInfo = new JSONArray(stageResult);





                                for (int j =0; j< stageInfo.length(); j++)
                                {
                                    stageJSONString = stageResult;
                                    Stages newStage = new Stages();

                                    newStage.json = stageInfo.getJSONObject(j).toString();
                                    newStage.Stage_Name = stageInfo.getJSONObject(j).getString("stage_name");
                                    newStage.ID_Stage = stageInfo.getJSONObject(j).getString("id_project_stage");
                                    newStage.ID_Project = stageInfo.getJSONObject(j).getString("id_project");
                                    newStage.comments = stageInfo.getJSONObject(j).getString("comments");
                                    newStage.details = stageInfo.getJSONObject(j).getString("details");
                                    newStage.completed = stageInfo.getJSONObject(j).getString("completed");
                                    newStage.end_Date = stageInfo.getJSONObject(j).getString("end_date");
                                    newStage.start_Date = stageInfo.getJSONObject(j).getString("start_date");


                                    newProject.insertStage(stageInfo.getJSONObject(j).getString("stage_name"));



                                    allStages.add(newStage);

                                }
                            }
                        }

                        catch (Exception e) {
                            publishProgress(e.toString());
                        }
                        allProjects.add(newProject);
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
                //stageInfo = new JSONArray(result);
                prepareListData();

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
}