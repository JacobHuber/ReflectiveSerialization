import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;

public class Deserializer {
	public static Object createSimpleObject(JsonNode source) {
		int x = source.get("objects").get(0).get("fields").get(0).get("value").asInt();
		return new SimpleObject(x);
	}

	public static Object createReferenceObject(JsonNode source) {
		int a = source.get("objects").get(0).get("fields").get(0).get("value").asInt();
		int b = source.get("objects").get(1).get("fields").get(0).get("value").asInt();
		String siblingId = source.get("objects").get(0).get("fields").get(1).get("reference").asText();
		

		ReferenceObject first = new ReferenceObject(b);
		ReferenceObject second = new ReferenceObject(a, first);
		return second;
	}

	public static Object createPrimitiveArray(JsonNode source) {
		int value1 = source.get("objects").get(1).get("entries").get(0).get("value").asInt();
		int value2 = source.get("objects").get(1).get("entries").get(1).get("value").asInt();
		return new PrimitiveArray(value1, value2);
	}

	public static Object createReferenceArray(JsonNode source) {
		int value1 = source.get("objects").get(2).get("fields").get(0).get("value").asInt();
		int value2 = source.get("objects").get(3).get("fields").get(0).get("value").asInt();
		return new ReferenceArray(value1, value2);
	}

	public static Object createCollectionObject(JsonNode source) {
		return new CollectionObject();	
	}

	public static Object deserialize(String source) {
		ObjectMapper m = new ObjectMapper();
		JsonNode jsonOut;
		try {
			jsonOut = m.readTree(source);
			//System.out.println(jsonOut.toPrettyString());
			
			String className = jsonOut.get("objects").get(0).get("class").asText();
			//System.out.println(className);

			switch (className) {
				case "SimpleObject":
					return createSimpleObject(jsonOut);
				case "ReferenceObject":
					return createReferenceObject(jsonOut);
				case "PrimitiveArray":
					return createPrimitiveArray(jsonOut);
				case "ReferenceArray":
					return createReferenceArray(jsonOut);
				case "CollectionObject":
					return createCollectionObject(jsonOut);
				default:
					break;
			}
		} catch (JsonProcessingException e) {
			System.out.println("JSON Processing Exception");
		}

		return new Object();
	}
}