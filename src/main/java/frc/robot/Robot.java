/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  private WPI_VictorSPX leftFront, leftRear, rightFront, rightRear;
  private SpeedControllerGroup left, right;
  private Joystick driver, mechanic;
  private DifferentialDrive drive;
  private Spark ballIntake, hatchMech;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //Drive
      //I have to change these using Pheonix software to work with CAN
      leftFront = new WPI_VictorSPX(1);
      leftRear = new WPI_VictorSPX(2);
      rightFront = new WPI_VictorSPX(3);
      rightRear = new WPI_VictorSPX(4);
      left = new SpeedControllerGroup(leftFront, leftRear);
      right  = new SpeedControllerGroup(rightFront, rightRear);
      drive = new DifferentialDrive(left, right);
      driver = new Joystick(0);
      mechanic =  new Joystick(1);

    //Control
      ballIntake = new Spark(0);
      hatchMech = new Spark(1);

    //Camera
    CameraServer.getInstance().startAutomaticCapture();

    

  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    driveyThingy();
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    driveyThingy();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void driveyThingy() {
    //Drive
      drive.arcadeDrive(driver.getRawAxis(1), driver.getRawAxis(4));

    //Mecanics
      if (mechanic.getRawButton(1) == true) {
        //This is the ball intake on
        //this may spin in the wrong direction, fix with either code or current polarity.
        ballIntake.set(1);
      } else {
        ballIntake.set(0);
      } if (mechanic.getRawButton(2) == true) {
        //this is ball intake shoot
        ballIntake.set(-1);
      } else {
        ballIntake.set(0);
      } if (mechanic.getRawButton(5) == true) {
        //this is hatch up
        hatchMech.set(1);
      } else {
        hatchMech.set(0);

      } if (mechanic.getRawButton(6) == true) {
        //this is hatch down
        hatchMech.set(-1);
      } else {
        hatchMech.set(0);
      }


  }
}
