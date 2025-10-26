package org.firstinspires.ftc.robotcontroller.external.samples

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import kotlin.math.abs
import com.qualcomm.robotcore.hardware.DcMotorSimple
@Autonomous(name = "Robot: Auto Drive By Encoder", group = "Robot")
class RobotAutoDriveByEncoder_Linear : LinearOpMode() {
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
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE)
        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD)

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER)

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER)

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData(
            "Starting at", "%7d :%7d",
            leftDrive.getCurrentPosition(),
            rightDrive.getCurrentPosition()
        )
        telemetry.update()

        // Wait for the game to start (driver presses START)
        waitForStart()

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        encoderDrive(DRIVE_SPEED, 48.0, 48.0, 5.0) // S1: Forward 47 Inches with 5 Sec timeout
        encoderDrive(DRIVE_SPEED, -48.0, -48.0, 5.0) // S3: Reverse 24 Inches with 4 Sec timeout
        telemetry.addData("Path", "Complete")
        telemetry.update()
        sleep(1000) // pause to display final telemetry message.
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the OpMode running.
     */
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
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION)
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION)

            // reset the timeout time and start motion.
            runtime.reset()
            leftDrive.setPower(abs(speed))
            rightDrive.setPower(abs(speed))

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

                telemetry.addData("Running to", " %7d :%7d", newLeftTarget, newRightTarget)
                telemetry.addData(
                    "Currently at", " at %7d :%7d",
                    leftDrive.getCurrentPosition(), rightDrive.getCurrentPosition()
                )
                telemetry.update()
            }

            // Stop all motion;
            leftDrive.setPower(0.0)
            rightDrive.setPower(0.0)

            // Turn off RUN_TO_POSITION
            leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER)
            rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER)

            sleep(250) // optional pause after each move.
        }
    }
    companion object {
        // Calculate the COUNTS_PER_INCH for your specific drive train.
        // Go to your motor vendor website to determine your motor's COUNTS_PER_MOTOR_REV
        // For external drive gearing, set DRIVE_GEAR_REDUCTION as needed.
        // For example, use a value of 2.0 for a 12-tooth spur gear driving a 24-tooth spur gear.
        // This is gearing DOWN for less speed and more torque.
        // For gearing UP, use a gear ratio less than 1.0. Note this will affect the direction of wheel rotation.
        const val COUNTS_PER_MOTOR_REV: Double = 1440.0 // eg: TETRIX Motor Encoder
        const val DRIVE_GEAR_REDUCTION: Double = 1.0 // No External Gearing.
        const val WHEEL_DIAMETER_INCHES: Double = 4.0 // For figuring circumference
        val COUNTS_PER_INCH: Double = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
                (WHEEL_DIAMETER_INCHES * 3.1415)
        const val DRIVE_SPEED: Double = 0.6
        const val TURN_SPEED: Double = 0.5
    }
}