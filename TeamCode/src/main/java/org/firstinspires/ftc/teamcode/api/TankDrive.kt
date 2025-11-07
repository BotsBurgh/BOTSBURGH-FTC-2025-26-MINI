
    package org.firstinspires.ftc.teamcode
    import com.qualcomm.robotcore.eventloop.opmode.Disabled
    import com.qualcomm.robotcore.eventloop.opmode.OpMode
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp
    import com.qualcomm.robotcore.hardware.DcMotor
    import org.firstinspires.ftc.teamcode.core.API

    /*
    left controls left motor
    right controls right motor
    push joystick forward --> motor drives forward
     */
    @TeleOp(name = "Robot: Teleop Tank", group = "Robot")
    class API : OpMode() {
        /* Declare OpMode. mem bers. */
        lateinit var leftDrive: DcMotor
        lateinit var rightDrive: DcMotor
        lateinit var outtakeMotor: DcMotor

        /*
         * Code to run ONCE when the driver hits INIT
         */
        val OUTTAKE_POWER: Double = 0.50 // Run outtake at 50% power
        override fun init() {
            // Define and Initialize Motors
            leftDrive = hardwareMap.get(DcMotor::class.java, "leftDrive")
            rightDrive = hardwareMap.get(DcMotor::class.java, "rightDrive")
            outtakeMotor = hardwareMap.get(DcMotor::class.java, "outtakeMotor")
            // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
            // Pushing the left and right sticks forward MUST make robot go forward. So adjust these two lines based on your first test drive.
            // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
            /*
            leftDrive!!.setDirection(DcMotor.Direction.REVERSE)
            rightDrive!!.setDirection(DcMotor.Direction.FORWARD)
            */
            // If there are encoders connected, switch to RUN_USING_ENCODER mode for greater accuracy
            // leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            // rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            // Define and initialize ALL installed servos.
            // Send telemetry message to signify robot waiting;
            telemetry.addData(">", "Robot Ready.  Press START.") //
        }

        override fun loop() {
            val left: Double
            val right: Double

            // Run wheels in tank mode (note: The joystick goes negative when pushed forward, so negate it)
            left = -gamepad1.left_stick_y.toDouble()
            right = -gamepad1.right_stick_y.toDouble()

            leftDrive.setPower(left)
            rightDrive.setPower(right)

            if (gamepad1.a) {
                outtakeMotor.setPower(OUTTAKE_POWER)
            } else {
                outtakeMotor.setPower(0.0)
            }
            // Use gamepad left & right Bumpers to open and close the claw
            // Send telemetry message to signify robot running;
            telemetry.addData("left", "%.2f", left)
            telemetry.addData("right", "%.2f", right)
        }
    }