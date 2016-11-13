using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace STWebService.Model
{
    public class HouseModel
    {
        public string house_id { get; set; }
        public string user_id { get; set; }
        public string city_id { get; set; }
        public string address { get; set; }
        public string limitation { get; set; }
        public string photo { get; set; }
        public string info { get; set; }
        public char state { get; set; }
    }
}