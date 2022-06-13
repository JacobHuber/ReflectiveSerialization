import java.util.ArrayList;
import java.lang.reflect.*;

public class Visualizer {
	ArrayList<Object> viewedObjects = new ArrayList<Object>();

    public void inspect(Object obj) {
        Class c = obj.getClass();
        System.out.print(inspectClass(c, obj, true, 0));
    }

    public String getClassName(Class c, String tabs) {
    	String out = "";

    	// 1. The name of the declaring class
    	String className = c.getName();
    	out += tabs + "CLASS\n";
    	out += tabs + "Class: " + className + "\n";

    	return out;
    }

    public String getArray(Class c, Object obj, boolean recursive, int depth, String tabs) {
    	String out = "";

    	if (c.isArray()) {
    		Class componentType = c.getComponentType();
			out += tabs + " Component Type: " + componentType.toString() + "\n";

			int length = Array.getLength(obj);
			out += tabs + " Length: " + length + "\n";
			out += tabs + " Entries->\n";

			for (int j = 0; j < length; j++) {
				Object arrayValue = Array.get(obj, j);

				if (componentType.isPrimitive() || arrayValue == null) {
					out += tabs + "  Value: " + arrayValue + "\n";
				} else {
					out += tabs + "  Value (ref): " + componentType.getName() + '@' + Integer.toHexString(arrayValue.hashCode()) + "\n";

					if (recursive) {
						out += tabs + "  -> Recursively inspect\n";
						out += inspectClass(componentType, arrayValue, recursive, depth + 1);
					}
				}
			}
    	}

    	return out;
    }

    public String getSuperClass(Class c, Object obj, boolean recursive, int depth, String tabs) {
    	String out = "";
    	// 2. The name of the immediate super-class
    	Class superClass = c.getSuperclass();
    	if (superClass != null) {
    		out += tabs + "SUPERCLASS -> Recursively Inspect\n";
    		out += tabs + "SuperClass: " + superClass.getName() + "\n";


    		// a. Always explore super-class immediately and recursively (even if recursive=false)
    		out += inspectClass(superClass, obj, recursive, depth + 1);	
    	} else {
    		out += tabs + "SuperClass: NONE\n";
    	}

    	return out;
    }

    public String getInterfaces(Class c, Object obj, boolean recursive, int depth, String tabs) {
    	String out = "";

    	// 3. The name of each interface the class implements
    	String className = c.getName();
    	out += tabs + "INTERFACES( " + className + " )\n";
    	
    	Class[] interfaces = c.getInterfaces();
    	if (interfaces.length > 0) {
    		out += tabs + "Interfaces->\n";
    		for (int i = 0; i < interfaces.length; i++) {
	    		out += tabs + " INTERFACE -> Recursively Inspect\n";
	    		String interfaceName = interfaces[i].getName();
	    		out += tabs + " " + interfaceName + "\n";


	    		// a. Always explore interfaces immediately and recursively (even if recursive=false)
	    		out += inspectClass(interfaces[i], obj, recursive, depth + 1);
	    	}
    	} else {
    		out += tabs + "Interfaces-> NONE\n";
    	}

    	return out;
    }

    public String getConstructors(Class c, String tabs) {
    	String out = "";

    	// 4. The constructors the class declares. For each, also find the following: 
    	String className = c.getName();
    	out += tabs + "CONSTRUCTORS( " + className + " )\n";

    	Constructor[] constructors = c.getDeclaredConstructors();
    	if (constructors.length > 0) {
    		out += tabs + "Constructors->\n";
    		for (int i = 0; i < constructors.length; i++) {
    			Constructor con = constructors[i];

    			out += tabs + " CONSTRUCTOR\n";


    			// a. The name
    			String conName = con.getName();
    			out += tabs + "  Name: " + conName + "\n";


    			// b. The exceptions thrown
    			out += getExceptions(con, tabs);


    			// c. The parameter types
    			out += tabs + "  Parameter types:\n";

    			Class[] parameters = con.getParameterTypes();
				for (int j = 0; j < parameters.length; j++) {
					String paramName = parameters[j].toString();
					out += tabs + "   " + paramName + "\n";
				}


    			// d. The modifiers
    			String mod = Modifier.toString(con.getModifiers());
    			out += tabs + "  Modifiers: " + mod + "\n";
    		}
    	} else {
    		out += tabs + "Constructors-> NONE\n";
    	}

    	return out;
    }

