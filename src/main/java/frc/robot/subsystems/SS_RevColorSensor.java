package frc.robot.subsystems;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.C_TestColorSensor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SS_RevColorSensor extends Subsystem {

  //need to be tuned
  private static final int RED_THRESHOLD = 20;
  private static final int GREEN_THRESHOLD = 20;
  private static final int BLUE_THRESHOLD = 20;

  private static final int CMD = 0x80;
  private static final int MULTI_BYTE_BIT = 0x20;

  private static final int ENABLE_REGISTER  = 0x00;
  private static final int ATIME_REGISTER   = 0x01;
  private static final int PPULSE_REGISTER  = 0x0E;

  private static final int ID_REGISTER     = 0x12;
  private static final int CDATA_REGISTER  = 0x14;
  private static final int RDATA_REGISTER  = 0x16;
  private static final int GDATA_REGISTER  = 0x18;
  private static final int BDATA_REGISTER  = 0x1A;
  private static final int PDATA_REGISTER  = 0x1C;

  private static final int PON   = 0b00000001;
  private static final int AEN   = 0b00000010;
  private static final int PEN   = 0b00000100;
  private static final int WEN   = 0b00001000;
  private static final int AIEN  = 0b00010000;
  private static final int PIEN  = 0b00100000;

  private final double integrationTime = 10;

 
  private I2C revColor;

  private ByteBuffer buffer = ByteBuffer.allocate(8);

  private short red = 0, green = 0, blue = 0, prox = 0;

  public SS_RevColorSensor(I2C.Port port) {
    buffer.order(ByteOrder.LITTLE_ENDIAN);
      revColor = new I2C(port, 0x39); //0x39 is the address of the color sensor
      
      revColor.write(CMD | 0x00, PON | AEN | PEN);
      
      revColor.write(CMD | 0x01, (int) (256-integrationTime/2.38)); //configures the integration time (time for updating color data)
      revColor.write(CMD | 0x0E, 0b1111);
      
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_TestColorSensor());
  }

  public int status() {
    buffer.clear();
    revColor.read(CMD | 0x13, 1, buffer);
    return buffer.get(0);
  }

  private void read() {
    buffer.clear();
    revColor.read(CMD | MULTI_BYTE_BIT | RDATA_REGISTER, 8, buffer);
  }

  public int getRed() {
    read();
    red = buffer.getShort(0);
    if(red < 0) { 
      red += 0b10000000000000000; 
    }

    return red;
  }

  public int getGreen() {
    read();
    green = buffer.getShort(2);
    if(green < 0) { 
      green += 0b10000000000000000; 
    }

    return green;
  }

  public int getBlue() {
    read();
    blue = buffer.getShort(4); 
    if(blue < 0) {
        blue += 0b10000000000000000; 
    }

    return blue;
  }

  public int getProximity() {
    read();
    prox = buffer.getShort(6); 
    if(prox < 0) { 
      prox += 0b10000000000000000; 
    }
    return prox;
  }

  public int getWhite() {
    double avarage = getRed() + getGreen() + getBlue();
    return (int)avarage / 3;
  }

  public boolean isRed() {
    return getRed() >= RED_THRESHOLD;
  }

  public boolean isGreen() {
    return getGreen() >= GREEN_THRESHOLD;
  }

  public boolean isBlue() {
    return getBlue() >= BLUE_THRESHOLD;
  }

  public boolean isWhite() {
    return isRed() && isGreen() && isBlue();
  }
}