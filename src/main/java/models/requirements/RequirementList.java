package models.requirements;

import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/21/14
 */
public abstract class RequirementList {
  private LogicType type;
  private String id;
  private String title;
  private String description;
  protected float requiredCount; //For count type subclass. Designed to be count of credits
  private List<Object> fulfillments;

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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
