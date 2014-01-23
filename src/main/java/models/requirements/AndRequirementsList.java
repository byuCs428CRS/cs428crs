package models.requirements;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
public class AndRequirementsList extends RequirementList {

  public AndRequirementsList() {
    super();
    super.setType(LogicType.AND);
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
