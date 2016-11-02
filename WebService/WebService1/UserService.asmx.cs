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
                if( dbOperation.insertUser(count.ToString(), password, nickname) )
                {
                    if (dbOperation.insertUserSecurity(count.ToString()))
                        return true;
                    else
                    {
                        dbOperation.deleteUser(count.ToString());
                        return false;
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

        //mei xie wan
        [WebMethod(Description = "Update User Security Info(MeiXieWan)")]
        public bool updateUserSecurityInfo(String user_id, String personal_id, String question, String answer, String phone)
        {
            return false;
        }
    }
}