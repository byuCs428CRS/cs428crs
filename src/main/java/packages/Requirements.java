package packages;

import models.requirements.Requirement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/23/14
 */
public class Requirements {
  private List<Requirement> requirements;

  public Requirements() {
    requirements = new ArrayList<>();
  }

  public List<Requirement> getRequirements() {
    return requirements;
  }

  public void setRequirements(List<Requirement> requirements) {
    this.requirements = requirements;
  }

  public void addRequirement(Requirement r) {
    requirements.add(r);
  }
}
