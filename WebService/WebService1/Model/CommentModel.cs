using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace STWebService.Model
{
    public class CommentModel
    {
        public string user_id { get; set; }
        public string story_id { get; set; }
        public string edittime { get; set; }
        public string content { get; set; }
        public string comment_id { get; set; }
    }
}