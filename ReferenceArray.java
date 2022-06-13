public class ReferenceArray {
	public SimpleObject rArray[] = new SimpleObject[2];

	public ReferenceArray(int x, int y) {
		this.rArray[0] = new SimpleObject(x);
		this.rArray[1] = new SimpleObject(y);
	}
}