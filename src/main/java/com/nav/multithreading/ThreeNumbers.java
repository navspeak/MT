package com.nav.multithreading;

public class ThreeNumbers {

	public static void main(String[] args) {
		Drop drop = new Drop();
		drop.put(0);
		Task task1 = new Task(0, drop);
		Task task2 = new Task(1, drop);
		Task task3 = new Task(2, drop);

		new Thread(task1).start();
		new Thread(task2).start();
		new Thread(task3).start();
		
		
	}


	public static class Task implements Runnable {
		private final int pos;
		private Drop drop;

		public Task(int pos, Drop drop) {
			this.pos = pos;
			this.drop = drop;
		}

		@Override
		public void run() {
			int v = 0;
			while(v < 15){
				v = drop.get();
				if (v % 3 == pos) {
					System.out.printf("Thread pos %d %d\n", pos+1, (v % 3) + 1 );
					v++;
				}
				drop.put(v);
			}
		}
	}

	public static class Drop {
		private int val;
		private volatile boolean empty = true;
		public synchronized void put(int v){
			while (empty == false){
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			val = v;
			empty = false;
			notify();

		}

		public synchronized int get(){
			while (empty == true)
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			empty = true;
			int ret = val;
			notify();
			return ret;
		}
	}
}

