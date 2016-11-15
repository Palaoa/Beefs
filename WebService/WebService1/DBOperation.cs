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
            try
            {
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
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
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception)
            {
                
            }
            return list;

        }

        public int countUser()
        {
            string sql = "select count(user_id) from user";
            int result = -1;
            try
            {
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    result = Int32.Parse(reader[0].ToString()); 
                }
                reader.Close();
                cmd.Dispose();
            }
            catch(Exception)
            {
                
            }
            return result;
        }

        public int countStory(String user_id)
        {
            string sql = "select count(story_id) from story where user_id = '" + user_id + "'";
            int result = -1;
            try
            {
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    result = Int32.Parse(reader[0].ToString());
                }
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception)
            {

            }
            return result;
        }

        public int countHouse(String user_id)
        {
            string sql = "select count(house_id) from house where user_id = '" + user_id + "'";
            int result = -1;
            try
            {
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                if (reader.Read())
                {
                    result = Int32.Parse(reader[0].ToString());
                }
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception)
            {

            }
            return result;
        }
        
        public List<string> queryUser(String nickname)
        {
            List<string> list = new List<string>();
            try
            {
                string sql = "select * from user where nickname = '" + nickname + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                if (reader.HasRows)
                {
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
            bool result = false;
            try
            {
                string sql = "exist(select * from user where user.nickname = '" + nickname + ")";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                reader.Read();
                if (reader.HasRows && reader[0] == "true")
                {
                    result = true;
                }
                reader.Close();
                cmd.Dispose();
                
            }
            catch(Exception)
            {

            }
            return result;
        }

        public bool queryExistStory(String st_id)
        {
            bool result = false;
            try
            {
                string sql = "exist(select * from story where story_id = '" + st_id + ")";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                reader.Read();
                if (reader.HasRows && reader[0] == "true")
                {
                    result = true;
                }
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception)
            {

            }
            finally
            {
                
                
            }
            return result;
        }

        public List<Model.StoryModel> queryStory(String user_id)
        {
            List<Model.StoryModel> list = new List<Model.StoryModel>();
            try
            {
                string sql = "select story_id,title,content,state,mshow,edittime from story where user_id = '" 
                    + user_id + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                Model.StoryModel sm = null;
                if(reader.HasRows)
                {
                    while (reader.Read())
                    {
                        sm = new StoryModel();
                        sm.story_id = reader[0].ToString();
                        sm.title = reader[1].ToString();
                        sm.content = reader[2].ToString();
                        sm.state = reader[3].ToString().ElementAt(0);
                        sm.mshow = reader[4].ToString().ElementAt(0);
                        string date = reader[5].ToString();
                        sm.editTime = DateTime.Parse(date);
                        list.Add(sm);
                    }
                }
                reader.Close();
                cmd.Dispose();

            }
            catch (Exception e)
            {

            }
            return list;
        }

        public List<Model.HouseModel> queryHouse(String user_id)
        {
            List<Model.HouseModel> list = new List<Model.HouseModel>();
            try
            {
                string sql = "select house_id,city_id,address,limitation,info,state from house where user_id = '" + user_id + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                Model.HouseModel hm = null;
                if (reader.HasRows)
                {
                    while (reader.Read())
                    {
                        hm = new HouseModel();
                        hm.house_id = reader[0].ToString();
                        hm.city_id = reader[1].ToString();
                        hm.address = reader[2].ToString();
                        hm.limitation = reader[3].ToString();
                        hm.info = reader[4].ToString();
                        hm.state = reader[5].ToString().ElementAt(0);
                        hm.user_id = user_id;
                        list.Add(hm);
                    }
                }
                reader.Close();
                cmd.Dispose();

            }
            catch (Exception e)
            {

            }
            return list;
        }

        public bool queryExistUserStory(String user_id, String st_id, String password)
        {
            bool result = false;
            try
            {
                string sql = "select story.story_id from story,user where story_id = '" + st_id + 
                    "' and story.user_id = user.user_id = '" + user_id + "' and user.password = '" + password + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                reader.Read();
                if (reader.HasRows)
                {
                    result = true;
                }
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception e)
            {

            }
            return result;
        }

        public bool queryExistUserHouse(String user_id, String house_id, String password)
        {
            bool result = false;
            try
            {
                string sql = "select house.house_id from house,user where house_id = '" + house_id +
                    "' and house.user_id = user.user_id = '" + user_id + "' and user.password = '" + password + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                reader.Read();
                if (reader.HasRows)
                {
                    result = true;
                }
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception e)
            {

            }
            return result;
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

        public bool insertStory(StoryModel sm)
        {
            try
            {
                string sql = "insert into story (story_id,user_id,title,content,edittime) values('" + sm.story_id + "','" + sm.user_id + "','" 
                    + sm.title + "','" + sm.content + "','" + DateTime.Now.ToString() +"')";
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

        public bool insertHouse(HouseModel hm)
        {
            try
            {
                string sql = "insert into house (house_id,user_id,city_id,address,limitation,info) values('" + hm.house_id + "','" + hm.user_id + "','"
                    + hm.city_id + "','" + hm.address + "','" + hm.limitation + "','" + hm.info + "')";
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

        public bool queryExistUser(String user_id,String password)
        {
            bool result = false;
            try
            {
                string sql = "select * from user where user_id = '" + user_id + "' and password = '" + password +"';";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                MySqlDataReader reader = cmd.ExecuteReader();
                reader.Read();
                if (reader[0] != null)
                {
                    result = true;
                }
                reader.Close();
                cmd.Dispose();
            }
            catch (Exception e)
            {
                
            }
            return result;
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

        public bool updateStory(StoryModel sm)
        {
            try
            {
                string sql = "update story set title = '" + sm.title + "',content = '" + sm.content + "',edittime = '" 
                    + DateTime.Now.ToString() + "' where story_id = '" + sm.story_id + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception e)
            {

            }
            return false;
        }

        public bool updateStoryState(StoryModel sm)
        {
            try
            {
                string sql = "update story set state = '" + sm.state + "',mshow = '" + sm.mshow + "' where story_id = '" + sm.story_id + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception e)
            {

            }
            return false;
        }

        public bool updateHouse(HouseModel hm)
        {
            try
            {
                string sql = "update house set city_id = '" + hm.city_id + "',address = '" + hm.address + "',limitation = '"
                    + hm.limitation + "',info = '" +hm.info + "' where house_id = '" + hm.house_id + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception e)
            {
                
            }
            return false;
        }

        public bool updateHouseState(String house_id, String state)
        {
            try
            {
                string sql = "update house set state = '" + state + "'where house_id = '" + house_id + "'";
                MySqlCommand cmd = new MySqlCommand(sql, sqlCon);
                cmd.ExecuteNonQuery();
                cmd.Dispose();
                return true;
            }
            catch (Exception e)
            {

            }
            return false;
        }
    }  

}
