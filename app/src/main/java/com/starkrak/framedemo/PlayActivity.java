package com.starkrak.framedemo;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.starkrak.framedemo.game.GameBall;
import com.starkrak.framedemo.game.GameBox;
import com.starkrak.framedemo.game.GameColor;
import com.starkrak.framedemo.game.GameSrc;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

@LayoutID(R.layout.activity_play)
public class PlayActivity extends BaseActivity {


    private final GameBall[] gameBalls = new GameBall[6];

    private final GameBox[] gameBoxes = new GameBox[6];

    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //控件初始化
        PointF dragTouchPoint = new PointF();
        DragListener dragListener = dragItem -> {
            MyDragShadowBuilder dragShadowBuilder = new MyDragShadowBuilder(dragItem,
                    new Point(((int) (dragTouchPoint.x - dragItem.getX())), (int) (dragTouchPoint.y - dragItem.getY())));
            Point shadowSize = new Point();
            Point shadowTouchPoint = new Point();
            dragShadowBuilder.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
            dragItem.startDrag(null, dragShadowBuilder, dragItem.getTag(), 0);
        };
        @SuppressLint("ClickableViewAccessibility") View.OnTouchListener onTouchListener = (v, event) -> {
            try {
                dragTouchPoint.set(event.getX(), event.getY());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    dragListener.startDrag(v);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        };
        gameBalls[0] = new GameBall(findViewById(R.id.item1View));
        gameBalls[1] = new GameBall(findViewById(R.id.item2View));
        gameBalls[2] = new GameBall(findViewById(R.id.item3View));
        gameBalls[3] = new GameBall(findViewById(R.id.item4View));
        gameBalls[4] = new GameBall(findViewById(R.id.item5View));
        gameBalls[5] = new GameBall(findViewById(R.id.item6View));

        gameBoxes[0] = new GameBox(findViewById(R.id.answerBox1));
        gameBoxes[1] = new GameBox(findViewById(R.id.answerBox2));
        gameBoxes[2] = new GameBox(findViewById(R.id.answerBox3));
        gameBoxes[3] = new GameBox(findViewById(R.id.answerBox4));
        gameBoxes[4] = new GameBox(findViewById(R.id.answerBox5));
        gameBoxes[5] = new GameBox(findViewById(R.id.answerBox6));

        List<DragListManager.ActionView> actionViews = initActionViews();
        DragListManager dragManager = new DragListManager(this, actionViews);
        for (GameBall gameBall : gameBalls) {
            gameBall.getView().setOnTouchListener(onTouchListener);
            gameBall.getView().setOnDragListener(dragManager);
        }
        title = findViewById(R.id.title);
        findViewById(R.id.ballLayout).setOnDragListener(dragManager);
        findViewById(R.id.mainView).setOnDragListener(dragManager);
        //初始化题目
        renderGamePage(createTest());
    }

    private GameSrc createTest() {
        GameSrc gameSrc = new GameSrc();
        gameSrc.questionDrawable = ContextCompat.getDrawable(getContext(), R.drawable.a1_test);
        GameSrc.Answer[] answer = new GameSrc.Answer[6];
        answer[0] = new GameSrc.Answer(ContextCompat.getDrawable(this, R.mipmap.a1_0), GameColor.SkyBlue);
        answer[1] = new GameSrc.Answer(ContextCompat.getDrawable(this, R.mipmap.a2_0), GameColor.Green);
        answer[2] = new GameSrc.Answer(ContextCompat.getDrawable(this, R.mipmap.a3_0), GameColor.Pink);
        answer[3] = new GameSrc.Answer(ContextCompat.getDrawable(this, R.mipmap.a4_0), GameColor.Red);
        answer[4] = new GameSrc.Answer(ContextCompat.getDrawable(this, R.mipmap.a5_0), GameColor.Yellow);
        answer[5] = new GameSrc.Answer(ContextCompat.getDrawable(this, R.mipmap.a6_0), GameColor.DarkBlue);
        gameSrc.answer = answer;
        gameSrc.setGameHint("请把你认为有关联的图片整理起来");
        return gameSrc;
    }


    private void renderGamePage(@NonNull GameSrc gameSrc) {
        title.setTag(gameSrc.getHint());
        title.setText(gameSrc.getHint());
        ImageView gamePageView = findViewById(R.id.gamePageView);
        gamePageView.setImageDrawable(gameSrc.questionDrawable);
        for (int i = 0; i < gameSrc.answer.length && i < gameBoxes.length; i++) {
            gameBoxes[i].getImageView().setImageDrawable(gameSrc.answer[i].answerDrawable);
            gameBoxes[i].setRightColor(gameSrc.answer[i].answerColor);
        }
    }

    private List<DragListManager.ActionView> initActionViews() {
        List<DragListManager.ActionView> list = new ArrayList<>();
        for (GameBox gameBox : gameBoxes) {
            DragListManager.ActionView actionView = new DragListManager.ActionView(gameBox.getView(), new DragListManager.ActionView.OnDragListener() {
                @Override
                public void onDropIn(Object localState) {
                    gameBox.setGameBall((GameBall) localState);
                    gameBox.invalidate();
                    checkGame();
                }

                @Override
                public void onMove(int x, int y) {

                }
            });
            list.add(actionView);
        }
        return list;
    }

    private void checkGame() {
        for (GameBox gameBox : gameBoxes) {
            if (!gameBox.isFilled()) {
                onFilling();
                return;
            }
        }
        onComplete();
    }

    //填写中
    private void onFilling() {
        title.setText((String) title.getTag());
    }

    private void onComplete() {
        for (GameBox gameBox : gameBoxes) {
            if (!gameBox.isRight()) {
                title.setText("错误");
                return;
            }
        }
        //正确
        title.setText("正确");
    }

}
