import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import bean.CommodityDetailEntity;
import bean.ResponseEntity;

import com.google.gson.Gson;

public class JavaBeanToJsonWithSimulatedData {

	public static String dirPath_desktop = "C:\\Users\\think_admin\\Desktop\\";
	public static String dirPath_E = "E:\\";

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		JavaBeanConfig config = new JavaBeanConfig.Builder()
				.className(ResponseEntity.class.getName())
				.classTName(CommodityDetailEntity.class.getName()).build();
		ResponseEntity<CommodityDetailEntity> obj = (ResponseEntity<CommodityDetailEntity>) SimulateDataUtils
				.simulateData(config);
		toJsonFile(ResponseEntity.class.getName(), obj);
	}

	public static void toJsonFile(String className, Object obj) {
		Gson gson = new Gson();
		System.out.println(gson.toJson(obj));
		String fileName = className.substring(className.lastIndexOf(".") + 1,
				className.length());
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
}
