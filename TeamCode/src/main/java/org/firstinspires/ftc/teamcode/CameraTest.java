package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.robocol.TelemetryMessage;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@TeleOp(name="CameraTest", group="Linear Opmode")
public class CameraTest extends OpMode {
    //注意，这个程序的很多部分都是按照示范写的
    //这个程序有MIT许可证，我们没有任何责任 ye
    protected ElapsedTime runtime;
    protected OpenCvCamera camera;
    protected double lastTime;
    protected double dt;

    //-130
    //protected enum liftLevel { //TODO: attach ints to values
    //    RECEIVE, HUB_1, HUB_2, HUB_3
    //}
    //liftLevel level = liftLevel.RECEIVE;
    @Override
    public void init() {
        //开始， 准备变量
        runtime = new ElapsedTime();
        telemetry.addData("Status", "Started");
        telemetry.update();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().
                getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().
                createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        camera.openCameraDeviceAsync( new OpenCvCamera.AsyncCameraOpenListener() {
                                          @Override
                                          public void onOpened() {
                                              camera.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
                                          }

                                          @Override
                                          public void onError(int errorCode) {

                                          }
                                      });
        StageSwitchingPipeline pipeline = new StageSwitchingPipeline();
        camera.setPipeline(new StageSwitchingPipeline());



    }

    @Override
    public void loop() {
        dt = runtime.time() - lastTime;
    }


    }

 class StageSwitchingPipeline extends OpenCvPipeline {
    private final Random rng = new Random(12345);
    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, input, Imgproc.COLOR_BGR2HSV);
        Scalar lowHSV = new Scalar(90,100,100);
        Scalar highHSV = new Scalar(115,360,360);
        Core.inRange(input, lowHSV, highHSV, input);
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(input, contours, input, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        double minX = 0;
        double minY = Integer.MAX_VALUE;
        for (int i = 0; i < contours.size(); i++) {
            MatOfPoint contour = contours.get(i);
            if (Imgproc.contourArea(contour)>=1600) {
                Moments p = Imgproc.moments(contour);
                if ((p.m01/p.m00)<minY) {
                    minY = p.m01/p.m00;
                    minX = p.m10/p.m00;
                }
            }
        }

        Imgproc.cvtColor(input, input, Imgproc.COLOR_HSV2BGR);
        Imgproc.rectangle(input, new Rect(new Point(100,100), new Point(500, 500))
        , new Scalar(128, 0,0));
        return input;
    }

}