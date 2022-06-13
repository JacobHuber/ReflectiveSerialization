public class ReferenceObject {
	public int x;
	public ReferenceObject sibling;

	public ReferenceObject(int x) {
		this.x = x;
	}

	public ReferenceObject(int x, ReferenceObject r) {
		this.x = x;
		this.sibling = r;
		r.setSibling(this);
	}

	public void setSibling(ReferenceObject r) {
		this.sibling = r;
	}
}