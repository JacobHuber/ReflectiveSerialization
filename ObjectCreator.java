import java.io.*;

public class ObjectCreator {
	private BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

	private String getUserInput() {
		try {
			return this.console.readLine();
		} catch (IOException e) {
			System.out.println("Error reading user input");
		}
		return "";
	}

	public Object createSimpleObject() {
		System.out.println("Choose an int value for the object's field");
		String userChoice = getUserInput();
		int a;
		try {
			a = Integer.parseInt(userChoice);
		} catch (NumberFormatException e) {
			System.out.println("Not an integer: setting value to 0");
			a = 0;
		}

		return new SimpleObject(a);
	}

	public Object createReferenceObject() {
		System.out.println("Choose an int value for the object's field");
		String userChoice = getUserInput();
		int a;
		try {
			a = Integer.parseInt(userChoice);
		} catch (NumberFormatException e) {
			System.out.println("Not an integer: setting value to 0");
			a = 0;
		}

		System.out.println("Choose an int value for the reference object's field");
		userChoice = getUserInput();
		int b;
		try {
			b = Integer.parseInt(userChoice);
		} catch (NumberFormatException e) {
			System.out.println("Not an integer: setting value to 0");
			b = 0;
		}

		ReferenceObject brother = new ReferenceObject(a);
		ReferenceObject sister = new ReferenceObject(b, brother);
		return brother;
	}

	public Object createPrimitiveArray() {
		System.out.println("Choose an int value for array[0]");
		String userChoice = getUserInput();
		int a;
		try {
			a = Integer.parseInt(userChoice);
		} catch (NumberFormatException e) {
			System.out.println("Not an integer: setting value to 0");
			a = 0;
		}
		
		System.out.println("Choose an int value for array[1]");
		userChoice = getUserInput();
		int b;
		try {
			b = Integer.parseInt(userChoice);
		} catch (NumberFormatException e) {
			System.out.println("Not an integer: setting value to 0");
			b = 0;
		}
		return new PrimitiveArray(a,b);
	}

	public Object createReferenceArray() {
		System.out.println("Choose an int value for array[0].x");
		String userChoice = getUserInput();
		int a;
		try {
			a = Integer.parseInt(userChoice);
		} catch (NumberFormatException e) {
			System.out.println("Not an integer: setting value to 0");
			a = 0;
		}
		
		System.out.println("Choose an int value for array[1].x");
		userChoice = getUserInput();
		int b;
		try {
			b = Integer.parseInt(userChoice);
		} catch (NumberFormatException e) {
			System.out.println("Not an integer: setting value to 0");
			b = 0;
		}

		return new ReferenceArray(a,b);
	}

	public Object createCollectionObject() {
		System.out.println("Creating Collection Object");
		
		return new CollectionObject();
	}

	public Object createObject() {
		System.out.println("Choose which object you'd like to create");
		System.out.println("1 - Simple Object");
		System.out.println("2 - Reference Object");
		System.out.println("3 - Array Object");
		System.out.println("4 - Array Reference Object");
		System.out.println("5 - Collection Object");
		System.out.println("6 - [SEND OBJECTS]");
		

		String userChoice = getUserInput();
		// Error checking later

		switch (userChoice) {
			case "1":
				return createSimpleObject();
			case "2":
				return createReferenceObject();
			case "3":
				return createPrimitiveArray();
			case "4":
				return createReferenceArray();
			case "5":
				return createCollectionObject();
			default:
				break;
		}

		return null;
	}
}