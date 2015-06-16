package controller.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioRecorder;

import org.jtransforms.fft.DoubleFFT_1D;

public class FftAnalysis {
	static final double c_normalisation = 13000d;
	static final int sampleRate = 44100;
	static final int sampleNumber = 8192; // number of samples
	static final int micSwichOnTimeInSamples = 10; // time to switch on the
													// microphone
	boolean isMono = true;
	short[] recordBuffer = new short[sampleNumber + micSwichOnTimeInSamples];
	private AudioRecorder recorder = Gdx.audio.newAudioRecorder(sampleRate,
			isMono);

	/**
	 * Records samples from the microphone, windows the discrete signal,
	 * computes FFT, magnitude and returns the frequency with the highest
	 * magnitude.
	 * 
	 * @return - frequency with the highest magnitude
	 * @throws Exception
	 */
	public Double analyse() throws Exception {
		// 1. Record data from microphone to short[] buffor
		recorder.read(recordBuffer, 0, recordBuffer.length);
		short[] recordBufferWithoutSwichingOn = new short[sampleNumber];
		System.arraycopy(recordBuffer, micSwichOnTimeInSamples,
				recordBufferWithoutSwichingOn, 0, sampleNumber);
		// 2. Transform short[] to double[]
		double[] transformedRecordBuffer = new double[sampleNumber];
		for (int j = 0; j < sampleNumber; j++) {
			transformedRecordBuffer[j] = (recordBufferWithoutSwichingOn[j])
					/ c_normalisation; // normalisation in terms of test
										// exposure limits
		}
		// 3. Hamming window
		double[] windowedRecordValues = putThroughtHammingWindow(transformedRecordBuffer);
		// 4. Copy real values from Hamming window into complex bufor of size 2N
		double[] complexWindowedRecordValues = new double[2 * sampleNumber];
		for (int i = 0; i < sampleNumber - 1; i++) {
			complexWindowedRecordValues[2 * i] = windowedRecordValues[i];
			complexWindowedRecordValues[2 * i + 1] = 0;
		}
		// 5. Create DoubleFFT_1D, which can compute the Fourier Transform using
		// fft algorithms for complex numbers.
		// Values 2i are Rez and values 2i+1 are Imz.
		DoubleFFT_1D fftDo = new DoubleFFT_1D(sampleNumber);
		double[] fftResult = new double[2 * sampleNumber];
		System.arraycopy(complexWindowedRecordValues, 0, fftResult, 0,
				complexWindowedRecordValues.length);
		fftDo.complexForward(fftResult);
		double[] magnitude = getMagnitude(fftResult);
		recorder.dispose();
		return getMaximumFrequency(magnitude);
	}

	private double[] getMagnitude(double[] fft) {

		double[] magnitude = new double[sampleNumber];
		for (int k = 0; k < fft.length / 2; k++) {
			double rez = fft[2 * k];
			double imz = fft[2 * k + 1];
			magnitude[k] = Math.sqrt(rez * rez + imz * imz);
		}
		return magnitude;
	}

	/**
	 * Puts gathered samples into Hamming window
	 * 
	 * @param transformedRecord
	 *            - samples from microphone as double
	 * @return - windowed samples
	 */
	private double[] putThroughtHammingWindow(double[] transformedRecord) {
		double[] windowedSamples = new double[sampleNumber];
		for (int i = 0; i < windowedSamples.length; i++) {
			windowedSamples[i] = transformedRecord[i]
					* (0.53836 - 0.46164 * Math.cos((2 * Math.PI * i)
							/ (sampleNumber - 1)));
		}
		return windowedSamples;
	}

	private double getMaximumFrequency(double[] magnitude) {
		double magnitudeMinimumValue = 30;
		int maxIndex = 0;
		for (int k = 0; k < magnitude.length / 2; k++) {
			double newnumber = magnitude[k];
			if (newnumber > magnitude[maxIndex]) {
				maxIndex = k;
			}
		}
		// finding the highest spectrum band

		if (magnitude[maxIndex] > magnitudeMinimumValue) {
			// Gdx.app.log("FftAnalysis", "Highest spectrum band magnitude: "
			// + magnitude[maxIndex]);
			return sampleRate * maxIndex / sampleNumber;
		} else {
			return -1d;
		}

	}
}