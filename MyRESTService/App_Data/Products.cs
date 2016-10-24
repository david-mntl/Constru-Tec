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
        public int ID_Product { get; set; }
        [DataMember]
        public string Details { get; set; }
        [DataMember]
        public int Stock { get; set; }
        [DataMember]
        public int Price { get; set; }
        [DataMember]
        public string TaxFree { get; set; }
        [DataMember]
        public int ID_Supplier { get; set; }
        [DataMember]
        public int ID_Category { get; set; }
        [DataMember]
        public string Active { get; set; }
        [DataMember]
        public string Name { get; set; }
        [DataMember]
        public int BOffice { get; set; }
        [DataMember]
        public int Quantity{ get; set; }
        [DataMember]
        public int ID_Stage { get; set; }

    }
}

