package database;

import exceptions.ServerException;
import packages.Majors;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public interface RegistrationStore {

  public Majors getAllMajors() throws ServerException;

}
