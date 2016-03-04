package com.linw.mymanager.common.utils;

import java.util.HashMap;

public class RemoteFileHelper {
	
	// <String, Object>: <fileName, RemoteFileObject>
	private static HashMap<String, Object> map = new HashMap<String, Object>();
	
	public static void MarkRemoteFileObject(String fileName, Object remoteObj) {
		if (fileName == null || fileName.length() == 0) {
			return;
		}
		
		map.put(fileName, remoteObj);
	}
	
	public static Object getRemoteFileObject(String fileName) {
		if (fileName == null || fileName.length() == 0) {
			return null;
		}
		
		return map.get(fileName);
	}
}