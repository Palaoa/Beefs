using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace STWebService.Model
{
    public class UserModel
    {
        public string user_id { get; set; }
        public string nickname { get; set; }
        public string slogan { get; set; }
        public string avatar { get; set; }    //   ???
        public DateTime birthday { get; set; }
        public string occupation { get; set; }
        public string gender { get; set; }
        public string address { get; set; }
        public int grade { get; set; }
        public int coin { get; set; }
        public int contribution { get; set; }
        public string password { get; set; }
        public string phone { get; set; }
        internal void setDate(string str)
        {
            if (str == "")
                return;
            try
            {
                birthday = DateTime.Parse(str);
            }
            catch
            {

            }
        }
    }
}