using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using Newtonsoft.Json;
using Npgsql;
//using MyRESTService.ServiceReference1;
//using System.Net.Http;
using System.Net;
//using System.Net.Http.Headers;
using System.Text;
//using System.Web.Script.Serialization;

namespace MyRESTService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "ProductRESTService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select ProductRESTService.svc or ProductRESTService.svc.cs at the Solution Explorer and start debugging.
    public class ProductRESTService : IProductRESTService
    {

        private NpgsqlConnection conn;

        public ProductRESTService()
        {
            conn = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["MyConnection"].ConnectionString);
        }

        /// <summary>
        /// All methods connect to some kind of NpgsqlConnection
        /// That's why this action is factorized
        /// </summary>
        /// <return> string the information of the sattus 
        public string connect()
        {
            try
            {
                conn.Open();
                conn.CreateCommand();
                return "ok";
            }
            catch (Exception ee)
            {
                return ee.Message;
            }
        }

        /// <summary>
        /// All methods disconect to some kind of NpgsqlConnection
        /// That's why this action is factorized 
        /// </summary>
        /// <returns> string with information of the status
        public string disconnect()
        {
            try
            {
                conn.Close();
                return "ok";
            }
            catch (Exception ee)
            {
                return ee.Message;
            }
        }


        /// <summary>
        /// To test the connection
        /// </summary>
        /// <returns> string with a standart information
        public string GetAll()
        {
            string msg = "todo bien";
            return msg;
        }

        /// <summary>
        /// To the post information
        /// </summary>
        /// <param name="str"> an string with any information</param>
        /// <returns>returns the same information</returns>
        public string PostTest(string str)
        {
            string msg = str;
            return msg;
        }

        /// <summary>
        /// To create a new Customer
        /// </summary>
        /// <param name="str">This objects contains all the necessary information to create a new customer, it is
        /// different from a engineer</param>
        /// <returns> string contains an "Ok" or an "Error:info" according to the query result</returns>
        public string PostCustomer(Customer str)
        {
            string query = "SELECT add_customer(@ID_Customer,@Name,@Lastname_1,@Lastname_2, @Phone,@Email,@Username,@Password)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Customer", str.ID_Customer);
                sqlcmd.Parameters.AddWithValue("@Name", str.Name);
                sqlcmd.Parameters.AddWithValue("@Lastname_1", str.Lastname_1);
                sqlcmd.Parameters.AddWithValue("@Lastname_2", str.Lastname_2);
                sqlcmd.Parameters.AddWithValue("@Phone", str.Phone);
                sqlcmd.Parameters.AddWithValue("@Email", str.Email);
                sqlcmd.Parameters.AddWithValue("@Username", str.Username);
                sqlcmd.Parameters.AddWithValue("@Password", str.Password);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }



        /// <summary>
        /// In case we need information about a single Customer, different from an engineer
        /// </summary>
        /// <param name="Username">Object with the user id to retrieve data</param>
        /// <returns>OK if the query is fine</returns>
        public string GetCustomer(string Username)
        {
            string query = "SELECT * from get_customer('" + Username + "')";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// In case we need information about a single engineer, different from an customer
        /// </summary>
        /// <param name="Username">Object with the user id to retrieve data</param>
        /// <returns>OK if the query is fine</returns>
        public string GetEngineer(string Username)
        {
            string query = "SELECT * from get_engineer('" + Username + "')";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Some of the information might be updated
        /// </summary>
        /// <param name="str">an object with the new information to update in a query</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string UpdateCustomer(Customer str)
        {
            string query = "SELECT update_customer(@ID_Customer,@Name,@Lastname_1,@Lastname_2, @Phone,@Email,@Username,@Password)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Customer", str.ID_Customer);
                sqlcmd.Parameters.AddWithValue("@Name", str.Name);
                sqlcmd.Parameters.AddWithValue("@Lastname_1", str.Lastname_1);
                sqlcmd.Parameters.AddWithValue("@Lastname_2", str.Lastname_2);
                sqlcmd.Parameters.AddWithValue("@Phone", str.Phone);
                sqlcmd.Parameters.AddWithValue("@Email", str.Email);
                sqlcmd.Parameters.AddWithValue("@Username", str.Username);
                sqlcmd.Parameters.AddWithValue("@Password", str.Password);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Acording to the id in the object reviced, the user is deleted
        /// In the database it is very likely that the user it is not deleted but 
        /// updated the status of active
        /// </summary>
        /// <param name="str">object with the user id</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string DeleteCustomer(Customer str)
        {
            string query = "SELECT delete_customer(@ID_Customer)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Customer", str.ID_Customer);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="str"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string VerifyLogin(Customer str)
        {
            string query = "SELECT login(@Username,@Password)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@Username", str.Username);
                sqlcmd.Parameters.AddWithValue("@Password", str.Password);
                sqlcmd.ExecuteNonQuery();

                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);

            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="str"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string PostEngineer(Engineer str)
        {
            string query = "SELECT add_engineer(@ID_Engineer,@Name,@Lastname_1,@Lastname_2, @Phone,@Email,@Eng_Code,@Username,@Password,@Role)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Engineer", str.ID_Engineer);
                sqlcmd.Parameters.AddWithValue("@Name", str.Name);
                sqlcmd.Parameters.AddWithValue("@Lastname_1", str.Lastname_1);
                sqlcmd.Parameters.AddWithValue("@Lastname_2", str.Lastname_2);
                sqlcmd.Parameters.AddWithValue("@Phone", str.Phone);
                sqlcmd.Parameters.AddWithValue("@Email", str.Email);
                sqlcmd.Parameters.AddWithValue("@Eng_Code", str.Eng_Code);
                sqlcmd.Parameters.AddWithValue("@Username", str.Username);
                sqlcmd.Parameters.AddWithValue("@Password", str.Password);
                sqlcmd.Parameters.AddWithValue("@Role", str.Role);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// To update an engineer we post all the new information
        /// </summary>
        /// <param name="str">object with the new information</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string UpdateEngineer(Engineer str)
        {
            string query = "SELECT update_engineer(@ID_Engineer,@Name,@Lastname_1,@Lastname_2, @Phone,@Email,@Eng_Code,@Username,@Password)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Engineer", str.ID_Engineer);
                sqlcmd.Parameters.AddWithValue("@Name", str.Name);
                sqlcmd.Parameters.AddWithValue("@Lastname_1", str.Lastname_1);
                sqlcmd.Parameters.AddWithValue("@Lastname_2", str.Lastname_2);
                sqlcmd.Parameters.AddWithValue("@Phone", str.Phone);
                sqlcmd.Parameters.AddWithValue("@Email", str.Email);
                sqlcmd.Parameters.AddWithValue("@Eng_Code", str.Eng_Code);
                sqlcmd.Parameters.AddWithValue("@Username", str.Username);
                sqlcmd.Parameters.AddWithValue("@Password", str.Password);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Change the active status to non-active
        /// </summary>
        /// <param name="str">object with the engineer id</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string DeleteEngineer(Engineer str)
        {
            string query = "SELECT delete_engineer(@ID_Engineer)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Engineer", str.ID_Engineer);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Return 
        /// </summary>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetRoles()
        {
            string query = "SELECT get_roles()";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// To add a new Role, just in the table
        /// </summary>
        /// <param name="str">Name of the new role</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string AddNewRole(Role str)
        {
            string query = "SELECT add_new_role(@Name)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@Name", str.Name);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error in New Role Function:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// This creates an relation among enginer and a Rol
        /// </summary>
        /// <param name="str">ID of the engineer and the role name</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string AddNewRoleToEngineer(Engineer str)
        {
            string query = "SELECT add_role_to_engineer(@ID_Engineer,@Role)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Engineer", str.ID_Engineer);
                sqlcmd.Parameters.AddWithValue("@Role", str.Role);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// To post a new 
        /// </summary>
        /// <param name="str"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string PostProject(Project str)
        {
            string query = "SELECT add_project(@Name,@Location,@ID_Engineer, @ID_Customer,@Comments,@Details)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@Name", str.Name);
                sqlcmd.Parameters.AddWithValue("@Location", str.Location);
                sqlcmd.Parameters.AddWithValue("@ID_Engineer", str.ID_Engineer);
                sqlcmd.Parameters.AddWithValue("@ID_Customer", str.ID_Customer);
                sqlcmd.Parameters.AddWithValue("@Comments", str.Comments);
                sqlcmd.Parameters.AddWithValue("@Details", str.Details);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Updates the comments of the project
        /// </summary>
        /// <param name="str">id of the project</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string PostProjectComments(Project str)
        {
            string query = "SELECT add_comment_to_project(@ID_Project,@Comments)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Project", str.ID_Project);
                sqlcmd.Parameters.AddWithValue("@Comments", str.Comments);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// This updates the details of the project
        /// </summary>
        /// <param name="str">id of the project</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string PostProjectDetails(Project str)
        {
            string query = "SELECT add_details_to_project(@ID_Project,@Details)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Project", str.ID_Project);
                sqlcmd.Parameters.AddWithValue("@Details", str.Details);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Deletes the comments in the project
        /// </summary>
        /// <param name="str"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string PostProjectCommentDel(Project str)
        {
            string query = "SELECT delete_comment_from_project(@ID_Project)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Project", str.ID_Project);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Marks as completed the project
        /// </summary>
        /// <param name="str">id project</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string CompleteProject(Project str)
        {
            string query = "SELECT complete_project(@ID_Project)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Project", str.ID_Project);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// This does not deletes the project, just changes the active state of the project
        /// </summary>
        /// <param name="str">project id</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string DeleteProject(Project str)
        {
            string query = "SELECT delete_project(@ID_Project)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Project", str.ID_Project);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// To create a new stage, we need to asign its project with an ID
        /// This is not the method to assign a new stage, this just creates a new name for a stage
        /// </summary>
        /// <param name="str">contains the satege name</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string AddStageName(Stage str)
        {
            string query = "SELECT add_new_stage_name(@Stage_Name)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@Stage_Name", str.Stage_Name);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Marks as completed the stage
        /// </summary>
        /// <param name="str">Id of the project</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string CompleteStage(Stage str)
        {
            string query = "SELECT complete_stage(@ID_Stage)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Stage", str.ID_Stage);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// It is possible to create a stage 
        /// </summary>
        /// <param name="str"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string CreateStage(Stage str)
        {
            string query = "SELECT add_project_stage(@ID_Project, @Stage_Name, @Start_Date, @End_Date, @Details, @Comments)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Project", str.ID_Project);
                sqlcmd.Parameters.AddWithValue("@Stage_Name", str.Stage_Name);
                sqlcmd.Parameters.AddWithValue("@Start_Date", str.Start_Date);
                sqlcmd.Parameters.AddWithValue("@End_Date", str.End_Date);
                sqlcmd.Parameters.AddWithValue("@Details", str.Details);
                sqlcmd.Parameters.AddWithValue("@Comments", str.Comments);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Add comments to any stage 
        /// </summary>
        /// <param name="str">the object contains id and comments</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string PostStageComments(Stage str)
        {
            string query = "SELECT add_comments_to_stage(@ID_Project, @Comments)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Project", str.ID_Project);
                sqlcmd.Parameters.AddWithValue("@Comments", str.Comments);
                sqlcmd.ExecuteNonQuery();
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// 
        /// </summary>
        /// <param name="str"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string PostStageDetails(Stage str)
        {
            string query = "SELECT add_details_to_stage(@ID_Stage, @Details)";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                sqlcmd.Parameters.AddWithValue("@ID_Stage", str.ID_Stage);
                sqlcmd.Parameters.AddWithValue("@Details", str.Details);
                sqlcmd.ExecuteNonQuery();
                msg = "ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /*
        * -------------------------------------------------------------------------------------
        *                                       GETs 
        * -------------------------------------------------------------------------------------  
        */

        /// <summary>
        /// all the stages from a project
        /// </summary>
        /// <param name="projectID"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetStagesFromProject(string projectID)
        {
            string query = "SELECT * from get_stages_from_project('" + projectID + "')";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// return all the stages 
        /// </summary>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetStagesName()
        {
            string query = "SELECT * from stage_name";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }


        /// <summary>
        /// select all the projects
        /// </summary>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetProjects()
        {
            string query = "SELECT * from project;";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// projects from a customer or a engineer
        /// </summary>
        /// <param name="status"></param>
        /// <param name="id"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetProjectsFrom(string status, string id)
        {
            string query = "SELECT * from get_projects_from_generic(" + status + "," + id + ");";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Get information from a stage
        /// </summary>
        /// <param name="ID"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetInfoFromStage(string ID)
        {
            string query = "SELECT * from get_info_from_stage(" + ID + ");";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }


        /// <summary>
        /// All the products from the stage
        /// </summary>
        /// <param name="ID"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetProductsFromStage(string ID)
        {
            string query = "SELECT * from get_products_from_stage(" + ID + ");";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// The project from the next weeks
        /// </summary>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetProjectsNextNeeks()
        {
            string query = "SELECT * from get_projects_next_weeks()";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Obtain products from a project
        /// </summary>
        /// <param name="id"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string get_products_info_from_project(string id)
        {
            string query = "SELECT * from get_products_info_from_project(" + id + ")";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// Obtains the stages with a given product, to later obtain
        /// the projects in the next 15 days
        /// </summary>
        /// <param name="name"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetProjectsByMaterial(string name)
        {
            string query = "SELECT * from get_projects_by_material(" + name + ")";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }

        /// <summary>
        /// REturns  a list with all the products in construtec
        /// </summary>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetProductList()
        {
            string query = "select * from get_products()";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }


        /// <summary>
        /// Returns all the info from a Customer
        /// </summary>
        /// <param name="id"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string GetUserInfo(string id)
        {
            string query = "SELECT * from get_contact_info_from_customer("+id+")";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            finally
            {
                this.disconnect();
            }
            return msg;
        }


        /*
        * -------------------------------------------------------------------------------------
        *                          Llamadas al web service de EPATEC
        * -------------------------------------------------------------------------------------  
        */
        /// <summary>
        /// obtains all the products from EPATEC
        /// </summary>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string EpatecGetProductList()
        {
            string Out = "";
            string msg = "";
            System.Net.WebRequest req = System.Net.WebRequest.Create("http://cewebserver.azurewebsites.net/Service1.svc/GetProducts?params=all");
            try
            {
                System.Net.WebResponse resp = req.GetResponse();
                using (System.IO.Stream stream = resp.GetResponseStream())
                {
                    using (System.IO.StreamReader sr = new System.IO.StreamReader(stream))
                    {
                        Out = sr.ReadToEnd();
                        sr.Close();
                        Out = Out.Substring(2, Out.Length - 4);
                        Out = Out.Replace("},", "}#");
                        string[] infoArray = Out.Split('#');
                        for (int i = 0; i < infoArray.Length; i++)
                        {
                            string itemstr = infoArray[i].Replace("\"", "").Replace("{", "").Replace("}", "").Replace("\\", "");
                            string[] itemData = itemstr.Split(',');
                            string query = "select add_product(@ID_Product,@Details,@Active,@Name,@Price)";
                            try
                            {
                                this.connect();
                                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                                sqlcmd.Parameters.AddWithValue("@ID_Product", itemData[0].Split(':')[1]);
                                sqlcmd.Parameters.AddWithValue("@Details", itemData[1].Split(':')[1]);
                                sqlcmd.Parameters.AddWithValue("@Active", itemData[7].Split(':')[1]);
                                sqlcmd.Parameters.AddWithValue("@Name", itemData[8].Split(':')[1]);
                                sqlcmd.Parameters.AddWithValue("@Price", itemData[3].Split(':')[1]);
                                sqlcmd.ExecuteNonQuery();
                                this.disconnect();
                            }
                            catch (Exception ex)
                            {
                                msg += "Error:";
                                msg += ex.Message;
                            }
                        }
                    }
                }
                msg = "ok";
            }
            catch (ArgumentException ex)
            {
                msg = string.Format("HTTP_ERROR :: The second HttpWebRequest object has raised an Argument Exception as 'Connection' Property is set to 'Close' :: {0}", ex.Message);
            }
            catch (WebException ex)
            {
                msg = string.Format("HTTP_ERROR :: WebException raised! :: {0}", ex.Message);
            }
            catch (Exception ex)
            {
                msg = string.Format("HTTP_ERROR :: Exception raised! :: {0}", ex.Message);
            }
            return msg;
        }

        /// <summary>
        /// send to buy the stage
        /// </summary>
        /// <param name="list"></param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string EpatecPostShop(List<Products> list)
        {
            string Out = String.Empty;
            string data = "";
            for (var i = 0; i < list.Count; i++)
            {
                {
                    
                    System.Net.WebRequest req = System.Net.WebRequest.Create("http://cewebserver.azurewebsites.net/Service1.svc/PostShop");
                    try
                    {
                        req.Method = "POST";
                        req.Timeout = 100000;
                        req.ContentType = "application/json";
                        data = "{\"DataG\":\"fasm22:Cartago," + list[i].id_product + ":" + list[i].quantity + ":" + list[i].price + "\"}";
                        byte[] sentData = Encoding.UTF8.GetBytes(data);
                        req.ContentLength = sentData.Length;
                        using (System.IO.Stream sendStream = req.GetRequestStream())
                        {
                            sendStream.Write(sentData, 0, sentData.Length);
                            sendStream.Close();
                        }
                        System.Net.WebResponse res = req.GetResponse();
                        System.IO.Stream ReceiveStream = res.GetResponseStream();
                        using (System.IO.StreamReader sr = new System.IO.StreamReader(ReceiveStream, Encoding.UTF8))
                        {
                            Char[] read = new Char[256];
                            int count = sr.Read(read, 0, 256);

                            while (count > 0)
                            {
                                String stri = new String(read, 0, count);
                                Out += stri;
                                count = sr.Read(read, 0, 256);
                            }
                        }
                    }
                    catch (ArgumentException ex)
                    {
                        Out = string.Format("HTTP_ERROR :: The second HttpWebRequest object has raised an Argument Exception as 'Connection' Property is set to 'Close' :: {0}", ex.Message);
                    }
                    catch (WebException ex)
                    {
                        Out = string.Format("HTTP_ERROR :: WebException raised! :: {0}", ex.Message);
                    }
                    catch (Exception ex)
                    {
                        Out = string.Format("HTTP_ERROR :: Exception raised! :: {0}", ex.Message);
                    }
                }
                //Console.WriteLine("Amount is {0} and type is {1}", str[i], str[i]);
            }


            return Out;
        }


        /// <summary>
        /// Create a shop in epatec
        /// </summary>
        /// <param name="st">id of the stage</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string PostStageShop(Products st)
        {
            string Out = String.Empty;

            string query = "SELECT * from get_products_from_stage(" + st.id_stage + ");";
            string msg = "";
            try
            {
                this.connect();
                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                NpgsqlDataAdapter sda = new NpgsqlDataAdapter(sqlcmd);
                DataSet dt = new DataSet();
                sda.Fill(dt);
                string result1 = JsonConvert.SerializeObject(dt.Tables);
                msg = result1.Remove(result1.Length - 1).Remove(0, 1);
                this.disconnect();

                //---------------------------------------Ir a comprar a epatec---------------------------------------------------------
                var array = JsonConvert.DeserializeObject<List<Products>>(msg);
                for(var i=0;i<array.Count;i++) 
                {
                    System.Net.WebRequest req = System.Net.WebRequest.Create("http://cewebserver.azurewebsites.net/Service1.svc/PostShop");
                    req.Method = "POST";
                    req.Timeout = 100000;
                    req.ContentType = "application/json";
                    msg = "{\"DataG\":\"fasm22:Cartago," + array[i].id_product + ":" + array[i].quantity + ":" + array[i].price + "\"}";
                    byte[] sentData = Encoding.UTF8.GetBytes(msg);
                    req.ContentLength = sentData.Length;
                    using (System.IO.Stream sendStream = req.GetRequestStream())
                    {
                        sendStream.Write(sentData, 0, sentData.Length);
                        sendStream.Close();
                    }
                    System.Net.WebResponse res = req.GetResponse();
                    System.IO.Stream ReceiveStream = res.GetResponseStream();
                    using (System.IO.StreamReader sr = new System.IO.StreamReader(ReceiveStream, Encoding.UTF8))
                    {
                        Char[] read = new Char[256];
                        int count = sr.Read(read, 0, 256);

                        while (count > 0)
                        {
                            String stri = new String(read, 0, count);
                            Out += stri;
                            count = sr.Read(read, 0, 256);
                        }
                    }
                    string query2 = "SELECT * from buy_product_from_stage(" + array[i].id_stage + ","+ array[i].id_product + ");";
                    this.connect();
                    NpgsqlCommand sqlcmd2 = new NpgsqlCommand(query2, conn);
                    NpgsqlDataAdapter sda2 = new NpgsqlDataAdapter(sqlcmd2);
                    DataSet dt2 = new DataSet();
                    sda2.Fill(dt2);
                    string result2 = JsonConvert.SerializeObject(dt2.Tables);
                    msg = result2.Remove(result2.Length - 1).Remove(0, 1);
                    this.disconnect();
                }
                msg = "Ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            return msg;
        }


        /// <summary>
        /// Add a product to the stage
        /// </summary>
        /// <param name="list">input object</param>
        /// <returns>OK if the query it's fine, otherwise returns the respective information</returns>
        public string addProductToStage(List<Products> list)
        {
            string msg = "";
            for (var i = 0; i < list.Count; i++)
            {
                string query = "SELECT add_product_to_stage(@ID_Stage, @ID_Product, @Quantity, @Price)";
                try
                {
                    this.connect();
                    NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                    sqlcmd.Parameters.AddWithValue("@ID_Stage", list[i].id_stage);
                    sqlcmd.Parameters.AddWithValue("@ID_Product", list[i].id_product);
                    sqlcmd.Parameters.AddWithValue("@Quantity", list[i].quantity);
                    sqlcmd.Parameters.AddWithValue("@Price", list[i].price);
                    sqlcmd.ExecuteNonQuery();
                    
                    this.disconnect();
                }
                catch (Exception ex)
                {
                    msg += "Error:";
                    msg += ex.Message;
                }
            }
            msg = "ok";
            return msg;
        }




        




    }
}








