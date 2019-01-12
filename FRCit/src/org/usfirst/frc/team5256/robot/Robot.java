/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5256.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	private Joystick logitech;
	private Spark RB, LB, RF, LF;
	private TalonSRX leftIntake, rightIntake;
	private SpeedControllerGroup leftSide, rightSide;
	
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		//can
		LB = new Spark(1);
		LF = new Spark(2);
		RB = new Spark(3);
		RF = new Spark(4);
		leftSide 	= new SpeedControllerGroup(LB,LF);
		rightSide 	= new SpeedControllerGroup(RB,RF);
		
		
		// Joystick
		logitech = new Joystick(1);
		
		leftIntake = new TalonSRX(0);
		rightIntake = new TalonSRX(1);
	}

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
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		DifferentialDrive m_drive = new DifferentialDrive(leftSide, rightSide);
		
		if (logitech.getRawButton(1) == true) {
			leftIntake.set(com.ctre.phoenix.motorcontrol.ControlMode.Current, 0.5);
			rightIntake.set(com.ctre.phoenix.motorcontrol.ControlMode.Current,-0.5);
			
		} else {
			leftIntake.set(com.ctre.phoenix.motorcontrol.ControlMode.Disabled, 0);
			rightIntake.set(com.ctre.phoenix.motorcontrol.ControlMode.Disabled, 0);
		}
		
		
		
		if (logitech.getRawButton(2) == true) {
			RB.set(0.5);
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
