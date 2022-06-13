import java.util.ArrayList;

public class CollectionObject {
	ArrayList<Object> a = new ArrayList<Object>(2);

	public CollectionObject() {
		for (int i = 0; i < 2; i++) {
			a.add(new Object());
		}
	}
}