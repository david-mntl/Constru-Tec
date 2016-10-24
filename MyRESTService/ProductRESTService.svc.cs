using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using Newtonsoft.Json;
using Npgsql;
using MyRESTService.ServiceReference1;
using System.Net.Http;
using System.Net;
using System.Net.Http.Headers;
using System.Text;
using System.Web.Script.Serialization;

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


        public string GetAll()
        {
            string msg = "todo bien";
            return msg;
        }



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

        public string PostTest(string str)
        {
            string msg = str;
            return msg;
        }

        public string GetCustomer(string Username)
        {
            string query = "SELECT * from get_customer('" + Username + "')";
            string msg = "";
            try
            {
                this.connect();
                /*                NpgsqlCommand sqlcmd = new NpgsqlCommand(query, conn);
                                NpgsqlDataReader dr = sqlcmd.ExecuteReader();

                                // Output rows
                                while (dr.Read())
                                    msg+= dr[0]+"\t"+dr[1]+"\t";*/
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

        public string CompleteStage(Stage str)
        {
            string query = "SELECT complete_stage(@ID_Project)";
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

        public string PostStageDetails(Stage str)
        {
            string query = "SELECT add_details_to_stage(@ID_Project, @Details)";
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

        /*
        * -------------------------------------------------------------------------------------
        *                                       GETs 
        * -------------------------------------------------------------------------------------  
        */

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

        /*
        * -------------------------------------------------------------------------------------
        *                          Llamadas al web service de EPATEC
        * -------------------------------------------------------------------------------------  
        */
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
                        data = "{\"DataG\":\"fasm22:Cartago," + list[i].ID_Product + ":" + list[i].Quantity + ":" + list[i].Price + "\"}";
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


        public string PostStageShop(Stage st)
        {
            string Out = String.Empty;

            string query = "SELECT * from get_products_from_stage(" + st.ID_Stage + ");";
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
                    msg = "{\"DataG\":\"fasm22:Cartago," + array[i].ID_Product + ":" + array[i].Quantity + ":" + array[i].Price + "\"}";
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
                }
                msg = "ok";
            }
            catch (Exception ex)
            {
                msg += "Error:";
                msg += ex.Message;
            }
            return msg;
        }


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
                    sqlcmd.Parameters.AddWithValue("@ID_Stage", list[i].ID_Stage);
                    sqlcmd.Parameters.AddWithValue("@ID_Product", list[i].ID_Product);
                    sqlcmd.Parameters.AddWithValue("@Quantity", list[i].Quantity);
                    sqlcmd.Parameters.AddWithValue("@Price", list[i].Price);
                    sqlcmd.ExecuteNonQuery();
                    msg = "Ok";
                    this.disconnect();
                }
                catch (Exception ex)
                {
                    msg += "Error:";
                    msg += ex.Message;
                }
            }
            return msg;
        }




        





    }
}








