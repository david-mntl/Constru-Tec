package com.epatec.construtecmovil;

import java.util.ArrayList;

/**
 * Created by David on 22/10/2016.
 */
public class Projects {

    private String project_ID = "";
    private String project_Name = "";

    ArrayList<String> stages_Array;

    public Projects()
    {
        stages_Array = new ArrayList<>();
    }

    public void insertStage(String pStage)
    {
        stages_Array.add(pStage);
    }
    public ArrayList<String> getStages()
    {
        return stages_Array;
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
