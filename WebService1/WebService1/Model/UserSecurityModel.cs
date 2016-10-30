using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace STWebService.Model
{
    public class UserSecurityModel
    {
        public string user_id { get; set;}
        public string personal_id { get; set; }
        public string question { get; set; }
        public string answer { get; set; }
        public string phone { get; set; }

    }
}