package hillinsight.metadata.api.starter.test;

import focus.apiclient.core.ApiInvoker;
import hillinsight.metadata.api.starter.MetaDataHelper;
import hillinsight.metadata.api.starter.MetaDataObject;
import hillinsight.metadata.api.starter.MetaDataProperty;
import org.junit.Test;

import java.util.Map;

public class MetaDataHelperTest {

    @MetaDataObject(name = "User")
    @MetaDataProperty(name = "Department")
    public static class User {
        private String name;
        private String code;
        private String extensionField1;
        private String extensionField2;
        private String extensionField3;
    }

    @Test
    public void testGetValue() {
        User user = new User();

        int value = MetaDataHelper.getValue("Department", user);

        Map<String, Object> values = MetaDataHelper.getAllValues(user);
    }
}
