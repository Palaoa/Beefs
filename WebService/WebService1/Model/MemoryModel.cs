using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace STWebService.Model
{
    public class MemoryModel
    {
        public string memory_id { get; set; }
        public string user_id { get; set; }
        public string title { get; set; }
        public string content { get; set; }
        public string photo { get; set; }
        public string limitation { get; set; }
    }
}