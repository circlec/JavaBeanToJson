import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.google.gson.Gson;

public class JavaBeanToJson {

	public static String dirPath_desktop = "C:\\Users\\think_admin\\Desktop\\";
	public static String dirPath_E = "E:\\";
	private static int arrayLength = 2;
	private static HashMap<String, String> specifyFields = new HashMap<>();

	public static void main(String[] args) {
		ResponseEntity<CommodityDetailEntity> obj = (ResponseEntity<CommodityDetailEntity>) simulatedDataWithJavaBean(
				ResponseEntity.class.getName(),
				CommodityDetailEntity.class.getName());
		toJsonFile(ResponseEntity.class.getName(), obj);
	}

	 public static Object simulatedDataWithJavaBean(String className) {
	        Object obj = null;
	        try {
	            obj = fillBeanData(className);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return obj;
	    }

	    public static Object simulatedDataWithJavaBean(String className,
	                                                   String childTclassName) {
	        Object obj = null;
	        try {
	            obj = fillBeanData(className, childTclassName);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return obj;
	    }

	    public static Object simulatedDataWithJavaBean(String className,
	                                                   int myArrayLength,
	                                                   HashMap<String, String> specifyStringFields) {
	        arrayLength = myArrayLength;
	        specifyFields = specifyStringFields;
	        Object obj = null;
	        try {
	            obj = fillBeanData(className);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return obj;
	    }

	    public static Object simulatedDataWithJavaBean(String className,
	                                                   String childTclassName,
	                                                   int myArrayLength,
	                                                   HashMap<String, String> specifyStringFields) {
	        arrayLength = myArrayLength;
	        specifyFields = specifyStringFields;
	        Object obj = null;
	        try {
	            obj = fillBeanData(className, childTclassName);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return obj;
	    }

	    public static void toJsonFile(String className, Object obj) {
	        Gson gson = new Gson();
	        System.out.println(gson.toJson(obj));
	        String fileName = className.substring(
	                className.lastIndexOf(".") + 1, className.length());
	        File file = new File(dirPath_desktop + fileName + ".json");
	        if (file.exists()) {
	            file.delete();
	        }
	        saveFile(dirPath_desktop + fileName + ".json", gson.toJson(obj));
	    }

	    public static void saveFile(String filePath, String content) {
	        try {
	            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
	                    filePath), true));
	            writer.write("\n" + content);
	            writer.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private static Object fillBeanData(String className)
	            throws ClassNotFoundException, InstantiationException,
	            IllegalAccessException, NoSuchMethodException,
	            InvocationTargetException {
	        Class clazz = Class.forName(className);
	        Object obj = clazz.newInstance();
	        Field[] fields = clazz.getDeclaredFields();
	        for (Field field : fields) {
	            String fieldName = field.getName();
	            if (fieldName.equals("serialVersionUID")) {
	                break;
	            }
	            Class<?> type = field.getType();
	            Type genericType = field.getGenericType();
	            String firstLetter = fieldName.substring(0, 1);
	            String methodName = "set" + firstLetter.toUpperCase()
	                    + fieldName.substring(1, fieldName.length());
	            Method method = clazz.getMethod(methodName, type);
	            if (type.toString().equals("int")
	                    || type.toString().equals("class java.lang.Integer")) {
	                method.invoke(obj, new Random().nextInt(100));
	            } else if (genericType.toString().equals("class java.lang.String")) {
	                if (specifyFields.containsKey(fieldName)) {
	                    method.invoke(obj, specifyFields.get(fieldName));
	                } else {
	                    method.invoke(obj, String.valueOf(new Random().nextInt(100)));
	                }
	            } else if (genericType.toString().equals("double")
	                    || type.toString().equals("class java.lang.Double")) {
	                DecimalFormat df = new DecimalFormat(".00");
	                method.invoke(obj, Double.valueOf(df.format(new Random().nextDouble())));
	            } else if (genericType.toString().equals("float")
	                    || type.toString().equals("class java.lang.Float")) {
	                DecimalFormat df = new DecimalFormat(".00");
	                method.invoke(obj,Float.valueOf(df.format(new Random().nextFloat())));
	            } else if (genericType.toString().equals("boolean")
	                    || type.toString().equals("class java.lang.Boolean")) {
	                method.invoke(obj, new Random().nextBoolean());
	            } else if (genericType.toString().equals("long")
	                    || type.toString().equals("class java.lang.Long")) {
	                method.invoke(obj, new Random().nextLong());
	            } else if (genericType.toString().contains("$")
	                    && !genericType.toString().contains("java.util.ArrayList")) {
	                String innerClassName = type.toString().replace("class ", "");
	                Object innerObject = fillBeanData(innerClassName);
	                method.invoke(obj, innerObject);
	            } else if (genericType.toString().contains("java.util.ArrayList")) {
//	                setArrayData(obj, genericType, method);
	                setArrayData(obj, genericType, method, fieldName);
	            }
	        }
	        return obj;
	    }

	    private static Object fillBeanData(String className, String childTclassName)
	            throws ClassNotFoundException, InstantiationException,
	            IllegalAccessException, NoSuchMethodException,
	            InvocationTargetException {
	        Class clazz = Class.forName(className);
	        Object obj = clazz.newInstance();
	        Field[] fields = clazz.getDeclaredFields();
	        for (Field field : fields) {
	            String fieldName = field.getName();
	            if (fieldName.equals("serialVersionUID")) {
	                break;
	            }
	            Class<?> type = field.getType();
	            Type genericType = field.getGenericType();
	            String firstLetter = fieldName.substring(0, 1);
	            String methodName = "set" + firstLetter.toUpperCase()
	                    + fieldName.substring(1, fieldName.length());
	            Method method = clazz.getMethod(methodName, type);
	            System.out.println(type);
	            System.out.println(genericType);
	            if (type.toString().equals("int")
	                    || type.toString().equals("class java.lang.Integer")) {
	                method.invoke(obj, new Random().nextInt(100));
	            } else if (genericType.toString().equals("class java.lang.String")) {
	                if (specifyFields.containsKey(fieldName)) {
	                    method.invoke(obj, specifyFields.get(fieldName));
	                } else {
	                    method.invoke(obj,
	                            String.valueOf(new Random().nextInt(100)));
	                }
	            } else if (genericType.toString().equals("double")
	                    || type.toString().equals("class java.lang.Double")) {
	                DecimalFormat df = new DecimalFormat(".00");
	                method.invoke(obj, Double.valueOf(df.format(new Random().nextDouble())));
	            } else if (genericType.toString().equals("float")
	                    || type.toString().equals("class java.lang.Float")) {
	                DecimalFormat df = new DecimalFormat(".00");
	                method.invoke(obj,Float.valueOf(df.format(new Random().nextFloat())));
	            } else if (genericType.toString().equals("boolean")
	                    || type.toString().equals("class java.lang.Boolean")) {
	                method.invoke(obj, new Random().nextBoolean());
	            } else if (genericType.toString().equals("long")
	                    || type.toString().equals("class java.lang.Long")) {
	                method.invoke(obj, new Random().nextLong());
	            } else if (genericType.toString().contains("$")
	                    && !genericType.toString().contains("java.util.ArrayList")) {
	                String innerClassName = type.toString().replace("class ", "");
	                Object innerObject = fillBeanData(innerClassName);
	                method.invoke(obj, innerObject);
	            } else if (genericType.toString().contains("java.util.ArrayList")) {
	                setArrayData(obj, genericType, method, fieldName);
	            } else if (genericType.toString().equals("T")) {
	                Object innerObject = fillBeanData(childTclassName);
	                method.invoke(obj, innerObject);
	            }
	        }
	        return obj;
	    }


	    private static void setArrayData(Object obj, Type genericType, Method method, String fieldName)
	            throws IllegalAccessException, InvocationTargetException,
	            ClassNotFoundException, InstantiationException,
	            NoSuchMethodException {
	        String genericTypeString = genericType.toString();
	        String arrayGenericType = genericTypeString.substring(
	                genericTypeString.indexOf("<") + 1,
	                genericTypeString.indexOf(">"));
	        ArrayList list = new ArrayList();
	        for (int i = 0; i < arrayLength; i++) {
	            if (arrayGenericType.equals("java.lang.String")) {
	                if (specifyFields.containsKey(fieldName)) {
	                    list.add(specifyFields.get(fieldName));
	                } else {
	                    list.add(String.valueOf(new Random().nextInt(100)));
	                }
	            } else if (arrayGenericType.equals("java.lang.Integer")) {
	                list.add(new Random().nextInt(100));
	            } else if (arrayGenericType.equals("java.lang.Long")) {
	                list.add(new Random().nextLong());
	            } else if (arrayGenericType.equals("java.lang.Double")) {
	                DecimalFormat df = new DecimalFormat(".00");
	                list.add(Double.valueOf(df.format(new Random().nextDouble())));
	            } else if (arrayGenericType.equals("java.lang.Float")) {
	                DecimalFormat df = new DecimalFormat(".00");
	                list.add(Float.valueOf(df.format(new Random().nextFloat())));
	            } else if (arrayGenericType.equals("java.lang.Boolean")) {
	                list.add(new Random().nextBoolean());
	            } else if (genericType.toString().contains("java.util.ArrayList")) {
	                String innerClassName = arrayGenericType;
	                Object innerObject = fillBeanData(innerClassName);
	                list.add(innerObject);
	            }
	        }
	        method.invoke(obj, list);
	    }

	    private static void setArrayData(Object obj, Type genericType, Method method)
	            throws IllegalAccessException, InvocationTargetException,
	            ClassNotFoundException, InstantiationException,
	            NoSuchMethodException {
	        String genericTypeString = genericType.toString();
	        String arrayGenericType = genericTypeString.substring(
	                genericTypeString.indexOf("<") + 1,
	                genericTypeString.indexOf(">"));
	        ArrayList list = new ArrayList();
	        for (int i = 0; i < arrayLength; i++) {
	            if (arrayGenericType.equals("java.lang.String")) {
	                list.add(String.valueOf(new Random().nextInt(100)));
	            } else if (arrayGenericType.equals("java.lang.Integer")) {
	                list.add(new Random().nextInt(100));
	            } else if (arrayGenericType.equals("java.lang.Long")) {
	                list.add(new Random().nextLong());
	            } else if (arrayGenericType.equals("java.lang.Double")) {
	                DecimalFormat df = new DecimalFormat("######0.00");
	                list.add(Double.valueOf(df.format(new Random().nextDouble())));
	            } else if (arrayGenericType.equals("java.lang.Float")) {
	                DecimalFormat df = new DecimalFormat("######0.00");
	                list.add(Float.valueOf(df.format(new Random().nextFloat())));
	            } else if (arrayGenericType.equals("java.lang.Boolean")) {
	                list.add(new Random().nextBoolean());
	            } else if (genericType.toString().contains("java.util.ArrayList")) {
	                String innerClassName = arrayGenericType;
	                Object innerObject = fillBeanData(innerClassName);
	                list.add(innerObject);
	            }
	        }
	        method.invoke(obj, list);
	    }
}
