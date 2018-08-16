package com.sharingMemory.producerConsumer.eg1;

public class Produce {
	public static class ProduceBuilder {
		private int instance;
		private Color color;
		
		public ProduceBuilder instance(int instance) {
			this.instance = instance;
			return this;
		}
		
		public ProduceBuilder color(Color color) {
			this.color = color;
			return this;
		}
		
		public Produce build()
		{
			return new Produce(this.instance, this.color);
		}
	}
	
	enum Color {RED, BLUE, GREEN, YELLOW};
	
	private final int instance;
	private final Color color;
	
	private Produce(int instance, Color color)
	{
		this.instance = instance;
		this.color = color;
	}
	
	public int getInstance()
	{
		return this.instance;
	}
	
	public Color getColor()
	{
		return this.color;
	}
}
