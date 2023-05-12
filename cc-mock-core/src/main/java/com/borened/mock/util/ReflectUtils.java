package com.borened.mock.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @author boren
 */
public class ReflectUtils {

    /**
     * 获取泛型的真实类型。
     * @param classParameterizedType 类的参数类型
     * @param genericType 泛型类型
     * @return 真实的类型
     */
    public static Type convertActualType(ParameterizedType classParameterizedType,  Type genericType)  {
        //字段本身是泛型类型,例如: private T user;根据泛型类型名匹配类的实际参数类型。
        if (genericType instanceof TypeVariable){
            TypeVariable<?> typeVariable = (TypeVariable<?>) genericType;
            Class<?> clazz = (Class<?>) classParameterizedType.getRawType();
            Type[] actualTypeArguments = classParameterizedType.getActualTypeArguments();
            TypeVariable<? extends Class<?>>[] typeParameters = clazz.getTypeParameters();
            for (int i = 0; i < typeParameters.length; i++) {
                TypeVariable<? extends Class<?>> typeParameter =  typeParameters[i];
                if ( typeParameter.getName().equalsIgnoreCase(typeVariable.getName()) ) {
                    return actualTypeArguments[i];
                }
            }
            throw new RuntimeException("get field type variable error!");
        //字段是其他参数化类型，如List<T>,Map<K,V>等类型，获取泛型参数的实际类型，递归转换真实类型。
        }else if ( genericType instanceof ParameterizedType ) {
            ParameterizedType parameterizedType = (ParameterizedType)genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (int i = 0; i < actualTypeArguments.length; i++) {
                Type actualTypeArgument = actualTypeArguments[i];
                actualTypeArguments[i] =  convertActualType(classParameterizedType, actualTypeArgument);
            }
            return new ParameterizedTypeImpl(parameterizedType.getRawType(), actualTypeArguments);
        //Class类型则直接返回
        }else{
            return genericType;
        }
    }


}