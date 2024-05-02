package com.colab1.funfinder.entity;

public enum Emotion {
	worst(1), sad(2), mad(3), normal(4), good(5), fun(6), happy(7);
	
	private final int value;
	
	Emotion(int i){
		this.value = i;
	}
	public int getValue() {
		return value;
	}
}
