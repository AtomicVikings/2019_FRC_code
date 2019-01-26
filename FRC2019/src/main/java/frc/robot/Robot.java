/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;



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
  private Spark RB, RF, LB, LF, pickupThingy;
  private SpeedControllerGroup left, right;
  private Joystick Logitech;
  private DifferentialDrive drive;
  private Compressor compressor;
  private Solenoid Charizard, Blastoise, Venusuar;
  private Timer timeRemaining;
  //Do you think god stays in heaven in fear of what he's created?

  //mechs
  private PWMVictorSPX intakyThingy, grabbyThingy, shootyThingy;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    //Other Things
    timeRemaining = new Timer();


    //PWM
    RF            = new Spark(0);
    RB            = new Spark(1);
    LF            = new Spark(2);
    LB            = new Spark(3);
    left          = new SpeedControllerGroup(LB, LF);
    right         = new SpeedControllerGroup(RB, RF);
    drive         = new DifferentialDrive(left, right);
    Logitech      = new Joystick(0);

    //CAN
    compressor    = new Compressor(1);

    //Solenoid
    Charizard     = new Solenoid(0);
    Blastoise     = new Solenoid(1);
    Venusuar      = new Solenoid(2);

    //Camera
    CameraServer.getInstance().startAutomaticCapture();

    //initialize mechs
    //PWM's
    shootyThingy    = new PWMVictorSPX(5);
    intakyThingy    = new PWMVictorSPX(4);
    grabbyThingy    = new PWMVictorSPX(6);
    pickupThingy    = new Spark(7);

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
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    timeRemaining.start();

    //Drive Command
    drive.arcadeDrive(Logitech.getRawAxis(1), Logitech.getRawAxis(4));

    //Controller Rumble
    if (timeRemaining == 35) {
      Logitech.setRumble(kLeftRumble, 1)
      Logitech.setRumble(kRightRumble, 1)
    }
    else {

    }

    //Mecanics Commands
    if (Logitech.getRawButton(2) == true) {
      /** 
       When you get to testing with real mechs, make sure 
       to set values that actually work 
      */
      intakyThingy.set(-1);

    } else {
      //mess with this and I castrate you
      intakyThingy.set(0);
    }

    if (Logitech.getRawButton(4) == true) {
      shootyThingy.set(-1);
    } else {
      shootyThingy.set(0);
    }

    if (Logitech.getRawButton(3) == true) {
      grabbyThingy.set(-1);
    } else {
      grabbyThingy.set(0);
    }

    if (Logitech.getRawButton(1) == true) {
      pickupThingy.set(1);
    } else {
      pickupThingy.set(0);
    }

    if (Logitech.getRawButton(6) == true) {
      Charizard.set(true);
      Blastoise.set(true);
      Venusuar.set(true);
    } else {
      Charizard.set(false);
      Blastoise.set(false);
      Venusuar.set(false);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
