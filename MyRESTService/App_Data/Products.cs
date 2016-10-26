using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Web;

using System.ServiceModel;
using System.Runtime.Serialization;


namespace MyRESTService
{
    [DataContract]
    public class Products
    {
        [DataMember]
        public int id_product { get; set; }
        [DataMember]
        public string details { get; set; }
        [DataMember]
        public int stock { get; set; }
        [DataMember]
        public int price { get; set; }
        [DataMember]
        public string taxfree { get; set; }
        [DataMember]
        public int id_supplier { get; set; }
        [DataMember]
        public int id_category { get; set; }
        [DataMember]
        public string active { get; set; }
        [DataMember]
        public string name { get; set; }
        [DataMember]
        public int boffice { get; set; }
        [DataMember]
        public int quantity { get; set; }
        [DataMember]
        public int id_stage { get; set; }
        //[DataMember]
        //public bool Purchased{get; set;}
        //[DataMember]
        //public string hashKey { get; set; }
    }
}

