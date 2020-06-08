package com.ennova.standard.utils.simulatedata;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

/**
 * @作者 zhouchao
 * @日期 2019/9/12
 * @描述 通过反射讲传入的javabean设置上随机数据
 */
public class SimulateDataUtils {

    public static String[] textArray = "白 日 登 山 望 烽 火 黄 昏 饮 马 傍 交 河".split(" ");

    public static Object simulateData(JavaBeanConfig javaBeanConfig) {
        Object obj = null;
        try {
            obj = fillBeanData(javaBeanConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Object fillBeanData(JavaBeanConfig javaBeanConfig)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchMethodException,
            InvocationTargetException {

        if (javaBeanConfig.className() != null && javaBeanConfig.className().length() > 0 && javaBeanConfig.className().equals("java.lang.Boolean")) {
            return new Random().nextBoolean();
        } else if (javaBeanConfig.className() != null && javaBeanConfig.className().length() > 0 && javaBeanConfig.className().equals("java.lang.Integer")) {
            return new Random().nextInt(100);
        } else if (javaBeanConfig.className() != null && javaBeanConfig.className().length() > 0 && javaBeanConfig.className().equals("java.lang.String")) {
//            return String.valueOf(new Random().nextInt(100));
            String value = getString();
            return value;
        } else if (javaBeanConfig.className() != null && javaBeanConfig.className().length() > 0 && javaBeanConfig.className().equals("java.lang.Double")) {
            DecimalFormat df = new DecimalFormat(".00");
            return Double.valueOf(df.format(new Random().nextDouble()));
        } else if (javaBeanConfig.className() != null && javaBeanConfig.className().length() > 0 && javaBeanConfig.className().equals("java.lang.Float")) {
            DecimalFormat df = new DecimalFormat(".00");
            return Float.valueOf(df.format(new Random().nextFloat()));
        } else if (javaBeanConfig.className() != null && javaBeanConfig.className().length() > 0 && javaBeanConfig.className().equals("java.lang.Long")) {
            return new Random().nextLong();
        }

        Class clazz = Class.forName(javaBeanConfig.className());
        Object obj = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.startsWith("is")) {
                Type genericType = field.getGenericType();
                Class<?> type = field.getType();
                if (!genericType.toString().equals("boolean")
                        || !type.toString().equals("class java.lang.Boolean")) {
                    continue;
                }
                fieldName = fieldName.replace("is", "");
            }
            if (fieldName.equals("serialVersionUID")
                    || fieldName.equals("CREATOR")
                    || fieldName.equals("SUCCESS")
                    || fieldName.equals("FAIL")
                    || fieldName.equals("TOKEN_EXPRISE")
                    || fieldName.equals("$change")) {
                continue;
            }
            Class<?> type = field.getType();
            Type genericType = field.getGenericType();
            String firstLetter = fieldName.substring(0, 1);
            String methodName = "set" + firstLetter.toUpperCase()
                    + fieldName.substring(1);
            Method method = clazz.getMethod(methodName, type);
            if (type.toString().equals("int")
                    || type.toString().equals("class java.lang.Integer")) {
                if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Integer) {
                    method.invoke(obj,
                            javaBeanConfig.specifyFields().get(fieldName));
                } else {
                    method.invoke(obj, new Random().nextInt(100));
                }
            } else if (genericType.toString().equals("class java.lang.String")) {
                if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof String) {
                    method.invoke(obj,
                            javaBeanConfig.specifyFields().get(fieldName));
                } else {
                    String value = getString();
                    method.invoke(obj, value);
//                    method.invoke(obj, String.valueOf(new Random().nextInt(100)));
                }
            } else if (genericType.toString().equals("double")
                    || type.toString().equals("class java.lang.Double")) {
                if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Double) {
                    method.invoke(obj,
                            javaBeanConfig.specifyFields().get(fieldName));
                } else {
                    DecimalFormat df = new DecimalFormat(".00");
                    method.invoke(obj,
                            Double.valueOf(df.format(new Random().nextDouble())));
                }
            } else if (genericType.toString().equals("float")
                    || type.toString().equals("class java.lang.Float")) {
                if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Float) {
                    method.invoke(obj,
                            javaBeanConfig.specifyFields().get(fieldName));
                } else {
                    DecimalFormat df = new DecimalFormat(".00");
                    method.invoke(obj,
                            Float.valueOf(df.format(new Random().nextFloat())));
                }
            } else if (genericType.toString().equals("boolean")
                    || type.toString().equals("class java.lang.Boolean")) {
                if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Boolean) {
                    method.invoke(obj,
                            javaBeanConfig.specifyFields().get(fieldName));
                } else if (javaBeanConfig.specifyFields().containsKey("is" + fieldName) && (javaBeanConfig.specifyFields().get("is" + fieldName).toString().equals("true") || javaBeanConfig.specifyFields().get("is" + fieldName).toString().equals("false"))) {
                    method.invoke(obj, Boolean.parseBoolean(javaBeanConfig.specifyFields().get("is" + fieldName).toString()));
                } else {
                    method.invoke(obj, new Random().nextBoolean());
                }
            } else if (genericType.toString().equals("long")
                    || type.toString().equals("class java.lang.Long")) {
                if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Long) {
                    method.invoke(obj,
                            javaBeanConfig.specifyFields().get(fieldName));
                } else {
                    method.invoke(obj, new Random().nextLong());
                }
            } else if (genericType.toString().contains("$")
                    && !genericType.toString().contains("java.util.ArrayList") && !genericType.toString().contains("java.util.List")) {
                String innerClassName = type.toString().replace("class ", "");
                JavaBeanConfig innerJavaBeanConfig = new JavaBeanConfig.Builder()
                        .className(innerClassName)
                        .specifyFields(javaBeanConfig.specifyFields())
                        .arrayLenght(javaBeanConfig.arrayLenght())
                        .build();
                Object innerObject = fillBeanData(innerJavaBeanConfig);
                method.invoke(obj, innerObject);
            } else if (genericType.toString().contains("java.util.ArrayList") || genericType.toString().contains("java.util.List")) {
                setArrayData(obj, genericType, method, fieldName,
                        javaBeanConfig);
            } else if (javaBeanConfig.classTName() != null
                    && javaBeanConfig.classTName().length() > 0
                    && genericType.toString().equals("T")) {
                if (javaBeanConfig.isArrayWithT()) {
                    setArrayData(obj, genericType, method, fieldName,
                            javaBeanConfig);
                } else if (javaBeanConfig.classTName().equals("java.lang.String")) {
//                    method.invoke(obj,String.valueOf(new Random().nextInt(100)));
                    String value = getString();
                    method.invoke(obj, value);
                } else if (javaBeanConfig.classTName().equals("java.lang.Integer")) {
                    method.invoke(obj, new Random().nextInt(100));
                } else if (javaBeanConfig.classTName().equals("java.lang.Double")) {
                    DecimalFormat df = new DecimalFormat(".00");
                    method.invoke(obj,
                            Double.valueOf(df.format(new Random().nextDouble())));
                } else if (javaBeanConfig.classTName().equals("java.lang.Float")) {
                    DecimalFormat df = new DecimalFormat(".00");
                    method.invoke(obj,
                            Float.valueOf(df.format(new Random().nextFloat())));
                } else if (javaBeanConfig.classTName().equals("java.lang.Boolean")) {
                    method.invoke(obj, new Random().nextBoolean());
                } else if (javaBeanConfig.classTName().equals("java.lang.Long")) {
                    method.invoke(obj, new Random().nextLong());
                } else {
                    JavaBeanConfig innerJavaBeanConfig = new JavaBeanConfig.Builder()
                            .className(javaBeanConfig.classTName())
                            .specifyFields(javaBeanConfig.specifyFields())
                            .arrayLenght(javaBeanConfig.arrayLenght()).build();
                    Object innerObject = fillBeanData(innerJavaBeanConfig);
                    method.invoke(obj, innerObject);
                }
            } else {
                String innerClassName = type.toString().replace("class ", "");
                JavaBeanConfig innerJavaBeanConfig = new JavaBeanConfig.Builder()
                        .className(innerClassName)
                        .specifyFields(javaBeanConfig.specifyFields())
                        .arrayLenght(javaBeanConfig.arrayLenght())
                        .build();
                Object innerObject = fillBeanData(innerJavaBeanConfig);
                method.invoke(obj, innerObject);
            }
        }
        return obj;
    }

    @NotNull
    private static String getString() {
        String value = "";
        for (int i = 0; i < new Random().nextInt(7); i++) {
            value += textArray[new Random().nextInt(textArray.length)];
        }
        return value;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void setArrayData(Object obj, Type genericType,
                                     Method method, String fieldName, JavaBeanConfig javaBeanConfig)
            throws IllegalAccessException, InvocationTargetException,
            ClassNotFoundException, InstantiationException,
            NoSuchMethodException {
        String genericTypeString = genericType.toString();
        if (genericTypeString.equals("T")) {
            ArrayList list = new ArrayList();
            for (int i = 0; i < javaBeanConfig.arrayLenght(); i++) {
                JavaBeanConfig innerJavaBeanConfig = new JavaBeanConfig.Builder()
                        .arrayLenght(javaBeanConfig.arrayLenght())
                        .specifyFields(javaBeanConfig.specifyFields())
                        .className(javaBeanConfig.classTName()).build();
                Object innerObject = fillBeanData(innerJavaBeanConfig);
                list.add(innerObject);
                method.invoke(obj, list);
            }
        } else {
            String arrayGenericType = genericTypeString.substring(
                    genericTypeString.indexOf("<") + 1,
                    genericTypeString.indexOf(">"));
            ArrayList list = new ArrayList();
            for (int i = 0; i < javaBeanConfig.arrayLenght(); i++) {
                if (arrayGenericType.equals("java.lang.String")) {
                    if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof String) {
                        list.add(javaBeanConfig.specifyFields().get(fieldName));
                    } else {
                        String value = getString();
                        method.invoke(obj, value);
                        list.add(value);
//                        list.add(String.valueOf(new Random().nextInt(100)));
                    }
                } else if (arrayGenericType.equals("java.lang.Integer")) {
                    if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Integer) {
                        list.add(javaBeanConfig.specifyFields().get(fieldName));
                    } else {
                        list.add(new Random().nextInt(100));
                    }

                } else if (arrayGenericType.equals("java.lang.Long")) {
                    if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Long) {
                        list.add(javaBeanConfig.specifyFields().get(fieldName));
                    } else {
                        list.add(new Random().nextLong());
                    }
                } else if (arrayGenericType.equals("java.lang.Double")) {
                    if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Double) {
                        list.add(javaBeanConfig.specifyFields().get(fieldName));
                    } else {
                        DecimalFormat df = new DecimalFormat(".00");
                        list.add(Double.valueOf(df.format(new Random().nextDouble())));
                    }
                } else if (arrayGenericType.equals("java.lang.Float")) {
                    if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Float) {
                        list.add(javaBeanConfig.specifyFields().get(fieldName));
                    } else {
                        DecimalFormat df = new DecimalFormat(".00");
                        list.add(Float.valueOf(df.format(new Random().nextFloat())));
                    }
                } else if (arrayGenericType.equals("java.lang.Boolean")) {
                    if (javaBeanConfig.specifyFields().containsKey(fieldName) && javaBeanConfig.specifyFields().get(fieldName) instanceof Boolean) {
                        list.add(javaBeanConfig.specifyFields().get(fieldName));
                    } else {
                        list.add(new Random().nextBoolean());
                    }
                } else if (genericType.toString().contains(
                        "java.util.ArrayList") || genericType.toString().contains(
                        "java.util.List")) {
                    String innerClassName = arrayGenericType;
                    JavaBeanConfig innerJavaBeanConfig = new JavaBeanConfig.Builder()
                            .arrayLenght(javaBeanConfig.arrayLenght())
                            .specifyFields(javaBeanConfig.specifyFields())
                            .className(innerClassName).build();
                    Object innerObject = fillBeanData(innerJavaBeanConfig);
                    list.add(innerObject);
                } else if (javaBeanConfig.classTName() != null
                        && javaBeanConfig.classTName().length() > 0
                        && genericType.toString().equals("T")) {
                    JavaBeanConfig innerJavaBeanConfig = new JavaBeanConfig.Builder()
                            .arrayLenght(javaBeanConfig.arrayLenght())
                            .specifyFields(javaBeanConfig.specifyFields())
                            .className(javaBeanConfig.classTName()).build();
                    Object innerObject = fillBeanData(innerJavaBeanConfig);
                    method.invoke(obj, innerObject);
                } else {
                    JavaBeanConfig innerJavaBeanConfig = new JavaBeanConfig.Builder()
                            .className(arrayGenericType)
                            .specifyFields(javaBeanConfig.specifyFields())
                            .arrayLenght(javaBeanConfig.arrayLenght()).build();
                    Object innerObject = fillBeanData(innerJavaBeanConfig);
                    method.invoke(obj, innerObject);
                }
            }
            method.invoke(obj, list);
        }

    }
}
