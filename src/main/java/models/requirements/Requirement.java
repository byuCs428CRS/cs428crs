package models.requirements;

import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Requirement {
  private String title;
  private String reqId;
  private List<RequirementList>  fulfillments;

  public String getReqId() {
    return reqId;
  }

  public void setReqId(String reqId) {
    this.reqId = reqId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<RequirementList> getFulfillments() {
    return fulfillments;
  }

  public void setFulfillments(List<RequirementList> fulfillments) {
    this.fulfillments = fulfillments;
  }
}
