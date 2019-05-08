package io.pivotal;

public class User1 {

	private String name;

	public User1() {
		super();
	}

	public User1(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User1 [name=" + this.name + "]";
	}

}
