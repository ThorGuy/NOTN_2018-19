package org.firstinspires.ftc.teamcode.NOTN_Guide;

public class Vector {

  private double x;
  private double y;
  private double z;

  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector() {
    x = 0;
    y = 0;
    z = 0;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setZ(double z) {
    this.z = z;
  }

  public void translate(double shiftX, double shiftY, double shiftZ) {
    x += shiftX;
    y += shiftY;
    z += shiftZ;
  }
}
