using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using MySql.Data.MySqlClient;
using STWebService.Model;

namespace STWebService
{
    public class DBOperation
    {
         public static MySqlConnection sqlCon;  //用于连接数据库  
  
        //将下面的引号之间的内容换成上面记录下的属性中的连接字符串  
         private String ConServerStr = @"server=121.42.12.186;user id=USER;Port=3306;database=stproject;";  
          
        //默认构造函数  
        public DBOperation()  
        {  
            if (sqlCon == null)  
            {  
                sqlCon = new MySqlConnection();  
                sqlCon.ConnectionString = ConServerStr;  
                sqlCon.Open();  
            }  
        }  
           
        //关闭/销毁函数，相当于Close()  
        public void Dispose()  
        {  
            if (sqlCon != null)  
            {  
                sqlCon.Close();  
                sqlCon = null;  
            }  
        }  

        //jin yong yu ce shi
        public List<UserModel>selectAllUser()
        {
            List<UserModel> list = new List<UserModel>();
            string sql = "select * from user";
            MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
            MySqlDataReader reader = cmd.ExecuteReader();
            try
            {
                int i = 0;
                while (reader.Read())
                {
                    list.Add(new UserModel());
                    list.ElementAt(i).user_id = reader[0].ToString();
                    list.ElementAt(i).nickname = reader[1].ToString();
                    list[i].slogan = reader[2].ToString();
                    list[i].avatar = reader[3].ToString();
                    list[i].setDate(reader[4].ToString());
                    list[i].occupation = reader[5].ToString();
                    list[i].gender = reader[6].ToString();
                    list[i].address = reader[7].ToString();
                    list[i].grade = (int)reader[8];
                    list[i].coin = (int)reader[9];
                    list[i].contribution = (int)reader[10];
                    list[i].password = reader[11].ToString();
                    i++;
                }
            }
            catch (Exception)
            {
                
            }
            finally
            {
                reader.Close();
                cmd.Dispose();
            }
            return list;

        }

        public int countUser()
        {
            string sql = "select count(user_id) from user";
            MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
            MySqlDataReader reader = cmd.ExecuteReader();
            try
            {

                if (reader.Read())
                    return Int32.Parse(reader[0].ToString());
                reader.Close();
                cmd.Dispose();
            }
            catch(Exception)
            {
                return -1;
            }
            finally
            {
                reader.Close();
                cmd.Dispose();
            }
            return -1;
        }
        
        public List<string> queryUser(String nickname)
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select * from user where nickname = '" + nickname + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    list.Add(reader[0].ToString());
                    list.Add(reader[1].ToString());
                    list.Add(reader[2].ToString());
                    list.Add(reader[3].ToString());
                    list.Add(reader[4].ToString());
                    list.Add(reader[5].ToString());
                    list.Add(reader[6].ToString());
                    list.Add(reader[7].ToString());
                    list.Add(reader[8].ToString());
                    list.Add(reader[9].ToString());
                    list.Add(reader[10].ToString());
                    list.Add(reader[11].ToString());
                }
                reader.Close();
                cmd.Dispose();
                
            }
            catch (Exception)
            {
                
            }
            return list;
        }

        public bool queryExistUser(String nickname)
        {
            try
            {
                string sql = "exist(select * from user where user.nickname = '" + nickname + ")";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                reader.Read();
                if (reader[0] == "true")
                {
                    reader.Close();
                    cmd.Dispose();
                    return true;
                }
                reader.Close();
                cmd.Dispose();
                return false;
            }
            catch(Exception)
            {

            }
            return false;
        }

        public bool insertUser(String user_id,String password,String nickname)
        {
            try
            {
                string sql = "insert into user(user_id,password,nickname) values(" + "'" + user_id + "','" + password + "','"+ nickname + "')";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();

                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool insertUserSecurity(String user_id)
        {
            try
            {
                string sql = "insert into user_security(user_id) values('" + user_id + "')";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();

                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }
  
        public bool deleteUserSecurity(String user_id)
        {
            try
            {
                string sql = "delete from user_security where user_id ='" + user_id + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();

                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        public bool deleteUser(String user_id)
        {
            try
            {
                string sql = "delete from user where user_id ='" + user_id + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();

                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }

        // meixie
        public bool updateUserSecurity(UserSecurityModel model)
        {
            return true;
        }

        // meixie (Bu Bao Han Tou Xiang) nickname bu neng gai
        public bool updateUserBasicInfo(UserModel model)
        {
            try
            {
                string sql = "update USER set password = '" + model.password + "',slogan = '" + model.slogan + 
                    "',birthday = '" + model.birthday.Date.ToString() +"',occupation = '" + model.occupation +
                    "',gender = '" + model.gender + "',address = '" + model.address + "' where user_id = '" + 
                    model.user_id + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch(Exception)
            {

            }
            return false;
        }

        // meixie (geng xin tou xiang)
        public bool updateUserAvatar(String str)
        {
            return true;
        }
    }  

}
