import java.util.HashMap;

public final class JavaBeanConfig {
	private final String className;
	private final String classTName;
	private final int arrayLenght;
	private final HashMap<String, String> specifyFields;

	private JavaBeanConfig(Builder builder) {
		this.className = builder.className;
		this.classTName = builder.classTName;
		this.arrayLenght = builder.arrayLenght;
		this.specifyFields = builder.specifyFields;
	}

	public String className() {
		return className;
	}

	public String classTName() {
		return classTName;
	}

	public int arrayLenght() {
		return arrayLenght;
	}

	public HashMap<String, String> specifyFields() {
		return specifyFields;
	}

	public static class Builder {
		private String className;
		private String classTName;
		private int arrayLenght;
		private HashMap<String, String> specifyFields;

		public Builder() {
			this.arrayLenght = 1;
			this.specifyFields = new HashMap<>();
		}

		public Builder className(String className) {
			if (className == null)
				throw new NullPointerException("className == null");
			this.className = className;
			return this;
		}

		public Builder classTName(String classTName) {
			this.classTName = classTName;
			return this;
		}

		public Builder arrayLenght(int arrayLenght) {
			this.arrayLenght = arrayLenght;
			return this;
		}

		public Builder specifyFields(HashMap<String, String> specifyFields) {
			this.specifyFields = specifyFields;
			return this;
		}

		public JavaBeanConfig build() {
			if (className == null)
				throw new IllegalStateException("className == null");
			return new JavaBeanConfig(this);
		}

	}

}
