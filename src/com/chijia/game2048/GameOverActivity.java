package com.chijia.game2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends Activity {
    private Button btnRestart;
    private TextView tvFinalScore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
		int score = getIntent().getIntExtra("score", 0);
		tvFinalScore = (TextView) findViewById(R.id.tvFinalScore);
		tvFinalScore.setText(Config.FINAL_SCORE_PRE + score);
		btnRestart = (Button) findViewById(R.id.btnRestart);
		btnRestart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				MainActivity.getMainActivity().getGameView().startGame();
				finish();
			}
		});
	}
}
