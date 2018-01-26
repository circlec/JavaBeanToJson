import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import bean.CommodityDetailEntity;
import bean.ResponseEntity;

import com.google.gson.Gson;

public class JavaBeanToJsonWithSimulatedData {

	public static String dirPath_desktop = "C:\\Users\\think_admin\\Desktop\\";
	public static String dirPath_E = "E:\\";
	public static HashMap<String,String> sp = new HashMap<>();
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
//		sp.put("image", "image");
//		sp.put("images", "images");
		JavaBeanConfig config = new JavaBeanConfig.Builder()
				.className(ResponseEntity.class.getName())
//				.isArrayWithT(true)
//				.arrayLenght(3)
//				.specifyFields(sp)
				.classTName(CommodityDetailEntity.class.getName())
				.build();
//		ResponseEntity<ArrayList<CommodityDetailEntity>> obj = (ResponseEntity<ArrayList<CommodityDetailEntity>>) SimulateDataUtils
//				.simulateData(config);
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
