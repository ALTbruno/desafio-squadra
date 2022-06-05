package com.altbruno.desafiosquadra.model;

public enum Status {
	ATIVO (1),
	INATIVO (2);

	private final Integer id;

	private Status(Integer statusId) {
		this.id = statusId;
	}

	public Integer getId() {
		return id;
	}
}
