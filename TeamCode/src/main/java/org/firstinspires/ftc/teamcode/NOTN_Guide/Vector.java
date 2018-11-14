package org.firstinspires.ftc.teamcode.NOTN_Guide;

/**
 * Made this for the hell of it. I was bored.
 * For Robotics NOTN_Guide I guess.
 *
 * @author Connor Vann
 */
public class Vector {

  // 3D Coordinates (Z can be whatever if you don't want 3D)
  private double x;
  private double y;
  private double z;

  /**
   * Sets up the initial values of the Vector
   *
   * @param x The initial x coordinate
   * @param y The initial y coordinate
   * @param z The initial z coordinate
   */
  public Vector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Sets up the vector with the initial values at 0, 0, 0
   */
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

  /**
   * Shift the vector along the respective axis
   *
   * @param shiftX How far along the x-axis to move
   * @param shiftY How far along the y-axis to move
   * @param shiftZ How far along the z-axis to move
   */
  public void translate(double shiftX, double shiftY, double shiftZ) {
    x += shiftX;
    y += shiftY;
    z += shiftZ;
  }
}
