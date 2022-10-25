package io.github.gabrielbcsilva.bill_survey_api.enumeration;

public enum BarCodeTypeEnum {
    NCP("NCP"),
    NORMAL("NORMAL");

    public static BarCodeTypeEnum getEnum(String type) {
        return type==null?null: (type.equals("NPC")?NCP:(type.equals("NORMAL")?NORMAL:null));
    }

    private String value;
	
	private BarCodeTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
