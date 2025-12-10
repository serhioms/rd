package ca.mss.rd.util.model;

public class SortableDef<T extends Number> implements Sortable<T> {

	@Override
	public T sortby() {
		throw new RuntimeException(Sortable.NOT_IMPLEMENTED);
	}

    @Override
	public int compareTo(T t) {
		throw new RuntimeException(Sortable.NOT_IMPLEMENTED);
	}

	@Override
	public int compare(T a, T b) {
		throw new RuntimeException(Sortable.NOT_IMPLEMENTED);
	}


}
