package models.requirements;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public class Requirement {
  private String title;
  private String reqId;

  @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
  @JsonSubTypes({
      @JsonSubTypes.Type(value=CountRequirementList.class, name="COUNT"),
      @JsonSubTypes.Type(value=AndRequirementList.class, name="AND"),
      @JsonSubTypes.Type(value=OrRequirementList.class, name="OR")
  })
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
