package com.dyl.utils.media_source;

import com.dyl.utils.file.FileUtil;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 媒体资源工具类
 * @Date 2019/6/26 19:00
 * @Author Dong YL
 * @Email silentself@126.com
 */
public class MsUtils {

    private static String TRANSCODED_FORMAT = "mp4";

    /**
     * 视频转码为mp4
     *
     * @param file 要转码的视频文件
     * @return absolutePath-转码成功返回文件本地绝对路径
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String convertToMp4(File file) throws IOException, NoSuchAlgorithmException {
        FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file.getAbsolutePath());
        frameGrabber.start();
        String absolutePath = file.getParentFile() + FileUtil.PATH_SEPARATOR + FileUtil.getMd5(file) + FileUtil.FILE_TYPE_SEPARATOR + TRANSCODED_FORMAT;
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(absolutePath, frameGrabber.getImageWidth(), frameGrabber.getImageHeight(), frameGrabber.getAudioChannels());
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); //avcodec.AV_CODEC_ID_H264 //AV_CODEC_ID_MPEG4
        recorder.setFormat(TRANSCODED_FORMAT);
        recorder.setFrameRate(frameGrabber.getFrameRate());
        // recorder.setSampleFormat(frameGrabber.getSampleFormat());
        recorder.setSampleRate(frameGrabber.getSampleRate());
        recorder.setAudioChannels(frameGrabber.getAudioChannels());
        recorder.setFrameRate(frameGrabber.getFrameRate());
        recorder.start();
        while (true) {
            Frame capturedFrame = frameGrabber.grabFrame();
            if (capturedFrame == null) {
                System.out.println("!!! Failed cvQueryFrame");
                break;
            }
            recorder.setTimestamp(frameGrabber.getTimestamp());
            recorder.record(capturedFrame);
        }
        recorder.stop();
        recorder.release();
        frameGrabber.stop();
        frameGrabber.release();
        return absolutePath;
    }

    /**
     * 获取视频关键帧
     *
     * @param fileAbsolutePath 文件绝对路径
     * @return 成功返回文件本地绝对路径
     */
    public static String getVideoKeyFrame(String fileAbsolutePath) throws IOException {
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(fileAbsolutePath);
        File outPut = File.createTempFile(FileUtil.getFileSerialNum(), FileUtil.FILE_TYPE_SEPARATOR + "jpg", new File(FileUtil.getBasePath()));
        fFmpegFrameGrabber.start();
        Frame frame = fFmpegFrameGrabber.grabKeyFrame();
        if (frame != null) {
            ImageIO.write(VedioFrame.frameToBufferedImage(frame), "jpg", outPut);
        }
        fFmpegFrameGrabber.stop();
        fFmpegFrameGrabber.release();
        return outPut.getAbsolutePath();
    }

    /**
     * 均匀获取视频的十张图片
     *
     * @param fileAbsolutePath 文件绝对路径
     * @return
     */
    public static List<String> grabTenFrame(String fileAbsolutePath) throws IOException {
        List<String> imgPathList = new ArrayList<>();
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(fileAbsolutePath);
        fFmpegFrameGrabber.start();
        //Frame对象
        Frame frame = null;
        //标识
        int flag = 0;
        int ftp = fFmpegFrameGrabber.getLengthInFrames();
        int index = ftp / 10;
        for (int i = 0; i < ftp; i++) {
            //获取帧
            frame = fFmpegFrameGrabber.grabImage();
            while (flag * index == i) {
                //文件绝对路径+名字
                String fileName = FileUtil.getBasePath() + FileUtil.PATH_SEPARATOR + FileUtil.getFileSerialNum() + "img_" + flag + ".jpg";
                //文件储存对象
                File outPut = new File(fileName);
                if (frame != null) {
                    ImageIO.write(VedioFrame.frameToBufferedImage(frame), "jpg", outPut);
                }
                flag++;
                imgPathList.add(fileName);
            }
        }
        fFmpegFrameGrabber.stop();
        fFmpegFrameGrabber.release();
        return imgPathList;
    }


    private static class VedioFrame {
        public static BufferedImage frameToBufferedImage(Frame frame) {
            //创建BufferedImage对象
            Java2DFrameConverter converter = new Java2DFrameConverter();
            BufferedImage bufferedImage = converter.getBufferedImage(frame);
            return bufferedImage;
        }
    }


}
