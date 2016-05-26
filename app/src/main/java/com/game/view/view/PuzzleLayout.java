package com.game.view.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.csit451sapien.jigsaw.CelebrateInterface;
import com.example.csit451sapien.jigsaw.SharedValues;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import game.utils.ImagePiece;
import game.utils.imagespliter;

/**
 * Created by Brian on 4/16/2016.
 */
public class PuzzleLayout extends RelativeLayout implements View.OnClickListener  {

    private static int mColumn = 3; // ****This is what determines the difficulty****
    private int mPadding;/* the edge distance */
    private int mMargin=3;/* the distance between pieces */
    private ImageView[] mItems;
    private int mItemWidth;
    private Bitmap mBitmap; /*the picture for the game*/
    private List<ImagePiece> mItemsBitmaps;
    private boolean once;
    private int mWidth;/*the width of the container */
    private static Bitmap image;
    private int[] goalState;
    private static CelebrateInterface celebrateInterface;
    Context cn = getContext();



    public PuzzleLayout(Context context) {
        this(context, null);
    }

    public PuzzleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PuzzleLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public void init(){
        mMargin= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3,
                getResources().getDisplayMetrics());
        mPadding=min(getPaddingLeft(),getPaddingRight(),getPaddingBottom(),getPaddingTop());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth=Math.min(getMeasuredHeight(), getMeasuredWidth());
        if (!once){
            initBitmap();/*cut the image,order it */
            initItem();
            once=true;
        }
        setMeasuredDimension(mWidth, mWidth);
    }

    // Randomizes puzzle piece positions in the beginning.
    private void initBitmap(){

        if (mBitmap==null){
            mBitmap= image;
        }
        mItemsBitmaps= imagespliter.splitimage(mBitmap, mColumn);


        /*use sort to implement out of order  */
        Collections.sort(mItemsBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece a, ImagePiece b) {
                return Math.random() > 0.5 ? 1 : -1;
            }
        });

        goalState = new int[mItemsBitmaps.size()];
        for (int i=0; i<mItemsBitmaps.size(); i++) {
            goalState[i] = mItemsBitmaps.get(i).getIndex();
        }
    }

    private void initItem(){
        mItemWidth=(mWidth-mPadding*2-mMargin*(mColumn-1))/mColumn;
        mItems=new ImageView[mColumn*mColumn];

        /*implement item,and set rule*/
        for (int i=0;i<mItems.length;i++){
            ImageView item=new ImageView(getContext());
            item.setOnClickListener(this);

            item.setImageBitmap(mItemsBitmaps.get(i).getBitmap());
            mItems[i]=item;
            item.setId(i+1);
            /*save the index in the tag*/
            item.setTag(i+"_"+mItemsBitmaps.get(i).getIndex());
            RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(mItemWidth,mItemWidth);

            /*not the last column, set rightmagin*/
            if ((i+1)%mColumn!=0){
                lp.rightMargin=mMargin;
            }
            /*not the first column，leftmagin*/
            if (i%mColumn!=0){
                lp.addRule(RelativeLayout.RIGHT_OF,mItems[i-1].getId());
            }
            /*if not the first row*/
            if ((i+1)>mColumn){
                lp.topMargin=mMargin;
                lp.addRule(RelativeLayout.BELOW,mItems[i-mColumn].getId());
            }
            addView(item,lp);
        }
    }

    private int min(int... params){
        int min=params[0];
        for (int param:params){
            if(param<min){
                min=param;
            }
        }
        return 0;
    }

    private ImageView mFirst;
    private ImageView mSecond;

    @Override
    public void onClick(View view) {
        //click the same image piece at the same time

        //Vibrate every time a puzzle piece is touched
        Vibrator vb = (Vibrator) cn.getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(100);


        if (mFirst==view){
            mFirst.setColorFilter(null);
            mFirst=null;
            return;
        }

        if(mFirst==null){
            mFirst=(ImageView) view;
            mFirst.setColorFilter(Color.parseColor("#55FF0000"));
        }
        else {
            mSecond=(ImageView) view;
            SharedValues.moves++;
            exchangeView();

        }
    }

    //exchange puzzle pieces
    private void exchangeView() {

        mFirst.setColorFilter(null);
        String firstTag=(String)mFirst.getTag();
        String secondTag=(String)mSecond.getTag();
        String[] firstParams=firstTag.split("_");
        String[] secondParams=secondTag.split("_");


        int firstEle = Integer.parseInt(firstParams[1]);
        int secondEle = Integer.parseInt(secondParams[1]);

        int firstI = getIndexOf(firstEle, goalState);
        int secondI = getIndexOf(secondEle, goalState);

        int temp = goalState[firstI];
        goalState[firstI] = goalState[secondI];
        goalState[secondI] = temp;

        Bitmap firstBitmap=mItemsBitmaps.get(Integer.parseInt(firstParams[0])).getBitmap();
        mSecond.setImageBitmap(firstBitmap);
        Bitmap secondBitmap=mItemsBitmaps.get(Integer.parseInt(secondParams[0])).getBitmap();
        mFirst.setImageBitmap(secondBitmap);

        mFirst.setTag(secondTag);
        mSecond.setTag(firstTag);

        mFirst=mSecond=null;

        checkEndGame();
    }

    // get index of value
    private int getIndexOf(int value, int[] array) {
        for (int i=0; i<array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return 0;
    }

    // If end game detected, if so then celebrate
    private void checkEndGame(){
        if (compareStates()) {
            Toast.makeText(cn, "You completed the Puzzle in " +SharedValues.moves + " moves!",Toast.LENGTH_LONG).show();
            SharedValues.moves = 0; //reset the counter
            celebrateInterface.celebration();

        }
    }

    // checks if all pieces are in original positions
    private boolean compareStates(){
        for (int i=0; i < goalState.length; i++) {
            if (!(goalState[i] == i)) {
                return false;
            }
        }
        return true;
    }

    // sets new colmn number
    public static void setColumn(int newCol) {
        mColumn = newCol;
    }

    // sets new image
    public static void setImage(Bitmap newImage) {
        image = newImage;
    }

    // sets interface
    public static void setInterface(CelebrateInterface newInter) {
        celebrateInterface = newInter;
    }
}
