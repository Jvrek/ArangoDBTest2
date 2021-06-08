package models;

import java.io.Serializable;

public class Cinema implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Double workers;

	public Cinema(String name, Double workers) {
		this.name = name;
		this.workers = workers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWorkers() {
		return workers;
	}

	public void setWorkers(Double workers) {
		this.workers = workers;
	}


	@Override
	public String toString() {
		return "Cinema{" +
				"name='" + name + '\'' +
				", workers=" + workers +
				'}';
	}
}
