package models;

import models.requirements.Requirement;

import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
public class Major {
  private String title;
  private String shortCode;
  private List<Requirement> requirements;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getShortCode() {
    return shortCode;
  }

  public void setShortCode(String shortCode) {
    this.shortCode = shortCode;
  }

  public List<Requirement> getRequirements() {
    return requirements;
  }

  public void setRequirements(List<Requirement> requirements) {
    this.requirements = requirements;
  }
}
