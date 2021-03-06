package com.epatec.construtecmovil;

import java.util.ArrayList;

/**
 * Created by David on 22/10/2016.
 */
public class Projects {

    public String project_ID = "";
    public String project_Name = "";
    public String start_Date = "";
    public String end_Date = "";
    public String comments = "";
    public String location = "";
    public String details = "";
    public String completed = "";

    ArrayList<String> stagesNames_Array;

    ArrayList<Stages> stages_Array;



    public Projects()
    {
        stagesNames_Array = new ArrayList<>();
        stages_Array = new ArrayList<Stages>();
    }

    public void addStagesToArray(Stages pStage)
    {
        stages_Array.add(pStage);
    }

    public ArrayList<Stages> getStages_Array() {
        return stages_Array;
    }

    public void setStages_Array(ArrayList<Stages> stages_Array) {
        this.stages_Array = stages_Array;
    }

    public void insertStage(String pStage)
    {
        stagesNames_Array.add(pStage);
    }
    public ArrayList<String> getStages()
    {
        return stagesNames_Array;
    }


    public String getProjectID ()
    {
        return project_ID;
    }

    public String getProject_Name ()
    {
        return project_Name;
    }

    public void setProjectID (String pID)
    {
         project_ID = pID;
    }

    public void setProject_Name (String pName)
    {
        project_Name = pName;
    }
}
