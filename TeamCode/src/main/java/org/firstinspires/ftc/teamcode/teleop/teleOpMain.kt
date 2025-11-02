package org.firstinspires.ftc.robotcontroller.external.samples

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.DcMotorSimple

/*
 * This OpMode executes a   Tank Drive control TeleOp a direct drive robot
 * The code is structured as an Iterative OpMode
 *
 * In this mode, the left and right joysticks control the left and right motors respectively.
 * Pushing a joystick forward will make the attached motor drive forward.
 * It raises and lowers the claw using the Gamepad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */
class RobotTeleopTank_Iterative : OpMode() {
    /* Declare OpMode members. */
    val OUTTAKE_POWER: Double = 0.50 // Run outtake motor at 50% power
    lateinit var leftDrive: DcMotor
    lateinit var rightDrive: DcMotor
    lateinit var outtakeMotor: DcMotor
    lateinit var preOuttake1: Servo
    lateinit var preOuttake2: Servo
    /*
     * Code to run ONCE when the driver hits INIT
     */
    override fun init() {
        // Define and Initialize Motors
        leftDrive = hardwareMap.get(DcMotor::class.java, "leftDrive")
        rightDrive = hardwareMap.get(DcMotor::class.java, "rightDrive")
        outtakeMotor = hardwareMap.get(DcMotor::class.java, "outtakeMotor")
        preOuttake1 = hardwareMap.get(Servo::class.java, "preOuttake1")
        preOuttake2 = hardwareMap.get(Servo::class.java, "preOuttake2")
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left and right sticks forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftDrive.direction = DcMotorSimple.Direction.REVERSE
        rightDrive.direction = DcMotorSimple.Direction.FORWARD
        // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
        // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Send telemetry message to signify robot waiting;
        telemetry.addData(">", "Robot Ready.  Press START.") //
    }


    /*
     * Code to run REPEATEDLY after the driver hits START but before they hit STOP
     */
    override fun loop() {
        val leftWheel: Double
        val rightWheel: Double

        // Run wheels in tank mode (note: The joystick goes negative when pushed forward, so negate it)
        leftWheel = -gamepad1.left_stick_y.toDouble()
        rightWheel = -gamepad1.right_stick_y.toDouble()
        leftDrive.setPower(leftWheel)
        rightDrive.setPower(rightWheel)

        // Move both servos to new position.  Assume servos are mirror image of each other.

        if(gamepad1.a) {
            outtakeMotor.setPower(OUTTAKE_POWER)
        }
        else{
            outtakeMotor.setPower(0.0)
        }

        if (gamepad1.dpad_up){
            preOuttake1.direction = Servo.Direction.FORWARD
            preOuttake2.direction = Servo.Direction.REVERSE}
        else{
            preOuttake1.position = 0.5
            preOuttake2.position = 0.5
        }

        // Send telemetry message to signify robot running;
        telemetry.addData("leftDrive", "%.2f", leftDrive)
        telemetry.addData("rightDrive", "%.2f", rightDrive)
    }

}