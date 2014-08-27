package com.chijia.game2048;



import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private int score = 0;
	private TextView tvScore,tvBestScore;
	private LinearLayout root = null;
	private Button btnNewGame;
	private GameView gameView;
	private AnimLayer animLayer;
	private static MainActivity mainActivity = null;
	private long exitTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mainActivity = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		root = (LinearLayout) findViewById(R.id.container);
		root.setBackgroundColor(0xfffaf8ef);
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvBestScore = (TextView) findViewById(R.id.tvBestScore);
		gameView = (GameView) findViewById(R.id.gameView);
		btnNewGame = (Button) findViewById(R.id.btnNewGame);
		btnNewGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				switch (view.getId()) {
				case R.id.btnNewGame:
					gameView.startGame();
					break;

				default:
					break;
				}
			}
		});
		animLayer = (AnimLayer) findViewById(R.id.animLayer);
	}
	
	public GameView getGameView() {
		return gameView;
	}

	public int getScore() {
		return score;
	}

	public AnimLayer getAnimLayer() {
		return animLayer;
	}

	public void setAnimLayer(AnimLayer animLayer) {
		this.animLayer = animLayer;
	}

	public static MainActivity getMainActivity(){
		return mainActivity;
	}
	
	public void clearScore(){
		score = 0;
		showScore();
	}

	public void showScore() {
		tvScore.setText(Config.SCORE_PRE + score);
	}
	
	public void addScore(int s) {
		score += s;
		showScore();
		int maxScore = Math.max(score, getBestScore());
		saveBestScore(maxScore);
		showBestScore(maxScore);
	}

	public void showBestScore(int maxScore) {
		tvBestScore.setText(Config.BEST_SCORE_PRE + maxScore);
	}

	private void saveBestScore(int maxScore) {
		Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putInt(Config.KEY_BEST_SCORE, maxScore);
		editor.commit();
	}

	public int getBestScore() {
		return getPreferences(MODE_PRIVATE).getInt(Config.KEY_BEST_SCORE, 0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次返回键退出游戏",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else{
				finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
