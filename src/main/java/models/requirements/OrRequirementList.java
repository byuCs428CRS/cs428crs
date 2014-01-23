package models.requirements;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
public class OrRequirementList extends RequirementList {

  public OrRequirementList() {
    super();
    super.setType(LogicType.OR);
  }

  @Override
  public float getRequiredCount() {
    return 0;
  }

  @Override
  public void setRequiredCount(float count) {
    requiredCount = 0;
  }
}
