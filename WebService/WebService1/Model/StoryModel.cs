using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace STWebService.Model
{
    public class StoryModel
    {
        public string story_id { get; set; }
        public string user_id { get; set; }
        public string title { get; set; }
        public string content { get; set; }
        public Byte[] photo { get; set; }
        public char state { get; set; }
        public char mshow { get; set; }
        public DateTime editTime { get; set; }
    }
}