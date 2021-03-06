package org.firstinspires.ftc.teamcode.ftc16072.pipelines;
import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.Rect;
import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This class finds the location of ducks -- useful for testing auto before we have a TSE
 * <p>
 * uses a quick and dirty method that works becausea we know where they are every time
 */
@Config
public class DuckLocation extends OpenCvPipeline {
    Telemetry telemetry;
    public static Rect space1 = new Rect(70, 120, 50, 50);
    public static Rect space2 = new Rect(200, 120, 50, 50);

    public static Rect space3 = new Rect(300, 120, 20, 50);
    protected int slotSelected = -1;
    Scalar blue = new Scalar(0, 0, 255);
    Scalar green = new Scalar(0, 255, 0);

    /**
     * constructer to give us acces to telemetry
     * @param telemetry so that we can return values to the telelmetry for debugging
     */
    public DuckLocation(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    /**
     * allows the robot to poll the pipeline and discover whet the best slot is
     * @return an integer representing 1, 2, or 3 for the slots (-1 if not run yet or no slot best)
     */
    public int getSlotSelected(){
        return slotSelected;
    }

    /**
     * The bulk of the pipeline:
     * Color scheme -> blur
     * then we take the blurred mat and submat it into 3 rectangles where we know the spots are
     * We average the color of the submat, and compare the yellow
     * The slot with the most yellow has the duck in it
     * @param inputMat the mat to analyse from the camera
     * @return returns the modified mat to display for debugging
     */
    @Override
    public Mat processFrame(Mat inputMat) {
        Mat colormat = new Mat();
        Imgproc.cvtColor(inputMat, colormat, Imgproc.COLOR_RGB2HSV);
        Mat blurredMat = new Mat();
        Imgproc.GaussianBlur(colormat, blurredMat, new Size(3, 3), 0);
        colormat.release();
        Mat slot1 = blurredMat.submat(space1);
        Mat slot2 = blurredMat.submat(space2);
        Mat slot3 = blurredMat.submat(space3);
        double space1Color = Core.mean(slot1).val[1];
        slot1.release();
        //telemetry.addData("Space 1", space1Color);
        double space2Color = Core.mean(slot2).val[1];
        slot2.release();
        //telemetry.addData("Space 2", space2Color);
        double space3Color = Core.mean(slot3).val[1];
        slot3.release();


        //Visual where rectangles are
        Imgproc.rectangle(inputMat, space1, blue, 3);
        Imgproc.rectangle(inputMat, space2, blue, 3);
        Imgproc.rectangle(inputMat, space3, blue, 3);

        // Select pipeline (color green)
        if (space1Color >= space2Color && space1Color >= space3Color) {
            slotSelected = 1;
            Imgproc.rectangle(inputMat, space1, green, 3);
        } else if (space2Color >= space3Color && space2Color >= space1Color ){
            slotSelected = 2;
            Imgproc.rectangle(inputMat, space2, green, 3);
        } else if (space3Color >= space2Color){
            slotSelected = 3;
            Imgproc.rectangle(inputMat, space3, green, 3);
        }

        telemetry.addData("Selected", slotSelected);

        telemetry.update();
        blurredMat.release();
        return inputMat;
    }

}

