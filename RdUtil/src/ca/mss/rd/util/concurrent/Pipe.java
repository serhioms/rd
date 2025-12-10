package ca.mss.rd.util.concurrent;

import java.util.concurrent.Semaphore;

public class Pipe<E> {

	private E e;

	private final Semaphore read = new Semaphore(0);
	private final Semaphore write = new Semaphore(1);

	public final void put(final E e) throws InterruptedException {
		write.acquire();
		this.e = e;
		read.release();
	}

	public final E take() throws InterruptedException {
		read.acquire();
		final E e = this.e;
		write.release();
		return e;
	}

}
