using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;
using NpgsqlTypes;

namespace MyRESTService
{
    [DataContract]
    public class Stage
    {
        [DataMember]
        public int ID_Stage { get; set; }
        [DataMember]
        public int ID_Stage_Name { get; set; }
        [DataMember]
        public string Stage_Name { get; set; }
        [DataMember]
        public int ID_Project { get; set; }
        [DataMember]
        public string Start_Date { get; set; }
        [DataMember]
        public string End_Date { get; set; }
        [DataMember]
        public string Details { get; set; }
        [DataMember]
        public bool Completed { get; set; }
        [DataMember]
        public string Comments { get; set; }

    }
}