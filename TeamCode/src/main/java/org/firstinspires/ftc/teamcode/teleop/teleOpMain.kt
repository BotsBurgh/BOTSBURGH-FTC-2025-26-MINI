package org.firstinspires.ftc.robotcontroller.external.samples

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.TankDrive
import com.qualcomm.robotcore.eventloop.opmode.TeleOp


@TeleOp(name = "teleOpMain", group = "Robot")
class teleOpMain : OpMode() {

    override fun init() {
        // Define and Initialize Motors
        TankDrive.init(this)
    }

    override fun loop() {

        // Controller input
        val drive = -gamepad1.left_stick_y.toDouble()
        val turn = gamepad1.right_stick_x.toDouble()

        TankDrive.power(drive, turn)


        // Outtake control
        if (gamepad1.a) {
            TankDrive.powerOuttake()
        } else {
            TankDrive.stopOuttake()
        }
    }

}
