using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Web;

namespace MyRESTService
{
    [DataContract]
    public class Engineer
    {
        [DataMember]
        public int ID_Engineer { get; set; }
        [DataMember]
        public string Name { get; set; }
        [DataMember]
        public string Lastname_1 { get; set; }
        [DataMember]
        public string Lastname_2 { get; set; }
        [DataMember]
        public string Phone { get; set; }
        [DataMember]
        public string Email { get; set; }
        [DataMember]
        public string Eng_Code { get; set; }
        [DataMember]
        public string Username { get; set; }
        [DataMember]
        public string Password { get; set; }
        [DataMember]
        public string Role { get; set; }
    }
}