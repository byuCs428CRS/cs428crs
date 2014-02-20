package service;

import database.MemoryRegistrationStore;
import database.RegistrationStore;
import models.Department;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
public class AdminWebService {
  private RegistrationStore registrationStore;

  public AdminWebService() {
    //ToDo: replace below to read from config and use a factory to choose the store
    registrationStore = MemoryRegistrationStore.getInstance();
  }

  public void resetStore() {
    //for testing reasons only, maybe can be moved to test code
    MemoryRegistrationStore.reset();
  }

  public void addDepartment(Department department) {
    registrationStore.addDepartment(department);
  }
}
