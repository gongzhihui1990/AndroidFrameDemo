package com.starkrak.framedemo;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

@LayoutID(R.layout.activity_play)
public class PlayActivity extends BaseActivity {


    private final GameBall[] gameBalls = new GameBall[6];

    private final GameBox[] gameBoxes = new GameBox[6];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            gameBall.view.setOnTouchListener(onTouchListener);
            gameBall.view.setOnDragListener(dragManager);
        }

    }

    private List<DragListManager.ActionView> initActionViews() {
        List<DragListManager.ActionView> list = new ArrayList<>();
        for (GameBox gameBox : gameBoxes) {
            DragListManager.ActionView actionView = new DragListManager.ActionView(gameBox.view, new DragListManager.ActionView.OnDragListener() {
                @Override
                public void onDropIn(Object localState) {
                    gameBox.setGameBall((GameBall) localState);
                    gameBox.invalidate();
                }

                @Override
                public void onMove(int x, int y) {

                }
            });
            list.add(actionView);
        }
        return list;
    }

    enum Color {
        Red, Yellow, Green, Blue, Group, Pink
    }

    private class GameBall implements GameView {
        Color color;
        boolean used = false;
        //0-5
        int currentIndex = -1;
        View view;

        GameBall(@NonNull View view) {
            this.view = view;
            switch (view.getId()) {
                case R.id.item1View:
                    color = Color.Red;
                    break;
                case R.id.item2View:
                    color = Color.Yellow;
                    break;
                case R.id.item3View:
                    color = Color.Green;
                    break;
                case R.id.item4View:
                    color = Color.Blue;
                    break;
                case R.id.item5View:
                    color = Color.Group;
                    break;
                case R.id.item6View:
                    color = Color.Pink;
                    break;
                default:
                    throw new RuntimeException("不支持");
            }
            view.setTag(this);
        }

        @Override
        public void invalidate() {
            if (used) {
                view.setAlpha(0.3f);
            } else {
                view.setAlpha(1f);
            }
        }
    }

    private class GameBox implements GameView {
        GameBall gameBall;
        GameBall gameBallPrv;
        View view;

        GameBox(@NonNull View view) {
            this.view = view;
        }

        private void setGameBall(GameBall gameBall) {
            gameBall.used = true;
            if (this.gameBall != null) {
                this.gameBallPrv = this.gameBall;
                this.gameBallPrv.used = false;
            }
            this.gameBall = gameBall;
        }

        private void clear() {
            this.gameBall = null;
        }

        @Override
        public void invalidate() {
            if (gameBall != null) {
                gameBall.invalidate();
            }
            if (this.gameBallPrv != null) {
                this.gameBallPrv.invalidate();
            }
        }
    }

    interface GameView {
        void invalidate();
    }
}
