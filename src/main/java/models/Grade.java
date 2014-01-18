package models;

/**
 * @autor: Nick Humrich
 * @date: 1/17/14
 */
public enum Grade {
  A_PLUS("A+"),
  A("A"),
  A_MINUS("A-"),
  B_PLUS("B+"),
  B("B"),
  B_MINUS("B-"),
  C_PLUS("C+"),
  C("C"),
  C_MINUS("C-"),
  D_PLUS("D+"),
  D("D"),
  D_MINUS("D-"),
  F_PLUS("E+"),
  F("E"),
  F_MINUS("E-");

  private String name;

  Grade(String s) {
    this.name = s;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
