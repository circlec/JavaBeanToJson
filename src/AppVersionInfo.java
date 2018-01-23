

import java.util.ArrayList;

public class AppVersionInfo {

	private int errorCode;
	private String errorMessage;
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	private ResultEntity result;
	private ArrayList<String> imgs;
	private ArrayList<ResultEntity> resultEntities;

	public ArrayList<ResultEntity> getResultEntities() {
		return resultEntities;
	}

	public void setResultEntities(ArrayList<ResultEntity> resultEntities) {
		this.resultEntities = resultEntities;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ResultEntity getResult() {
		return result;
	}

	public void setResult(ResultEntity result) {
		this.result = result;
	}

	public ArrayList<String> getImgs() {
		return imgs;
	}

	public void setImgs(ArrayList<String> imgs) {
		this.imgs = imgs;
	}

	public static class ResultEntity {
		/**
		 * id : 11 versionName : 1.0.1 versionCode : 1 versionHints : 2
		 * versionLink : https://www.baidu.com versionContent : 1、更新APP 2、更新微信
		 * 3、更新网页
		 */

		private int id;
		private String versionName;
		private int versionCode;
		private int versionHints;
		private String versionLink;
		private String versionContent;
		private Test test;
		private ArrayList<Test> tests;
		

		public ArrayList<Test> getTests() {
			return tests;
		}

		public void setTests(ArrayList<Test> tests) {
			this.tests = tests;
		}

		public Test getTest() {
			return test;
		}

		public void setTest(Test test) {
			this.test = test;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getVersionName() {
			return versionName;
		}

		public void setVersionName(String versionName) {
			this.versionName = versionName;
		}

		public int getVersionCode() {
			return versionCode;
		}

		public void setVersionCode(int versionCode) {
			this.versionCode = versionCode;
		}

		public int getVersionHints() {
			return versionHints;
		}

		public void setVersionHints(int versionHints) {
			this.versionHints = versionHints;
		}

		public String getVersionLink() {
			return versionLink;
		}

		public void setVersionLink(String versionLink) {
			this.versionLink = versionLink;
		}

		public String getVersionContent() {
			return versionContent;
		}

		public void setVersionContent(String versionContent) {
			this.versionContent = versionContent;
		}

		public static class Test {
			private int testId;

			public int getTestId() {
				return testId;
			}

			public void setTestId(int testId) {
				this.testId = testId;
			}

		}
	}
}
