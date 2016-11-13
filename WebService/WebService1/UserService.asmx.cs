using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace STWebService
{
    /// <summary>
    /// Service1 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消注释以下行。
    [System.Web.Script.Services.ScriptService]
    public class Service1 : System.Web.Services.WebService
    {
        DBOperation dbOperation = new DBOperation();
        [WebMethod]
        public string HelloWorld()
        {
            return "Hello World";
        }

        [WebMethod(Description = "获取所有User的信息")]
        public string[] selectAllUserInfo()
        {
            List<string> list = new List<string>();
            foreach(var i in dbOperation.selectAllUser().ToArray())
            {
                list.Add(i.user_id);
                list.Add(i.nickname);
                list.Add(i.password);
            }
            return list.ToArray();
        }

        //bing wei user_security + 1
        [WebMethod(Description = "增加一条User信息")]
        public bool insertUser(String password,String nickname)
        {
            int count;
            if( !dbOperation.queryExistUser(nickname) )
            {
                count = dbOperation.countUser();
                if (count == -1)
                    return false;
                string id = count.ToString();
                while(id.Length < 5)
                {
                    id = "0" + id;
                }
                id = "U" + id;
                if( dbOperation.insertUser(id, password, nickname) )
                {

                    if (dbOperation.insertUserSecurity(id))
                        return true;
                    else
                    {
                        dbOperation.deleteUser(id);
                        return false;
                    }
                }
                
            }
            return false;    
        }

        [WebMethod(Description = "Add a story")]
        public bool insertStory(String user_id, String password, String title, String content)
        {
            int count = 0;
            Model.StoryModel sm = new Model.StoryModel();
            if(dbOperation.queryExistUser(user_id,password))
            {
                count = dbOperation.countStory(user_id);
                if(count != -1)
                {
                    string id = count.ToString();
                    while (id.Length < 5)
                    {
                        id = "0" + id;
                    }
                    id = "S" + user_id.Substring(1) + id;
                    sm.user_id = user_id;
                    sm.story_id = id;
                    sm.title = title;
                    sm.content = content;
                    if(dbOperation.insertStory(sm))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        [WebMethod(Description = "Insert House")]
        public bool insertHouse(String user_id,String password,String city,String address,String limitation,String info)
        {
            int count = 0;
            Model.HouseModel hm = new Model.HouseModel();
            if (dbOperation.queryExistUser(user_id, password))
            {
                count = dbOperation.countHouse(user_id);
                if (count != -1)
                {
                    string id = count.ToString();
                    while(id.Length < 5)
                    {
                        id = "0" + id;
                    }
                    id = "H" + user_id.Substring(1) + id;
                    hm.user_id = user_id;
                    hm.house_id = id;
                    hm.city_id = city;
                    hm.address = address;
                    hm.limitation = limitation;
                    hm.info = info;
                    if (dbOperation.insertHouse(hm))
                    {
                        return true;
                    }
                }
            }
            return false;
        }

        // hai yao gai
        [WebMethod(Description = "DengLu")]
        public string[] loginUser(String nickname,String password)
        {
            List<string> list = dbOperation.queryUser(nickname);
            if(list.Count != 0 && list[11] == password)
            {
                return list.ToArray();
            }
            list.Clear();
            string[] str = new string[1] { "false" };
            return str;
        }

        [WebMethod(Description = "Update User Basic Info")]
        public bool updateUserBasicInfo(String user_id,String password,String slogan,DateTime birthday,String occupation,String gender,String address)
        {
            Model.UserModel model = new Model.UserModel();
            model.user_id = user_id;
            model.password = password;
            model.slogan = slogan;
            model.birthday = birthday;
            model.occupation = occupation;
            model.gender = gender;
            model.address = address;
            return dbOperation.updateUserBasicInfo(model);
        }

        [WebMethod(Description = "Update Story")]
        public bool updateStory(String user_id, String password, String story_id, String title, String content)
        {
            if(dbOperation.queryExistUserStory(user_id,story_id,password))
            {
                Model.StoryModel sm = new Model.StoryModel();
                sm.user_id = user_id;
                sm.story_id = story_id;
                sm.title = title;
                sm.content = content;
                return dbOperation.updateStory(sm);
            }
            return false;
        }

        [WebMethod(Description = "Update House info")]
        public bool updateHouse(String user_id, String password, String house_id, String city, String address, String limitation,String info)
        {
            if (dbOperation.queryExistUserHouse(user_id, house_id, password))
            {
                Model.HouseModel hm = new Model.HouseModel();
                hm.user_id = user_id;
                hm.house_id = house_id;
                hm.city_id = city;
                hm.address = address;
                hm.limitation = limitation;
                hm.info = info;

                return dbOperation.updateHouse(hm);
            }
            return false;
        }

        [WebMethod(Description = "update House state")]
        public bool updateHouseState(String user_id,String password,String house_id, String state)
        {
            if (dbOperation.queryExistUserHouse(user_id, house_id, password))
            {
                if (state.Length > 1)
                    state = state.Substring(0, 1);
                return dbOperation.updateHouseState(house_id,state);
            }
            return false;
        }

        [WebMethod(Description = "Query User Story")]
        public string[] queryUserStory(String user_id)
        {
            List<string> list = new List<string>();
            foreach (var i in dbOperation.queryStory(user_id).ToArray())
            {
                list.Add(i.story_id);
                list.Add(user_id);
                list.Add(i.title);
                list.Add(i.title);
            }
            return list.ToArray();
        }

        [WebMethod(Description = "Query House")]
        public string[] queryUserHouse(String user_id)
        {
            List<string> list = new List<string>();
            foreach (var i in dbOperation.queryHouse(user_id).ToArray())
            {
                list.Add(i.house_id);
                list.Add(user_id);
                list.Add(i.city_id);
                list.Add(i.address);
                list.Add(i.limitation);
                list.Add(i.info);
                list.Add(i.state.ToString());
            }
            return list.ToArray();
        }

        //mei xie wan
        [WebMethod(Description = "Update User Security Info(MeiXieWan)")]
        public bool updateUserSecurityInfo(String user_id, String personal_id, String question, String answer, String phone)
        {
            return false;
        }
    }
}