package controller.helpers;

import com.badlogic.gdx.Gdx;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AudioInputAsyncHelper {

	@SuppressWarnings("rawtypes")
	private Future future;

	public AudioInputAsyncHelper() {
		future = null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void startFftAnalysis() {
		if (future != null) {
			future = null;
		}
		future = Executors.newFixedThreadPool(1).submit(new Callable() {
			@Override
			public Object call() throws Exception {
				return new FftAnalysis().analyse();
			}
		});
	}

	public Boolean isDone() {
		return future != null && future.isDone();
	}

	public Double getAnalysisResult() {
		Double analysisResult = -1d;
		if (future != null) {
			try {
				analysisResult = (Double) future.get();
			} catch (Exception e) {
				Gdx.app.log("AudioInputAsyncHelper", "Not yet Done!");
			}
			return analysisResult;
		} else {
			Gdx.app.log("AudioInputAsyncHelper", "Future jest NULL");
			return null;
		}
	}
}
