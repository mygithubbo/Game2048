package com.chijia.game2048;


import java.util.ArrayList;
import java.util.List;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class GameView extends LinearLayout {
	private Card[][] cardsMap = new Card[Config.LINES][Config.LINES];
	private List<Point> emptyPoints = new ArrayList<Point>();
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}
	
	public void initGameView(){
		setBackgroundColor(0xffbbada0);
		setOrientation(LinearLayout.VERTICAL);
		setOnTouchListener(new OnTouchListener() {
			private float startX,startY,offsetX,offsetY;
			/**
			 * 该方法一定要返回true,否则系统无法监听到抬起的事件
			 */
			@Override
			public boolean onTouch(View view, MotionEvent event) {
				
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = event.getX();
					startY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = event.getX() - startX;
					offsetY = event.getY() - startY;
					if(Math.abs(offsetX) > Math.abs(offsetY)){
						if(offsetX < -5){
							swipeLeft();
						} else if(offsetX > 5){
							swipeRight();
						}
					} else{
						if(offsetY < -5){
							swipeUp();
						} else if(offsetY > 5){
							swipeDown();
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Config.CARD_WIDTH = (Math.min(w, h) - 10) / 4;
		addCards(Config.CARD_WIDTH,Config.CARD_WIDTH);
		startGame();
	}
	
	private void addCards(int width, int height) {
		Card c;
		LinearLayout line;
		LinearLayout.LayoutParams lineLp;
		for (int y = 0; y < Config.LINES; y++) {
			line = new LinearLayout(getContext());
			lineLp = new LinearLayout.LayoutParams(-1, height);
			if(y == 0){
				lineLp.setMargins(10, 10, 0, 0);
			}else {
				lineLp.setMargins(10, 0, 0, 0);
			}
			addView(line, lineLp);
			for (int x = 0; x < Config.LINES; x++) {
				c = new Card(getContext());
				line.addView(c, width, height);
                cardsMap[x][y] = c;
			}
		}
	}

	private void swipeLeft(){

		boolean merge = false;

		for (int y = 0; y < Config.LINES; y++) {
			for (int x = 0; x < Config.LINES; x++) {

				for (int x1 = x+1; x1 < Config.LINES; x1++) {
					if (cardsMap[x1][y].getNum()>0) {

						if (cardsMap[x][y].getNum()<=0) {

							MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y],cardsMap[x][y], x1, x, y, y);

							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);

							x--;
							merge = true;

						}else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
							MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
							cardsMap[x1][y].setNum(0);

							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}

						break;
					}
				}
			}
		}

		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}
	
	private void swipeRight(){

		boolean merge = false;

		for (int y = 0; y < Config.LINES; y++) {
			for (int x = Config.LINES-1; x >=0; x--) {
				for (int x1 = x-1; x1 >=0; x1--) {
					if (cardsMap[x1][y].getNum()>0) {
						if (cardsMap[x][y].getNum()<=0) {
							MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							x++;
							merge = true;
						}else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
							MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x1][y], cardsMap[x][y],x1, x, y, y);
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
							cardsMap[x1][y].setNum(0);
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}
						break;
					}
				}
			}
		}

		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}
	private void swipeUp(){

		boolean merge = false;

		for (int x = 0; x < Config.LINES; x++) {
			for (int y = 0; y < Config.LINES; y++) {

				for (int y1 = y+1; y1 < Config.LINES; y1++) {
					if (cardsMap[x][y1].getNum()>0) {

						if (cardsMap[x][y].getNum()<=0) {
							MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);

							y--;

							merge = true;
						}else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
							MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
							cardsMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}

						break;

					}
				}
			}
		}

		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}
	private void swipeDown(){

		boolean merge = false;

		for (int x = 0; x < Config.LINES; x++) {
			for (int y = Config.LINES-1; y >=0; y--) {

				for (int y1 = y-1; y1 >=0; y1--) {
					if (cardsMap[x][y1].getNum()>0) {

						if (cardsMap[x][y].getNum()<=0) {
							MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);

							y++;
							merge = true;
						}else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
							MainActivity.getMainActivity().getAnimLayer().createMoveAnim(cardsMap[x][y1],cardsMap[x][y], x, x, y1, y);
							cardsMap[x][y].setNum(cardsMap[x][y].getNum()*2);
							cardsMap[x][y1].setNum(0);
							MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
							merge = true;
						}

						break;
					}
				}
			}
		}

		if (merge) {
			addRandomNum();
			checkComplete();
		}
	}

	public void startGame() {
		MainActivity mainActivity = MainActivity.getMainActivity();
		mainActivity.clearScore();
		mainActivity.showBestScore(mainActivity.getBestScore());
		for(int y = 0; y < Config.LINES; y++){
			for(int x = 0; x < Config.LINES; x++){
				cardsMap[x][y].setNum(0);
			}
		}
		addRandomNum();
		addRandomNum();
	}

	private void addRandomNum() {
		emptyPoints.clear();
		for(int y = 0; y < Config.LINES; y++){
			for(int x = 0; x < Config.LINES; x++){
				if(cardsMap[x][y].getNum() <= 0){
					Point point = new Point(x, y);
					emptyPoints.add(point);
				}
			}
		}
		if(emptyPoints.size() > 0){
			Point point = emptyPoints.remove((int)(Math.random() * emptyPoints.size()));
			cardsMap[point.x][point.y].setNum(Math.random() > 0.1 ? 2 : 4);
			MainActivity mainActivity = MainActivity.getMainActivity();
			mainActivity.getAnimLayer().createScaleTo1(cardsMap[point.x][point.y]);
		}
	}
	
	private void checkComplete() {
		boolean complete = true;
        boolean success = false;
		ALL:
			for (int y = 0; y < Config.LINES; y++) {
				for (int x = 0; x < Config.LINES; x++) {
					if(cardsMap[x][y].getNum() == 2048){
						success = true;
						break;
					}
					if (cardsMap[x][y].getNum() == 0||
							(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
							(x<Config.LINES-1&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
							(y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
							(y<Config.LINES-1&&cardsMap[x][y].equals(cardsMap[x][y+1]))) {

						complete = false;
						break ALL;
					}
				}
			}
		if (complete) {
			Intent intent = new Intent(getContext(),GameOverActivity.class);
			int score = MainActivity.getMainActivity().getScore();
			intent.putExtra("score", score);
			getContext().startActivity(intent);
		}
		if(success){
			Intent intent = new Intent(getContext(),GameSuccessActivity.class);
			int score = MainActivity.getMainActivity().getScore();
			intent.putExtra("score", score);
			getContext().startActivity(intent);
		}
	}



}
