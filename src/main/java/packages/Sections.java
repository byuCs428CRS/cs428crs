package packages;

import models.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Nick Humrich
 * @date: 1/18/14
 */
public class Sections {
  private List<Section> sections;


  public Sections() {
    sections = new ArrayList<>();
  }

  public List<Section> getSections() { return sections; }

  public void setSections(List<Section> list) {
    sections = list;
  }

  public void addSection(Section section) {
    sections.add(section);
  }
}
