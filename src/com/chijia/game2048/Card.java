package com.chijia.game2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
    private TextView label;
    public TextView getLabel() {
		return label;
	}

	public void setLabel(TextView label) {
		this.label = label;
	}

	private int num = 0;
	private View background;
	public Card(Context context) {
		
		super(context);
		LayoutParams lp = null;
		background = new View(getContext());
		background.setBackgroundColor(0x33ffffff);
		lp = new LayoutParams(-1,-1);
		lp.setMargins(10, 10, 0, 0);
		addView(background,lp);
		label = new TextView(getContext());
		label.setTextSize(Config.TEXT_SIZE);
		label.setGravity(Gravity.CENTER);
		lp = new LayoutParams(-1,-1);
		lp.setMargins(10, 10, 0, 0);
		addView(label, lp);
		setNum(0);
	}
	
	public boolean equals(Card card) {
		return this.getNum() == card.getNum();
	}
	
	public Card clone(){
		Card card = new Card(getContext());
		card.setNum(getNum());
		return card;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
		if (num<=0) {
			label.setText("");
		}else{
			label.setText(num+"");
		}
		switch (num) {
		case 0:
			label.setBackgroundColor(0x00000000);
			break;
		case 2:
			label.setBackgroundColor(0xffeee4da);
			break;
		case 4:
			label.setBackgroundColor(0xffede0c8);
			break;
		case 8:
			label.setBackgroundColor(0xfff2b179);
			break;
		case 16:
			label.setBackgroundColor(0xfff59563);
			break;
		case 32:
			label.setBackgroundColor(0xfff67c5f);
			break;
		case 64:
			label.setBackgroundColor(0xfff65e3b);
			break;
		case 128:
			label.setBackgroundColor(0xffedcf72);
			break;
		case 256:
			label.setBackgroundColor(0xffedcc61);
			break;
		case 512:
			label.setBackgroundColor(0xffedc850);
			break;
		case 1024:
			label.setBackgroundColor(0xffedc53f);
			break;
		case 2048:
			label.setBackgroundColor(0xffedc22e);
			break;
		default:
			label.setBackgroundColor(0xff3c3a32);
			break;
		}
	}

}
