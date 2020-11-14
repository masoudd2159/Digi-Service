package ir.ac.sku.service.digiservice.config;

public class MyAPI {
    //* Web Site URL
    public static final String SKU                  = "https://www.sku.ac.ir/";
    public static final String DIGI_SERVICE         = "http://service.sku.ac.ir/";
    public static final String BASE_URL             = "http://service.sku.ac.ir/tiidapi/";

    //* Web Service Digi Service
    public static final String SELECTED_RESOURCES   = "resource";                    // id [get from Resources field id]
    public static final String DEPARTMENTS_FILTER   = "departmentsfilter";           // status [10 -> Resources | 01 -> Educational]
    public static final String POPULAR_RESOURCES    = "popularresources";            // status [10 -> Resources | 01 -> Educational]
    public static final String RECENT_RESOURCES     = "recentresources";             // status [10 -> Resources | 01 -> Educational]
    public static final String ALL_RESOURCES        = "allresources";                //
    public static final String DEPARTMENTS          = "departments";                 //
    public static final String SYSTEM_INFO          = "systeminfo";                  //
    public static final String RESOURCES            = "resources";                   // areaId [get from Area field id]
    public static final String SCHEDULE             = "schedule";                    // id [get from Selected Resources field id]
    public static final String SLIDER               = "slider";                      // eventId [0 -> Resources | -1 -> Educational]
    public static final String SEARCH               = "search";                      // filter [Search Text] & eventId [0 -> Resources | -1 -> Educational]
    public static final String AREAS                = "areas";                       //
    public static final String NEWS                 = "news";                        // eventId [0 -> Resources | -1 -> Educational]
}
