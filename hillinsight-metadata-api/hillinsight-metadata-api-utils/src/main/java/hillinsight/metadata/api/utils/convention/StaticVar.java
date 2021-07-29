package hillinsight.metadata.api.utils.convention;

public class StaticVar {
    public static class BeanNames {
        public static final String DATASOURCE = "hillinsight.metadata.api.datasource";
        public static final String PAGEHELPER = "hillinsight.metadata.api.pagehelper";
        public static final String SQL_SESSION_TEMPLATE = "hillinsight.metadata.api.sqlSessionTemplate";
        public static final String MYBATIS_CONFIG = "hillinsight.metadata.api.mybatisConfig";
        public static final String SQL_SESSION_FACTORY = "hillinsight.metadata.api.sqlSessionFactory";
        public static final String TRANSACTION_MANAGER = "hillinsight.metadata.api.transactionManager";
    }

    public static class ApiClientBeans {
        public static final String NORMAL_REST_TEMPLATE = "hillinsight.example.apiclientbeans.normal-rest-template";
        public static final String RIBBON_REST_TEMPLATE = "hillinsight.example.apiclientbeans.ribbon-rest-template";
    }

    public static class ApiClientKeys {
        public static final String HLINK_API_CLIENT = "hillinsight.metadata.apiclient.hlink";
        public static final String ACS_API_CLIENT = "hillinsight.metadata.apiclient.acs";
    }

    public static class ServiceNames {
        public static final String ACS_SERVICE_NAME = "hillinsight-acs-api";
    }
}
