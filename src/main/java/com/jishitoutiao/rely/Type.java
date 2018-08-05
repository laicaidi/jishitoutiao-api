package com.jishitoutiao.rely;
/**
 * 资讯类型，所属于domain层InformationOutputArticle
 * @author leitianxiang
 *
 */
public enum Type {
	PICTURE("PICTURE"),TEXT("TEXT"),VIDEO("VIDEO");
	
	private String name;
	
	private Type(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
