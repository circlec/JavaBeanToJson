import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;

public class JavaBeanToJson {

	public static String dirPath_desktop = "C:\\Users\\think_admin\\Desktop\\";
	public static String dirPath_E = "E:\\";

	public static void main(String[] args) {
		String className = "AppVersionInfo";
		generateJsonFromJavaBean(className);
	}

	private static void generateJsonFromJavaBean(String className) {
		try {
			Object obj = fillBeanData(className);
			Gson gson = new Gson();
			System.out.println(gson.toJson(obj));
			String fileName = className.substring(
					className.lastIndexOf(".") + 1, className.length());
			File file = new File(dirPath_desktop + fileName + ".json");
			if (file.exists()) {
				System.out.println("exists");
				file.delete();
			} else {
				System.out.println("not exists");
			}
			saveFile(dirPath_desktop + fileName + ".json", gson.toJson(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			if (type.toString().equals("int")) {
				method.invoke(obj, new Random().nextInt(100));
			} else if (genericType.toString().equals("class java.lang.String")) {
				method.invoke(obj, String.valueOf(new Random().nextInt(100)));
			} else if (genericType.toString().equals("double")
					|| type.toString().equals("class java.lang.Double")) {
				method.invoke(obj, new Random().nextDouble());
			} else if (genericType.toString().equals("float")
					|| type.toString().equals("class java.lang.Float")) {
				method.invoke(obj, new Random().nextFloat());
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
				setArrayData(obj, genericType, method);
			}
		}
		return obj;
	}

	private static void setArrayData(Object obj, Type genericType, Method method)
			throws IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, InstantiationException,
			NoSuchMethodException {
		String genericTypeString = genericType.toString();
		String arrayGenericType = genericTypeString.substring(
				genericTypeString.indexOf("<") + 1,
				genericTypeString.indexOf(">"));
		if (arrayGenericType.toString().equals("java.lang.String")) {
			ArrayList<String> strList = new ArrayList<>();
			strList.add(String.valueOf(new Random().nextInt(100)));
			strList.add(String.valueOf(new Random().nextInt(100)));
			method.invoke(obj, strList);
		} else if (arrayGenericType.toString().equals("java.lang.Integer")) {
			ArrayList<Integer> strList = new ArrayList<>();
			strList.add(new Random().nextInt(100));
			strList.add(new Random().nextInt(100));
			method.invoke(obj, strList);
		} else if (arrayGenericType.toString().equals("java.lang.Long")) {
			ArrayList<Long> strList = new ArrayList<>();
			strList.add(new Random().nextLong());
			strList.add(new Random().nextLong());
			method.invoke(obj, strList);
		} else if (arrayGenericType.toString().equals("java.lang.Double")) {
			ArrayList<Double> strList = new ArrayList<>();
			strList.add(new Random().nextDouble());
			strList.add(new Random().nextDouble());
			method.invoke(obj, strList);
		} else if (arrayGenericType.toString().equals("java.lang.Float")) {
			ArrayList<Float> strList = new ArrayList<>();
			strList.add(new Random().nextFloat());
			strList.add(new Random().nextFloat());
			method.invoke(obj, strList);
		} else if (arrayGenericType.toString().equals("java.lang.Boolean")) {
			ArrayList<Boolean> strList = new ArrayList<>();
			strList.add(new Random().nextBoolean());
			strList.add(new Random().nextBoolean());
			method.invoke(obj, strList);
		} else if (arrayGenericType.toString().contains("$")
				&& genericType.toString().contains("java.util.ArrayList")) {
			String innerClassName = arrayGenericType.toString();
			Object innerObject = fillBeanData(innerClassName);
			ArrayList list = new ArrayList<>();
			list.add(innerObject);
			list.add(innerObject);
			method.invoke(obj, list);
		}
	}

}
