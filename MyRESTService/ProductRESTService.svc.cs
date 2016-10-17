using System;
using System.Collections.Generic;
using System.Configuration;
using Npgsql;

namespace MyRESTService
{
    // NOTE: You can use the "Rename" command on the "Refactor" menu to change the class name "ProductRESTService" in code, svc and config file together.
    // NOTE: In order to launch WCF Test Client for testing this service, please select ProductRESTService.svc or ProductRESTService.svc.cs at the Solution Explorer and start debugging.
    public class ProductRESTService : IProductRESTService
    {

        private NpgsqlConnection conn;

        public ProductRESTService() {
            conn = new NpgsqlConnection(ConfigurationManager.ConnectionStrings["MyConnection"].ConnectionString);
        }

        public bool connect() {
            try {
                conn.Open();
                conn.CreateCommand();
                return true;
            } catch (Exception ee) {
                return false;
            }
        }

        public bool disconnect() {
            try{
                conn.Close();
                return true;
            }catch (Exception ee){
                return false;
            }
        }


        public List<Product> GetProductList()
        {
            return Products.Instance.ProductList;
        }




        public string GetAll()
        {
            string msg = "todo bien";
            return msg;
        }



        public string PostCustomer(Customer str){
            string query = "SELECT add_customer(@ID_Customer,@Name,@Lastname_1,@Lastname_2, @Phone,@Email,@Username,@Password)";
            string msg="";
            try {
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
            }catch (Exception ex) {
                msg += "Error:";
                msg += ex.Message;
            }finally{
                this.disconnect();
            }
            return msg;
        }

        public string UpdateCustomer(Customer str)
        {
            string query = "SELECT update_customer(@ID_Customer,@Name,@Lastname_1,@Lastname_2, @Phone,@Email,@Username,@Password)";
            string msg = "";
            try{
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

        public string UpdateEngineer(Engineer str){
            string query = "SELECT update_engineer(@ID_Engineer,@Name,@Lastname_1,@Lastname_2, @Phone,@Email,@Eng_Code,@Username,@Password)";
            string msg = "";
            try{
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
            }catch (Exception ex){
                msg += "Error:";
                msg += ex.Message;
            }finally{
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

        public string PostProjectComment(Project str)
        {
            string query = "SELECT add_comment_to_project(@ID_Project,@Comments)";
            string msg = "";
            try{
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





























    }
}
