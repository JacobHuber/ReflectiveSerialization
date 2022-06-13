import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.annotation.*;

import java.lang.reflect.*;

public class AS3Tests {
   
	@Test
	public void testSimpleObjectSerialization() {
		SimpleObject so = new SimpleObject(2);
		String serialized = Serializer.serializeObject(so);

		try {
			ObjectMapper m = new ObjectMapper();
			JsonNode jsonOut = m.readTree(serialized);

			jsonOut = jsonOut.get("objects").get(0);
			String className = jsonOut.get("class").asText();
			assertEquals(className, "SimpleObject");

			String type = jsonOut.get("type").asText();
			assertEquals(type, "object");

			int value = jsonOut.get("fields").get(0).get("value").asInt();
			assertEquals(value, 2);
		} catch (JsonProcessingException e) {
			fail();
		}
	}

	@Test
	public void testArrayObjectSerialization() {
		PrimitiveArray so = new PrimitiveArray(1, 2);
		String serialized = Serializer.serializeObject(so);

		try {
			ObjectMapper m = new ObjectMapper();
			JsonNode jsonOut = m.readTree(serialized);

			JsonNode obj1 = jsonOut.get("objects").get(0);
			String className = obj1.get("class").asText();
			assertEquals(className, "PrimitiveArray");

			JsonNode obj2 = jsonOut.get("objects").get(1);

			String type = obj2.get("type").asText();
			assertEquals(type, "array");

			int value1 = obj2.get("entries").get(0).get("value").asInt();
			int value2 = obj2.get("entries").get(1).get("value").asInt();
			assertEquals(value1, 1);
			assertEquals(value2, 2);
		} catch (JsonProcessingException e) {
			fail();
		}
	}

	@Test
	public void testSimpleObjectDeserialization() {
		SimpleObject so = new SimpleObject(2);
		String serialized = Serializer.serializeObject(so);

		Object deserialized = Deserializer.deserialize(serialized);

		try {
			Field f = deserialized.getClass().getField("x");
			int x = f.getInt(deserialized);
			assertEquals(x, 2);
		} catch (Exception e) {
			fail();
		}

	}
}