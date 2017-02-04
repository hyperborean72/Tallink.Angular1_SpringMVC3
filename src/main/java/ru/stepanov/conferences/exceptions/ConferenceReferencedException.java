package ru.stepanov.conferences.exceptions;

public class ConferenceReferencedException extends Exception{

  public ConferenceReferencedException(int id) {
    super("Conference with id=" + id + " has registered participants. Un-register participants first");
  }
}
