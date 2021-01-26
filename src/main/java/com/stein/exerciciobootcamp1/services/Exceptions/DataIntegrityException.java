package com.stein.exerciciobootcamp1.services.Exceptions;

public class DataIntegrityException extends RuntimeException{	
	private static final long serialVersionUID = 1L;
	

	public DataIntegrityException(String msg) {
		super(msg);
	}

}