using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace MyRESTService
{
    [DataContract]
    public class Role
    {
        [DataMember]
        public int ID_Role { get; set; }
        [DataMember]
        public string Name { get; set; }
    }
}