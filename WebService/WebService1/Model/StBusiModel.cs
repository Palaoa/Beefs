using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace STWebService.Model
{
    public class StBusiModel
    {
        public String story_id { get; set; }
        public String user_id { get; set; }
        public String title { get; set; }
        public String nickName { get; set; }
        public String avatar { get; set; }
        public String photo { get; set; }
        public String content { get; set; }
        public String like_num { get; set; }
        public String bl { get; set; }
    }
}