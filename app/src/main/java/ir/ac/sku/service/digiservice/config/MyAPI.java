package ir.ac.sku.service.digiservice.config;

public class MyAPI {
    //* Web Site URL
    public static final String DIGI_SERVICE             = "http://service.sku.ac.ir";
    public static final String SKU                      = "https://www.sku.ac.ir";

    //* Web Service Digi Service
    public static final String SELECTED_RESOURCES       = "http://service.sku.ac.ir/tiidapi/resource";             // id [get from Resources field id]
    public static final String DEPARTMENTS_FILTER       = "http://service.sku.ac.ir/tiidapi/departmentsfilter";    // status [10 -> Resources | 01 -> Educational]
    public static final String POPULAR_RESOURCES        = "http://service.sku.ac.ir/tiidapi/popularresources";     // status [10 -> Resources | 01 -> Educational]
    public static final String RECENT_RESOURCES         = "http://service.sku.ac.ir/tiidapi/recentresources";      // status [10 -> Resources | 01 -> Educational]
    public static final String ALL_RESOURCES            = "http://service.sku.ac.ir/tiidapi/allresources";         //
    public static final String DEPARTMENTS              = "http://service.sku.ac.ir/tiidapi/departments";          //
    public static final String SYSTEM_INFO              = "http://service.sku.ac.ir/tiidapi/systeminfo";           //
    public static final String RESOURCES                = "http://service.sku.ac.ir/tiidapi/resources";            // areaId [get from Area field id]
    public static final String SCHEDULE                 = "http://service.sku.ac.ir/tiidapi/schedule";             // id [get from Selected Resources field id]
    public static final String SLIDER                   = "http://service.sku.ac.ir/tiidapi/slider";               // eventId [0 -> Resources | -1 -> Educational]
    public static final String SEARCH                   = "http://service.sku.ac.ir/tiidapi/search";               // filter [Search Text] & eventId [0 -> Resources | -1 -> Educational]
    public static final String AREAS                    = "http://service.sku.ac.ir/tiidapi/areas";                //
    public static final String NEWS                     = "http://service.sku.ac.ir/tiidapi/news";                 // eventId [0 -> Resources | -1 -> Educational]
}
