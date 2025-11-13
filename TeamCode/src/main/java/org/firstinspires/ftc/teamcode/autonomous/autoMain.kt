 package org.firstinspires.ftc.robotcontroller.external.samples

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.abs
import com.qualcomm.robotcore.hardware.DcMotorSimple


@Autonomous(name = "Robot: Auto Drive By Encoder", group = "Robot")
class API : LinearOpMode() {
    /* Declare OpMode members. */
    lateinit var leftDrive: DcMotor
    lateinit var rightDrive: DcMotor

    private val runtime = ElapsedTime()

    override fun runOpMode() {
        // Initialize the drive system variables.

        leftDrive = hardwareMap.get(DcMotor::class.java, "left_drive")
        rightDrive = hardwareMap.get(DcMotor::class.java, "right_drive")

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // When run, this OpMode should start both motors driving forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftDrive.direction = DcMotorSimple.Direction.REVERSE
        rightDrive.direction = DcMotorSimple.Direction.FORWARD

        leftDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        rightDrive.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        leftDrive.mode = DcMotor.RunMode.RUN_USING_ENCODER
        rightDrive.mode = DcMotor.RunMode.RUN_USING_ENCODER

        telemetry.update()

        // Wait for the game to start (driver presses START)
        waitForStart()

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED, 48.0, 48.0, 5.0) // S1: Forward 48 Inches with 5 Sec timeout
        encoderDrive(DRIVE_SPEED, -48.0, -48.0, 5.0) // S2: Reverse 48 Inches with 5 Sec timeout
        encoderDrive(DRIVE_SPEED, 36.0, 36.0, 5.0) // S3: Forward 36 Inches with 5 Sec timeout
        encoderDrive(DRIVE_SPEED,-36.0,-36.0,5.0)
        encoderDrive(DRIVE_SPEED, 36.0, -36.0,5.0)
        telemetry.addData("Path", "Complete")
        telemetry.update()
        sleep(1000) // pause to display final telemetry message.
    }


    fun encoderDrive(
        speed: Double,
        leftInches: Double, rightInches: Double,
        timeoutS: Double
    ) {
        val newLeftTarget: Int
        val newRightTarget: Int

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller

            newLeftTarget =
                leftDrive.getCurrentPosition() + (leftInches * COUNTS_PER_INCH).toInt()
            newRightTarget =
                rightDrive.getCurrentPosition() + (rightInches * COUNTS_PER_INCH).toInt()
            leftDrive.setTargetPosition(newLeftTarget)
            rightDrive.setTargetPosition(newRightTarget)

            // Turn On RUN_TO_POSITION
            leftDrive.mode = DcMotor.RunMode.RUN_TO_POSITION
            rightDrive.mode = DcMotor.RunMode.RUN_TO_POSITION

            // reset the timeout time and start motion.
            runtime.reset()
            leftDrive.power = abs(speed)
            rightDrive.power = abs(speed)

            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                (runtime.seconds() < timeoutS) &&
                (leftDrive.isBusy() && rightDrive.isBusy())
            ) {
                // Display it for the driver.

                telemetry.update()
            }

            // Stop all motion;
            leftDrive.power = 0.0
            rightDrive.power = 0.0

            // Turn off RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER)

            sleep(250) // optional pause after each move.
        }
    }
    companion object {
          // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
        const val COUNTS_PER_MOTOR_REV: Double = 1440.0 // eg: TETRIX Motor Encoder
        const val DRIVE_GEAR_REDUCTION: Double = 1.0 // No External Gearing.
        const val WHEEL_DIAMETER_INCHES: Double = 4.0 // For figuring circumference
        val COUNTS_PER_INCH: Double = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                (WHEEL_DIAMETER_INCHES * 3.1415)
        const val DRIVE_SPEED: Double = 0.6
        const val TURN_SPEED: Double = 0.5
    }
    private fun runAuto(){

    }
}