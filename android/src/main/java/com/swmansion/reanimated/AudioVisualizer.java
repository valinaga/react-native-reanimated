package com.swmansion.reanimated;

import android.util.Log;
import android.media.audiofx.Visualizer;
import android.view.KeyEvent;
import java.lang.Runtime;

public class AudioVisualizer {

    private static final int PREFERRED_CAPTURE_SIZE = 128;

    private static final float MAX_DB_VALUE = 45;

    private static final int LOW_FREQUENCY = 300;
    private static final int MID_FREQUENCY = 2500;
    private static final int HIGH_FREQUENCY = 3000;

    private static final float[] SOUND_INDEX_COEFFICIENTS = new float[]{
        LOW_FREQUENCY / 44100f,
        MID_FREQUENCY / 44100f,
        HIGH_FREQUENCY / 44100f,
    };    

    private static final float FILTRATION_ALPHA = 0.55f;
    private static final float FILTRATION_BETA = 1 - FILTRATION_ALPHA;
    private static float[] mDbsFrequencies = new float[SOUND_INDEX_COEFFICIENTS.length];

    private static final String TAG = ">> Visualizer";

    private static Visualizer mVisualizer;
    private static AudioVisualizer mAudioVisualizer = new AudioVisualizer();
    private static boolean mVisualisationEnabled = false;

    private static int audioSessionId = 0;    
    private static int captureSize = PREFERRED_CAPTURE_SIZE;
    
    static {
        try {
            Class.forName("android.media.audiofx.Visualizer");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static AudioVisualizer getInstance() {
        if(mVisualizer == null) {            
            try {
                mVisualizer = new Visualizer(audioSessionId);
                mVisualizer.setEnabled(false);
                int[] captureSizeRange = Visualizer.getCaptureSizeRange();
                captureSize = Math.max(PREFERRED_CAPTURE_SIZE, captureSizeRange[0]);
                captureSize = Math.min(captureSize, captureSizeRange[1]);
                mVisualizer.setCaptureSize(captureSize);
                // mVisualizer.setDataCaptureListener(mAudioVisualizer, Visualizer.getMaxCaptureRate(), false, true);
                Log.w(TAG, "Visualizers: "+mAudioVisualizer+" internal:"+mVisualizer);                
            } catch (Exception e) {
                Log.w(TAG, "Exception init: "+e.toString());
            }
        }
        return mAudioVisualizer;
    }

    //     try {
    //         if (mVisualizer == null) {
    //             mVisualizer = new Visualizer(audioSessionId);
    //         }
    //         if (mVisualizer != null && mAudioVisualizer != null) {
    //             mVisualizer.setEnabled(false);
    //             int[] captureSizeRange = Visualizer.getCaptureSizeRange();
    //             captureSize = Math.max(PREFERRED_CAPTURE_SIZE, captureSizeRange[0]);
    //             captureSize = Math.min(captureSize, captureSizeRange[1]);
    //             mVisualizer.setCaptureSize(captureSize);
    //             mVisualizer.setDataCaptureListener(mAudioVisualizer, Visualizer.getMaxCaptureRate(), false, true);
    //             Log.w(TAG, "Visualizers: "+mAudioVisualizer+" internal:"+mVisualizer);
    //             return mAudioVisualizer;
    //         }
    //         Log.w(TAG, "Exception mVisualizer NULL ");
    //     } catch (Exception e) {
    //         Log.w(TAG, "Exception init: "+e.toString());
    //     }
    //     return mAudioVisualizer;
    // }

    public boolean isAvailable() {
        return (mVisualizer != null);
    }

    public boolean isEnabled() {
        return mVisualizer.getEnabled();
    }

    public void setEnabled(boolean enabled) {
        if (mVisualizer != null) {
            mVisualizer.setEnabled(enabled);
        }
    }

    public void release() {
        if (mVisualizer != null) {
            mVisualizer.release();
        }
    }

    public float[] getDbsFrequencies() {
        float[] mDbsFrequencies = new float[SOUND_INDEX_COEFFICIENTS.length];
        if (mVisualizer != null && mVisualizer.getEnabled()) {
            byte[] fft = new byte[captureSize];
            try {
                mVisualizer.getFft(fft);        
            } catch (Exception e) {
                Log.d(TAG, "Exception: "+e.toString());
                return mDbsFrequencies;
            }
            int dataSize = fft.length / 2 - 1;
            for (int i = 0; i < SOUND_INDEX_COEFFICIENTS.length; i++) {
                int index = (int) (SOUND_INDEX_COEFFICIENTS[i] * dataSize);
                long magnitude = (long) Math.hypot(fft[2 * index], fft[2 * index + 1]);            
                float dbs = magnitudeToDb(magnitude);
                float dbPercentage = dbs / MAX_DB_VALUE;
                if (dbPercentage > 1.0f) {
                    dbPercentage = 1.0f;
                }
                mDbsFrequencies[i] = 
                    mDbsFrequencies[i] * FILTRATION_ALPHA + 
                    dbPercentage * FILTRATION_BETA;
            }
        }
        return mDbsFrequencies;
    }

    // public Double getDbsFrequencyAt(int index) {
    //     if (index < SOUND_INDEX_COEFFICIENTS.length) {
    //         return Double.valueOf(mDbsFrequencies[index]);
    //     }
    //     return 0.0;
    // }

    // @Override
    // public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
    //     Log.d(TAG, "Wave samplingRate: "+samplingRate+" size:"+waveform.length);
    // }
    
    // @Override
    // public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
    //     extractFrequencies(fft);
    //     Log.d(TAG, "FFT ["+mDbsFrequencies[0]+", "+mDbsFrequencies[1]+", "+mDbsFrequencies[2]+"]");
    // }

    // private void extractFrequencies(byte[] fft) {
    //     int dataSize = fft.length / 2 - 1;
    //     for (int i = 0; i < SOUND_INDEX_COEFFICIENTS.length; i++) {
    //         int index = (int) (SOUND_INDEX_COEFFICIENTS[i] * dataSize);
    //         long magnitude = (long) Math.hypot(fft[2 * index], fft[2 * index + 1]);            
    //         float dbs = magnitudeToDb(magnitude);
    //         float dbPercentage = dbs / MAX_DB_VALUE;
    //         if (dbPercentage > 1.0f) {
    //             dbPercentage = 1.0f;
    //         }
    //         mDbsFrequencies[i] = 
    //             mDbsFrequencies[i] * FILTRATION_ALPHA + 
    //             dbPercentage * FILTRATION_BETA;
    //     }
    // }

    private float magnitudeToDb(float squareMag) {
        if (squareMag == 0) {
            return 0;
        }
        return (float) (20 * Math.log10(squareMag));
    }
}
