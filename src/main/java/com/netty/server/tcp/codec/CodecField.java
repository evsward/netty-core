package com.netty.server.tcp.codec;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;

public class CodecField {

	private int type = 0; // 0:String ,1:MessageLite ,2:List

	private String name;
	private Field field;

	private List<CodecField> children;
	
	public CodecField(){
		super();
	}
	
	public CodecField(Field field){
		this.type = 0;
		this.field = field;
	}

	public boolean hasChild() {
		if (children == null || children.size() == 0) {
			return false;
		}
		return true;
	}

	public void addChild(CodecField field) {
		if (this.children == null) {
			this.children = Lists.newArrayList();
		}
		this.children.add(field);
	}

	public List<CodecField> getChildren() {
		return children;
	}

	public void setChildren(List<CodecField> children) {
		this.children = children;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CodecField [type=" + type + ", name=" + name + ", field=" + field + ", children=" + children + "]";
	}
	

}
