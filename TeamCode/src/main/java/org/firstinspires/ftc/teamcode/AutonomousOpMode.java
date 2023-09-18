package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutonomousOpMode extends LinearOpMode {

    // Defines 4 Mecanum Wheel Motors.
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    @Override
    public void runOpMode() throws InterruptedException {
        // Declares motors using IDs that match the configuration on the REV Control Hub.
        motorFrontLeft = hardwareMap.dcMotor.get("motorFrontLeft"); // Front Left Motor.
        motorBackLeft = hardwareMap.dcMotor.get("motorBackLeft"); // Back Left Motor.
        motorFrontRight = hardwareMap.dcMotor.get("motorFrontRight"); // Front Right Motor.
        motorBackRight = hardwareMap.dcMotor.get("motorBackRight"); // Back Right Motor.

        // Sets all motors to use encoders.
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Set the zero power behavior to BRAKE for all motors.
        motorFrontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorFrontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorBackRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Reverse the right side motors since we are using mecanum wheels.
        // Reverse left motors if you are using NeveRests.
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Adds telemetry to the Driver Station.
        telemetry.addData("Status", "Initialized"); // Adds Initialized Status.
        telemetry.update();

        waitForStart(); // Wait for the game to start (driver presses PLAY).

        // Usage of move and turn functions:

        move(180, 0.5);
        move(270, 0.5);
    }

    // Define a method to perform a move action at a specific angle for a given distance.
    private void move(double degrees, double distanceInFeet) {
        // Calculate the encoder ticks based on the desired distance in feet.
        double wheelCircumferenceInFeet = 11.875 / 12.0; // Wheel circumference in feet
        double encoderTicks = (distanceInFeet * 1440.0) / wheelCircumferenceInFeet; // Assuming 1440 encoder ticks per rotation

        // Calculate the motor powers based on the specified degrees
        double radians = Math.toRadians(degrees);
        double x = Math.cos(radians);
        double y = Math.sin(radians);

        // Denominator is the largest motor power (absolute value) or 1.
        double denominator = Math.max(Math.abs(x) + Math.abs(y), 1);
        double frontLeftPower = (x + y) / denominator;
        double backLeftPower = (-x + y) / denominator;
        double frontRightPower = (x - y) / denominator;
        double backRightPower = (-x - y) / denominator;

        // Set target encoder positions for each motor based on distance
        int targetFrontLeft = motorFrontLeft.getCurrentPosition() + (int) (encoderTicks * frontLeftPower);
        int targetBackLeft = motorBackLeft.getCurrentPosition() + (int) (encoderTicks * backLeftPower);
        int targetFrontRight = motorFrontRight.getCurrentPosition() + (int) (encoderTicks * frontRightPower);
        int targetBackRight = motorBackRight.getCurrentPosition() + (int) (encoderTicks * backRightPower);

        // Set target positions for each motor
        motorFrontLeft.setTargetPosition(targetFrontLeft);
        motorBackLeft.setTargetPosition(targetBackLeft);
        motorFrontRight.setTargetPosition(targetFrontRight);
        motorBackRight.setTargetPosition(targetBackRight);

        // Set motor run mode to RUN_TO_POSITION
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Calculate motor powers
        double power = 0.5; // You can adjust the power based on your robot's capabilities
        motorFrontLeft.setPower(power);
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackRight.setPower(power);

        // Wait until the motors reach their targets
        while (motorFrontLeft.isBusy() || motorBackLeft.isBusy() || motorFrontRight.isBusy() || motorBackRight.isBusy()) {
            // Wait for the motors to finish
        }

        // Stop all motors
        setMotorPower(0);
    }



    // Method to turn the robot using encoders.
    private void turn(double degrees) {
        // Calculate the encoder ticks for the turn
        double wheelbaseWidthInFeet = 1.0; // Adjust this based on your robot's wheelbase width
        double wheelCircumferenceInFeet = 11.875 / 12.0; // Wheel circumference in feet
        double encoderTicks = (Math.PI * wheelbaseWidthInFeet * degrees) / 360.0 / wheelCircumferenceInFeet * 1440.0;

        // Set target positions for the motors to perform the turn
        int targetFrontLeft = motorFrontLeft.getCurrentPosition() + (int) encoderTicks;
        int targetBackLeft = motorBackLeft.getCurrentPosition() + (int) encoderTicks;
        int targetFrontRight = motorFrontRight.getCurrentPosition() - (int) encoderTicks;
        int targetBackRight = motorBackRight.getCurrentPosition() - (int) encoderTicks;

        // Set target positions for each motor
        motorFrontLeft.setTargetPosition(targetFrontLeft);
        motorBackLeft.setTargetPosition(targetBackLeft);
        motorFrontRight.setTargetPosition(targetFrontRight);
        motorBackRight.setTargetPosition(targetBackRight);

        // Set motor run mode to RUN_TO_POSITION
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // Calculate motor powers
        double power = 0.5; // You can adjust the power based on your robot's capabilities
        motorFrontLeft.setPower(power);
        motorBackLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackRight.setPower(power);

        // Wait until the motors reach their targets
        while (motorFrontLeft.isBusy() || motorBackLeft.isBusy() || motorFrontRight.isBusy() || motorBackRight.isBusy()) {
            // Wait for the motors to finish
        }

        // Stop all motors
        setMotorPower(0);
    }

    // Define methods to set motor powers
    private void setMotorPower(double power) {
        motorFrontLeft.setPower(-power);
        motorBackLeft.setPower(-power);
        motorFrontRight.setPower(-power);
        motorBackRight.setPower(-power);
    }
}