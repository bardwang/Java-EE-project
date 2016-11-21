package com.neu.xunweb.exception;

public class AccException extends Exception
{
	public AccException(String message)
	{
		super(message);
	}
	
	public AccException(String message, Throwable cause)
	{
		super(message,cause);
	}
}