    public String getExceptions(Executable e, String tabs) {
    	String out = "";

    	// b. The exceptions thrown
    	Class[] exceptions = e.getExceptionTypes();
		if (exceptions.length > 0) {
			out += tabs + "  Exceptions->\n";

			for (int j = 0; j < exceptions.length; j++) {
				out += tabs + "  " + exceptions[j].toString() + "\n";
			}
		} else {
			out += tabs + "  Exceptions-> NONE\n";
		}

		return out;
    }

    public String getMethods(Class c, String tabs) {
    	String out = "";

    	// 5. The methods the class declares. For each, also find the following:
    	String className = c.getName();
    	out += tabs + "METHODS( " + className + " )\n";

    	Method[] methods = c.getDeclaredMethods();
    	if (methods.length > 0) {
    		out += tabs + "Methods->\n";

    		for (int i = 0; i < methods.length; i++) {
    			Method m = methods[i];
    			out += tabs + " METHOD\n";


    			// a. The name
    			String methodName = m.getName();
    			out += tabs + "  Name: " + methodName + "\n";


    			// b. The exceptions thrown
    			out += getExceptions(m, tabs);


    			// c. The parameter and types
    			Class[] parameters = m.getParameterTypes();
    			if (parameters.length > 0) {
    				out += tabs + "  Parameter types-> \n";
					for (int j = 0; j < parameters.length; j++) {
						String paramName = parameters[j].toString();
						out += tabs + "  " + paramName + "\n";
					}
				} else {
					out += tabs + "  Parameter types-> NONE\n";
				}


				// d. The return-type
				Class returnType = m.getReturnType();
				out += tabs + "  Return type: " + returnType.toString() + "\n";

				// e. The modifiers
				String mod = Modifier.toString(m.getModifiers());
    			out += tabs + "  Modifiers: " + mod + "\n";
    		}
    	} else {
    		out += tabs + "Methods-> NONE\n";
    	}

    	return out;
    }

    public String getFields(Class c, Object obj, boolean recursive, int depth, String tabs) {
    	String out = "";

    	// 6. The fields the class declares. For each, also find the following:
    	String className = c.getName();
    	out += tabs + "FIELDS( " + className + " )\n";

    	Field[] fields = c.getDeclaredFields();

    	if (fields.length > 0) {
    		out += tabs + "Fields->\n";
    		for (int i = 0; i < fields.length; i++) {
    			out += tabs + " FIELD\n";
    			Field f = fields[i];

    			// a. The name
    			String fieldName = f.getName();
    			out += tabs + "  Name: " + fieldName + "\n";

    			// b. The type
    			Class fieldType = f.getType();
    			out += tabs + "  Type: " + fieldType.toString() + "\n";

    			// c. The modifiers
    			String mod = Modifier.toString(f.getModifiers());
    			out += tabs + "  Modifiers: " + mod + "\n";

    			// d. The current value
    			f.setAccessible(true);
    			try {
    				Object value = f.get(obj);
    				if (fieldType.isPrimitive() || value == null) {
    					out += tabs + "  Value: " + value + "\n";
    				} else if (fieldType.isArray()) {
    					out += getArray(fieldType, value, recursive, depth, tabs);
    				} else {
    					// i. If the field is an object reference, and recursive is set to false, then simply
    					//    print out the "reference value" directly (this will be the name of the
    					//    object’s class plus the object’s "identity hash code"
						out += tabs + "  Value (ref): " + fieldType.getName() + '@' + Integer.toHexString(value.hashCode()) + "\n";
    					

						// ii. If the field is an object reference, and recursive is set to true, then
						//     immediately recurse on the object.
    					if (recursive && !viewedObjects.contains(value)) {
    						viewedObjects.add(value);
    						out += tabs + "   -> Recursively inspect\n";
    						out += inspectClass(fieldType, value, recursive, depth + 1);
    					}
    				}
    			} catch (IllegalAccessException e) {
    				out += tabs + "  Value: IllegalAccessException\n";
    			} catch (IllegalArgumentException e) {
    				out += tabs + "  Value: IllegalArgumentException\n";
    			}
    		}
    	} else {
    		out += tabs + "Fields-> NONE\n";
    	}

    	return out;
    }

    private String inspectClass(Class c, Object obj, boolean recursive, int depth) {
    	String out = "";

    	String tabs = "";
    	for (int i = 0; i < depth; i++) {
    		tabs += "\t";
    	}

    	out += getClassName(c, tabs);
    	out += getArray(c, obj, recursive, depth, tabs);
    	//out += getSuperClass(c, obj, recursive, depth, tabs);
    	//out += getInterfaces(c, obj, recursive, depth, tabs);
    	//out += getConstructors(c, tabs);
    	//out += getMethods(c, tabs);
    	out += getFields(c, obj, recursive, depth, tabs);

    	return out;
    }
}