package models.requirements;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
public class CountRequirementList extends RequirementList {

  public CountRequirementList() {
    super();
    super.setType(LogicType.COUNT);
  }

  public float getRequiredCount() {
    return requiredCount;
  }

  public void setRequiredCount(float requiredCount) {
    this.requiredCount = requiredCount;
  }
}
