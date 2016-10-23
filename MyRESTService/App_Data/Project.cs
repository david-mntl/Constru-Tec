using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace MyRESTService
{
    [DataContract]
    public class Project
    {
        [DataMember]
        public int ID_Project { get; set; }
        [DataMember]
        public string Name { get; set; }
        [DataMember]
        public string Location { get; set; }
        [DataMember]
        public int ID_Engineer { get; set; }
        [DataMember]
        public int ID_Customer { get; set; }
        [DataMember]
        public string Comments { get; set; }
        [DataMember]
        public string Details { get; set; }
        [DataMember]
        public bool Active { get; set; }
        [DataMember]
        public bool Completed { get; set; }
    }
}