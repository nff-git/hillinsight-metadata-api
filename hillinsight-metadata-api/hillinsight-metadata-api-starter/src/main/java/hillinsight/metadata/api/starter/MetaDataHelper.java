package hillinsight.metadata.api.starter;

import focus.apiclient.core.ApiInvoker;
import focus.core.exceptions.SystemException;
import focus.core.utils.ObjectUtil;
import hillinsight.metadata.api.starter.requests.MetaDataInfoRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

public class MetaDataHelper {
    /**
     * 根据 Model 上的注解获取扩展字段值
     *
     * @param propertyName 扩展字段的名称
     * @param model        模型的对象
     * @param <TModel>     模型的类型
     * @param <TValue>     要获取的扩展字段值的类型
     * @return 扩展字段值
     */
    public static <TModel, TValue> TValue getValue(String propertyName, TModel model) {
        // 取注解上的信息，然后调用元数据的接口，找到对应的 A

        Class clazz = model.getClass();

        // TODO：缓存反射的注解信息
        Annotation[] annotations = clazz.getAnnotations();

        MetaDataObject metaDataObject = (MetaDataObject) Arrays.stream(annotations).filter(v -> v instanceof MetaDataObject).findFirst().get();
        MetaDataProperty metaDataProperty = (MetaDataProperty) Arrays.stream(annotations).filter(v -> v instanceof MetaDataProperty && ((MetaDataObject) v).name().equals(propertyName)).findFirst().get();

        MetaDataInfoRequest request = ObjectUtil.initObject(new MetaDataInfoRequest())
                .init(v -> v.setMetaDataObjectName(metaDataObject.name()))
                .init(v -> v.setMetaDataPropertyName(metaDataProperty.name()))
                .getObject();

        // 调用元数据的服务获取对应的扩展字段列名
        String json = ApiInvoker.invokeService(request, String.class);
        // TODO：分析返回结果，根据返回的扩展字段列取值

        String extensionFieldName = "";
        try {
            Field field = clazz.getDeclaredField(extensionFieldName);
            field.setAccessible(false);
            return (TValue) field.get(model);
        } catch (NoSuchFieldException ce) {
            throw new SystemException("没有找到对应的字段 -> " + extensionFieldName);
        } catch (Throwable ce) {
            throw new SystemException("反射取值未知异常", ce);
        }
    }

    /**
     * 根据 Model 上的注解的对象获取全部扩展字段的值
     *
     * @param model    模型的对象
     * @param <TModel> 模型的类型
     * @return 全部扩展字段值的字典
     */
    public static <TModel> Map<String, Object> getAllValues(TModel model) {
        return null;
    }
}
