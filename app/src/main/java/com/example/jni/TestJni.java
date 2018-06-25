package com.example.jni;

public class TestJni {
	static {
		System.loadLibrary("akaenc");
		}
	public native static byte[] encoder(int ano,int bno,int room,byte uid,int count,byte[] key);
	 
}
