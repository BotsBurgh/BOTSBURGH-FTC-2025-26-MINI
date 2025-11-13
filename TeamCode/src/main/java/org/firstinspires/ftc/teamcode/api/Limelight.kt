package org.firstinspires.ftc.teamcode.api
import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry
import org.firstinspires.ftc.teamcode.core.API



object Limelight: API() {
    lateinit var limelight: Limelight3A
    override fun init(opMode: OpMode) {
        super.init(opMode)
        limelight = hardwareMap.get(Limelight3A::class.java, "limelight")
        limelight.setPollRateHz(100)
        limelight.start()
        limelight.pipelineSwitch(0)
        val result = limelight.getLatestResult()

    }
}