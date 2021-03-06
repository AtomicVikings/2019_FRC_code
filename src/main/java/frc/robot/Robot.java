package frc.robot;

import edu.wpi.first.cameraserver.*;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  private WPI_VictorSPX leftFront, leftRear, rightFront, rightRear;
  private SpeedControllerGroup left, right;
  private Joystick driver, mechanic;
  private DifferentialDrive drive;
  private WPI_TalonSRX ballIntake, hatchMech;
  private DigitalInput controllers;

  @Override
  public void robotInit() {
    //Drive
      leftFront     = new WPI_VictorSPX(2);
      leftRear      = new WPI_VictorSPX(4);
      rightFront    = new WPI_VictorSPX(3);
      rightRear     = new WPI_VictorSPX(1);
      left          = new SpeedControllerGroup(leftFront, leftRear);
      right         = new SpeedControllerGroup(rightFront, rightRear);
      drive         = new DifferentialDrive(left, right);
      driver        = new Joystick(0);
      mechanic      = new Joystick(1);
      controllers   = new DigitalInput(0);
    //Control
      ballIntake    = new WPI_TalonSRX(7);
      hatchMech     = new WPI_TalonSRX(8);

    //Camera
    CameraServer.getInstance().startAutomaticCapture(0);
    CameraServer.getInstance().startAutomaticCapture(1);

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    
  }

  @Override
  public void autonomousPeriodic() {
    driveyThingy();
  }

  @Override
  public void teleopPeriodic() {
    driveyThingy();
  }

  @Override
  public void testPeriodic() {
  }

  public void driveyThingy() {
  //Drive
    boolean driveSwitch = false;
    if (driver.getRawButton(3) == true) {
      driveSwitch = true;
    } else {
      driveSwitch = false;
    } 

    
    if (driveSwitch == true) {
      drive.arcadeDrive(driver.getRawAxis(1) * -1 * .8, driver.getRawAxis(4) * -1);
    } else {
      drive.arcadeDrive(driver.getRawAxis(1) * .8, driver.getRawAxis(4) * -1);
    }
      
  //Mecanics
    //This is for 1/2 controller input
    if (controllers == true) {
      //Ball Intake for Driver
      if (driver.getRawButton(1) == true) {
        ballIntake.set(-.5);        
      } else if (driver.getRawButton(2) == true) {
        ballIntake.set(.5);
      } else {
        ballIntake.set(0);
      }
      //Hatch for Driver
      if (driver.getRawButton(6) == true) {
        hatchMech.set(-1);
      } else if (driver.getRawButton(6) == true) {
        hatchMech.set(1);
      } else {
        hatchMech.set(0);
      }
    } else if (controllers == false) {
      //Ball Intake
      if (mechanic.getRawButton(1) == true) {
        ballIntake.set(-.5);
        } else if (mechanic.getRawButton(2) == true) {
          ballIntake.set(.5);
        } else {
          ballIntake.set(0);
        }
      //Hatch Mechanism
      if (mechanic.getRawButton(6) == true) {
        hatchMech.set(-1);
      } else if (mechanic.getRawButton(6) == true) {
        hatchMech.set(1);
      } else {
        hatchMech.set(0);
      }
    }  
  }
}
