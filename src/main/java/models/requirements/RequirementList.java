package models.requirements;

import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
public abstract class RequirementList {
  private LogicType type;
  private List<Object> fulfillments;
  protected float requiredCount; //For count type subclass. Designed to be count of credits

  public LogicType getType() {
    return type;
  }

  public void setType(LogicType type) {
    this.type = type;
  }

  public List<Object> getFulfillments() {
    return fulfillments;
  }

  public void setFulfillments(List<Object> fulfillments) {
    this.fulfillments = fulfillments;
  }

  public abstract float getRequiredCount();

  public abstract void setRequiredCount(float count);
}
