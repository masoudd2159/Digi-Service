package ir.ac.sku.service.digiservice.config;

public class MyAPI {
    //* Web Site URL
    public static final String SKU                      = "https://www.sku.ac.ir";
    public static final String DIGI_SERVICE             = "http://service.sku.ac.ir";
    public static final String BASE_URL                 = "http://service.sku.ac.ir/tiidapi/";

    //* Web Service Digi Service
    public static final String SELECTED_RESOURCES  = BASE_URL + "resource";            // id [get from Resources field id]
    public static final String DEPARTMENTS_FILTER  = BASE_URL + "departmentsfilter";   // status [10 -> Resources | 01 -> Educational]
    public static final String POPULAR_RESOURCES   = BASE_URL + "popularresources";    // status [10 -> Resources | 01 -> Educational]
    public static final String RECENT_RESOURCES    = BASE_URL + "recentresources";     // status [10 -> Resources | 01 -> Educational]
    public static final String ALL_RESOURCES       = BASE_URL + "allresources";        //
    public static final String DEPARTMENTS         = BASE_URL + "departments";         //
    public static final String SYSTEM_INFO         = BASE_URL + "systeminfo";          //
    public static final String RESOURCES           = BASE_URL + "resources";           // areaId [get from Area field id]
    public static final String SCHEDULE            = BASE_URL + "schedule";            // id [get from Selected Resources field id]
    public static final String SLIDER              = BASE_URL + "slider";              // eventId [0 -> Resources | -1 -> Educational]
    public static final String SEARCH              = BASE_URL + "search";              // filter [Search Text] & eventId [0 -> Resources | -1 -> Educational]
    public static final String AREAS               = BASE_URL + "areas";               //
    public static final String NEWS                = BASE_URL + "news";                // eventId [0 -> Resources | -1 -> Educational]
}
