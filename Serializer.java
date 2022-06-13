import java.util.ArrayList;
import java.lang.reflect.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;

public class Serializer {
	public static Object getFieldValue(Object source, Field field) {
		field.setAccessible(true);
		try {
			return field.get(source);
		} catch (IllegalAccessException e) {
			return new Object();
		}
	} 

	public static String serializeObject(Object source) {
		ArrayList<Object> objectsToInspect = new ArrayList<Object>();
		ArrayList<String> objects = new ArrayList<String>();

		objectsToInspect.add(source);

		for (int currObject = 0; currObject < objectsToInspect.size(); currObject++) {
			Object src = objectsToInspect.get(currObject);
			Class objectClass = src.getClass();

			String className = objectClass.getName();
			String id = String.valueOf(src.hashCode());
			String type = objectClass.isArray() ? "array" : "object";
			//ArrayList<String> fieldStrings = new ArrayList<String>();
			
			String obj = "{\"class\": \"" + className +
						"\", \"id\": \"" + id +
						"\", \"type\": \"" + type +
						"\", ";

			if (type.equals("array")) {
				int arraySize = Array.getLength(src);

				obj += "\"length\": \"" + String.valueOf(arraySize) +
						"\", \"entries\":[";

				for (int i = 0; i < arraySize; i++) {
					Object a_val = Array.get(src, i);
					if (objectClass.getComponentType().isPrimitive()) {
						obj += "{\"value\":\"" + a_val.toString() + "\"}";
					} else {
						obj += "{\"reference\":\"" + a_val.hashCode() + "\"}";
						if (!objectsToInspect.contains(a_val)) {
							objectsToInspect.add(a_val);
						}
					}

					if (i < arraySize - 1) {
						obj += ", ";
					}
				}
			} else {
				obj += "\"fields\":[";

				Field[] fields = objectClass.getFields();

				for (int i = 0; i < fields.length; i++) {
					Field f = fields[i];
					String fieldName = f.getName();
					String declaringClass = f.getDeclaringClass().getName();
					Class fieldType = f.getType();

					String value = "";
					f.setAccessible(true);
					if (fieldType.isPrimitive()) {
						value = "\"value\":\"";
						value += getFieldValue(src, f).toString() + "\"";
					//} else if (fieldType.isArray()) {

					} else {
						value = "\"reference\":";
						Object val = getFieldValue(src, f);

						if (val == null) {
							value += "\"null\"";
						} else {
							value += "\"" + val.hashCode() + "\"";

							if (!objectsToInspect.contains(val)) {
								objectsToInspect.add(val);
							}
						}
					}

					String fieldOut = "{\"name\":\"" + fieldName + "\",\"declaringclass\":\"" + declaringClass + "\"," + value + "}";
					obj += fieldOut;

					if (i < fields.length - 1) {
						obj += ", ";
					}
				}
			}

			obj += "]}";
			objects.add(obj);
		}
		

		String out = "{\"objects\" : [\n";

		for (int i = 0; i < objects.size(); i++) {
			out += objects.get(i);

			if (i < objects.size() - 1) {
				out += ", \n";
			}
		}

		out += "] }";		

		return out;
	}
}