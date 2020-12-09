package com.dpad.crmclientapp.android.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

import com.dpad.crmclientapp.android.util.utils.PanelUtil
import com.dpad.telematicsclientapp.netlibrary.R
import java.lang.Math.cos
import java.lang.Math.sin
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * ================================================
 * ��    �ߣ�wenbody
 * ��    ����1.0
 * �������ڣ�2019/12/20 15:40
 * ��    ����
 * �޶���ʷ��
 * ================================================
 */
class PanelView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var mWidth: Float = 0f
    var mHeight: Float = 0f
    private val viewMargin = 10
    private val topMargin = 10


    /**
     * ����view�Ŀ�Ⱥ͸߶�
     */
    private var itemWidth = 0
    private var itemHeight = 0

    /**
     * ʵ��ͼƬ����Ļ����ʾ�Ŀ�͸�
     */
    private var imageHeight = 0f
    private var imageWidth = 0f

    private var startX = 0
    private var startY = 0

    /**
     * ��һ��Բ����Բ��
     */
    private var point1x = 0
    private var point1y = 0
    /**
     * �ڶ���Բ����Բ��
     */
    private var point2x = 0
    private var point2y = 0
    /**
     * �м�Բ����Բ��
     */
    private var point3x = 0
    private var point3y = 0
    /**
     * ���ĸ�Բ����Բ��
     */
    private var point4x = 0
    private var point4y = 0
    /**
     * �����Բ����Բ��
     */
    private var point5x = 0
    private var point5y = 0


    /**
     * ͼƬ���ű���
     */
    private var scale = 0f

    var bmp: Bitmap
    var tbmp: Bitmap

    private var progressPoint1 = 0f

    /**
     * ���ĽǶ�
     */
    private var corner1 = 225
    private var corner2 = 225
    private var corner3 = 270
    private var corner4 = 225
    private var corner5 = 215

    /**
     * ��Ҫ��ת�ĽǶ�
     */
    private var mCorner1 = 0
    private var mCorner2 = 0
    private var mCorner3 = 0
    private var mCorner4 = 0
    private var mCorner5 = 0

    /**
     * Բ�̵İ뾶
     */
    private var R1 = 0f
    private var R2 = 0f
    private var R3 = 0f
    private var R4 = 0f
    private var R5 = 0f

    private var maxProgress = 300f

    private var percentList: List<ArrayList<String>>
    private var titleList: ArrayList<String>
    private var unitList: ArrayList<String>

    /**
     * �Ƿ���T��
     */
    private var isTcar = false


    /**
     * �̶�����
     */
    private var numberPaint: Paint
    /**
     * �Ǳ�����
     */
    private var titlePaint: Paint
    /**
     * �Ǳ�λ
     */
    private var unitPaint: Paint

    private val originalImageWidth = 750
    private val originalImageHeight = 644

    init {
        /**
         * Ĭ�ϵ綯��
         */
        titleList = PanelUtil.getPanelNameList("0")
        unitList = PanelUtil.getPanelUnitNameList("0")
        percentList = PanelUtil.getAllPanelDataList("0")
        val resources = this.resources
        val dm = resources.displayMetrics

        bmp = BitmapFactory.decodeResource(resources, R.mipmap.ybp)
        tbmp = BitmapFactory.decodeResource(resources, R.mipmap.tybp)
        mWidth = dm.widthPixels.toFloat()
        mHeight = mWidth * 644 / 750//ͼƬ��߱�


        imageWidth = mWidth - 2 * viewMargin
        imageHeight = mHeight - 2 * topMargin

        itemHeight = (mHeight + 2 * topMargin).toInt()
        startY = topMargin
        point1x = (97 * imageWidth / originalImageWidth).toInt()
        point1y = (428 * imageHeight / originalImageHeight).toInt()

        point2x = (217 * imageWidth / originalImageWidth).toInt()
        point2y = (385 * imageHeight / originalImageHeight).toInt()

        point3x = (376 * imageWidth / originalImageWidth).toInt()
        point3y = (340 * imageHeight / originalImageHeight).toInt()

        point4x = (534 * imageWidth / originalImageWidth).toInt()
        point4y = (386 * imageHeight / originalImageHeight).toInt()

        point5x = (652 * imageWidth / originalImageWidth).toInt()
        point5y = (430 * imageHeight / originalImageHeight).toInt()


        R1 = (imageWidth * 80) / originalImageWidth
        R2 = (imageWidth * 96) / originalImageWidth
        R3 = (imageWidth * 118) / originalImageWidth
        R4 = (imageWidth * 97) / originalImageWidth
        R5 = (imageWidth * 79) / originalImageWidth

        scale = imageWidth / bmp.width.toFloat()

        //   ��ʼ������
        numberPaint = Paint()
        numberPaint.isAntiAlias = true
        numberPaint.color = Color.parseColor("#ffffff")
        numberPaint.textSize = 20f
        numberPaint.textAlign = Paint.Align.CENTER

        titlePaint = Paint()
        titlePaint.isAntiAlias = true
        titlePaint.color = Color.parseColor("#455165")
        titlePaint.textSize = 28f
        titlePaint.style = Paint.Style.FILL
        titlePaint.textAlign = Paint.Align.CENTER

        unitPaint = Paint()
        unitPaint.isAntiAlias = true
        unitPaint.color = Color.parseColor("#6DC6DA")
        unitPaint.textSize = 25f
        unitPaint.textAlign = Paint.Align.CENTER

    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setLayerType(LAYER_TYPE_HARDWARE, null)
        val newBmp = if (isTcar) Bitmap.createScaledBitmap(tbmp, imageWidth.toInt(), imageHeight.toInt(), true) else Bitmap.createScaledBitmap(bmp, imageWidth.toInt(), imageHeight.toInt(), true)
        val pointBmp1 = BitmapFactory.decodeResource(resources, R.mipmap.point1)
//        val newPointBmp1 = Bitmap.createScaledBitmap(
//                pointBmp1,
//                (pointBmp1.width * scale).toInt(),
//                (pointBmp1.height * scale).toInt(),
//                true
//        )
        val pointBmp2 = BitmapFactory.decodeResource(resources, R.mipmap.point2)
//        val newPointBmp2 = Bitmap.createScaledBitmap(
//                pointBmp2,
//                (pointBmp2.width * scale).toInt(),
//                (pointBmp2.height * scale).toInt(),
//                true
//        )
        val pointBmp3 = BitmapFactory.decodeResource(resources, R.mipmap.point3)
//        val newPointBmp3 = Bitmap.createScaledBitmap(
//                pointBmp3,
//                (pointBmp3.width * scale).toInt(),
//                (pointBmp3.height * scale).toInt(),
//                true
//        )
        val pointBmp4 = BitmapFactory.decodeResource(resources, R.mipmap.point4)
//        val newPointBmp4 = Bitmap.createScaledBitmap(
//                pointBmp4,
//                (pointBmp4.width * scale).toInt(),
//                (pointBmp4.height * scale).toInt(),
//                true
//        )
        val pointBmp5 = BitmapFactory.decodeResource(resources, R.mipmap.point5)
//        val newPointBmp5 = Bitmap.createScaledBitmap(
//                pointBmp5,
//                (pointBmp5.width * scale).toInt(),
//                (pointBmp5.height * scale).toInt(),
//                true
//        )

        val desRec =
                RectF(
                        startX.toFloat(),
                        startY.toFloat(),
                        (imageWidth + startX),
                        (imageHeight + startY)
                )
        canvas!!.drawBitmap(newBmp, null, desRec, null)

        drawNumbers(canvas, percentList[0], -20.0, 45.0, point1x, point1y, 12f, R1)
        drawNumbers(canvas, percentList[1], -20.0, 45.0, point2x, point2y, 16f, R2)
        drawNumbers(canvas, percentList[2], 45.0, 45.0, point3x, point3y, 20f, R3)
        drawNumbers(canvas, percentList[3], 155.0, 45.0, point4x, point4y, 16f, R4)
        drawNumbers(canvas, percentList[4], 156.0, 45.0, point5x, point5y, 12f, R5)

        /**
         * �����̵�λ
         */
        unitPaint.textSize = 18f
        canvas.drawText(
                unitList[0],
                point1x.toFloat() + 10f,
                point1y + imageWidth * 35.toFloat() / originalImageWidth,
                unitPaint
        )
        unitPaint.textSize = 24f
        canvas.drawText(
                unitList[1],
                point2x.toFloat() + 10f,
                point2y + imageWidth * 45.toFloat() / originalImageWidth,
                unitPaint
        )
        unitPaint.textSize = 28f
        canvas.drawText(
                unitList[2],
                point3x.toFloat() + 10f,
                point3y + imageWidth * 55.toFloat() / originalImageWidth,
                unitPaint
        )
        unitPaint.textSize = 20f
        canvas.drawText(
                unitList[3],
                point4x.toFloat() + 10f,
                point4y + imageWidth * 45.toFloat() / originalImageWidth,
                unitPaint
        )
        unitPaint.textSize = 18f
        canvas.drawText(
                unitList[4],
                point5x.toFloat() + 10f,
                point5y + imageWidth * 35.toFloat() / originalImageWidth,
                unitPaint
        )

        /**
         * ����������
         */
        canvas.drawText(
                titleList[0],
                point1x.toFloat(),
                point1y + imageWidth * 120.toFloat() / originalImageWidth,
                titlePaint
        )
        canvas.drawText(
                titleList[1],
                point2x.toFloat(),
                point2y + imageWidth * 130.toFloat() / originalImageWidth,
                titlePaint
        )
        canvas.drawText(
                titleList[2],
                point3x.toFloat(),
                point3y + imageWidth * 155.toFloat() / originalImageWidth,
                titlePaint
        )
        canvas.drawText(
                titleList[3],
                point4x.toFloat(),
                point4y + imageWidth * 130.toFloat() / originalImageWidth,
                titlePaint
        )
        canvas.drawText(
                titleList[4],
                point5x.toFloat(),
                point5y + imageWidth * 120.toFloat() / originalImageWidth,
                titlePaint
        )
        /**
         * �м��ͱ�
         */
        if (progressPoint1 < maxProgress) {
            canvas.rotate(
                    corner3 * this.progressPoint1 / maxProgress,
                    (startX + point3x).toFloat(),
                    (startY + point3y).toFloat()
            )
        } else {
            canvas.rotate(
                    corner3 - (corner3 - mCorner3) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point3x).toFloat(),
                    (startY + point3y).toFloat()
            )
        }
        canvas.drawBitmap(
                pointBmp3,
                (startX + point3x - pointBmp3.width).toFloat(),
                (startY + point3y).toFloat(),
                Paint(Paint.ANTI_ALIAS_FLAG)
        )

        /**
         * ��һ���ͱ�
         */
        canvas.save()
        canvas.restore()
        if (progressPoint1 < maxProgress) {
            canvas.rotate(
                    -(corner3 * this.progressPoint1 / maxProgress),
                    (startX + point3x).toFloat(),
                    (startY + point3y).toFloat()
            )
            canvas.rotate(
                    corner1 * this.progressPoint1 / maxProgress,
                    (startX + point1x).toFloat(),
                    (startY + point1y).toFloat()
            )
        } else {
            canvas.rotate(
                    -corner3 + (corner3 - mCorner3) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point3x).toFloat(),
                    (startY + point3y).toFloat()
            )
            canvas.rotate(
                    corner1 - (corner1 - mCorner1) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point1x).toFloat(),
                    (startY + point1y).toFloat()
            )
        }
        canvas.drawBitmap(
                pointBmp1,
                (startX + point1x).toFloat(),
                (startY + point1y).toFloat(),
                Paint(Paint.ANTI_ALIAS_FLAG)
        )

        /**
         * �ڶ����ͱ�
         */
        canvas.save()
        canvas.restore()
        if (progressPoint1 < maxProgress) {
            canvas.rotate(
                    -(corner1 * this.progressPoint1 / maxProgress),
                    (startX + point1x).toFloat(),
                    (startY + point1y).toFloat()
            )
            canvas.rotate(
                    corner2 * this.progressPoint1 / maxProgress,
                    (startX + point2x).toFloat(),
                    (startY + point2y).toFloat()
            )
        } else {
            canvas.rotate(
                    -corner1 + (corner1 - mCorner1) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point1x).toFloat(),
                    (startY + point1y).toFloat()
            )
            canvas.rotate(
                    corner2 - (corner2 - mCorner2) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point2x).toFloat(),
                    (startY + point2y).toFloat()
            )
        }
        canvas.drawBitmap(
                pointBmp2,
                (startX + point2x.toFloat()),
                (startY + point2y).toFloat(),
                Paint(Paint.ANTI_ALIAS_FLAG)
        )
        /**
         * ���ĸ��ͱ�
         */
        canvas.save()
        canvas.restore()

        if (progressPoint1 < maxProgress) {
            canvas.rotate(
                    -(corner2 * this.progressPoint1 / maxProgress),
                    (startX + point2x).toFloat(),
                    (startY + point2y).toFloat()
            )
            canvas.rotate(
                    corner4 * this.progressPoint1 / maxProgress,
                    (startX + point4x).toFloat(),
                    (startY + point4y).toFloat()
            )
        } else {
            canvas.rotate(
                    -corner2 + (corner2 - mCorner2) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point2x).toFloat(),
                    (startY + point2y).toFloat()
            )
            canvas.rotate(
                    corner4 - (corner4 - mCorner4) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point4x).toFloat(),
                    (startY + point4y).toFloat()
            )

        }

        canvas.drawBitmap(
                pointBmp4,
                (startX + point4x - pointBmp4.width).toFloat(),
                (startY + point4y - pointBmp4.height).toFloat(),
                Paint(Paint.ANTI_ALIAS_FLAG)
        )

        /**
         * ������ͱ�
         */
        canvas.save()
        canvas.restore()
        if (progressPoint1 < maxProgress) {
            canvas.rotate(
                    -(corner4 * this.progressPoint1 / maxProgress),
                    (startX + point4x).toFloat(),
                    (startY + point4y).toFloat()
            )

            canvas.rotate(
                    corner5 * this.progressPoint1 / maxProgress,
                    (startX + point5x).toFloat(),
                    (startY + point5y).toFloat()
            )
        } else {
            canvas.rotate(
                    -corner4 + (corner4 - mCorner4) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point4x).toFloat(),
                    (startY + point4y).toFloat()
            )
            canvas.rotate(
                    corner5 - (corner5 - mCorner5) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point5x).toFloat(),
                    (startY + point5y).toFloat()
            )

        }
        canvas.drawBitmap(
                pointBmp5,
                (startX + point5x - pointBmp5.width).toFloat(),
                (startY + point5y - pointBmp5.height).toFloat(),
                Paint(Paint.ANTI_ALIAS_FLAG)
        )


        if (progressPoint1 < maxProgress) {
            canvas.rotate(
                    -(corner5 * this.progressPoint1 / maxProgress),
                    (startX + point4x).toFloat(),
                    (startY + point4y).toFloat()
            )
        } else {
            canvas.rotate(
                    -corner5 + (corner5 - mCorner5) * (this.progressPoint1 - maxProgress) / maxProgress,
                    (startX + point4x).toFloat(),
                    (startY + point4y).toFloat()
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        when (widthMode) {
            MeasureSpec.EXACTLY -> {
                itemWidth = widthSize - 2 * viewMargin
                startX = viewMargin
                startY = topMargin
            }
            MeasureSpec.AT_MOST -> { //matchparent
                itemWidth = widthSize - 2 * viewMargin
                startX = viewMargin
                startY = topMargin
            }
            else -> { //自�?�应
                itemWidth = widthSize - 2 * viewMargin
                startX = viewMargin
                startY = topMargin
            }
        }

        startY = when {
            heightMode == MeasureSpec.EXACTLY -> {
                //                itemHeight = heightSize - 2 * topMargin
                topMargin
            }
            widthMode == MeasureSpec.AT_MOST -> {
                //                itemHeight = heightSize - 2 * topMargin
                topMargin
            }
            else -> { //自�?�应
                //                itemHeight = heightSize - 2 * topMargin
                topMargin
            }
        }
        setMeasuredDimension(widthSize, itemHeight)
    }


    //    userVehType: String
    fun setData(value1: Int, value2: Int, value3: Int, value4: Int, value5: Int, userVehType: String) {
        if (userVehType == "0" || userVehType == "4") {
            PanelUtil.initRegisterDays(value3)
        }
        ////�û��ͳ������ͣ�0���ο�1:�綯T��2���춯T��3��ȼ��T��4����T����
        isTcar = (userVehType == "1" || userVehType == "2" || userVehType == "3")
        this.titleList = PanelUtil.getPanelNameList(userVehType)
        this.percentList = PanelUtil.getAllPanelDataList(userVehType)
        this.mCorner1 = PanelUtil.getRealCorner(userVehType, 0, value1)
        this.mCorner2 = PanelUtil.getRealCorner(userVehType, 1, value2)
        this.mCorner3 = PanelUtil.getRealCorner(userVehType, 2, value3)
        this.mCorner4 = PanelUtil.getRealCorner(userVehType, 3, value4)
        this.mCorner5 = PanelUtil.getRealCorner(userVehType, 4, value5)
        this.unitList = PanelUtil.getPanelUnitNameList(userVehType)

        startAnim()
    }

    private fun startAnim() {
        setLayerType(LAYER_TYPE_HARDWARE, null)
        val objectAnimator: ValueAnimator = ObjectAnimator.ofFloat(0f, 2 * maxProgress)
        objectAnimator.addUpdateListener {
            this.progressPoint1 = it.animatedValue as Float
            postInvalidate()
        }
        objectAnimator.duration = 1200
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
//        objectAnimator.interpolator = AccelerateInterpolator()
        objectAnimator.start()
    }

    private fun drawNumbers(
            canvas: Canvas,
            numbers: ArrayList<String>,
            startRadias: Double,
            addRadias: Double,
            centerX: Int,
            centerY: Int,
            textsize: Float,
            R: Float
    ) {
        numberPaint.textSize = textsize
        for (index in numbers.indices) {
            canvas.drawText(
                    numbers[index],
                    centerX.toFloat() + 10 - sin(Math.toRadians(startRadias + addRadias * index)).toFloat() * 0.7f * R,
                    centerY.toFloat() + 15 + cos(Math.toRadians(startRadias + addRadias * index)).toFloat() * 0.7f * R,
                    numberPaint
            )
        }
    }
}