package com.starkrak.framedemo;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.starkrak.framedemo.game.GameBall;
import com.starkrak.framedemo.game.GameBox;
import com.starkrak.framedemo.game.GameColor;
import com.starkrak.framedemo.game.GameSrc;

import net.gtr.framework.rx.dialog.NativeMessageDialog;
import net.gtr.framework.util.Loger;

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
    private View successView;
    private ImageView ivIcon;
    private PointF dragTouchPoint = new PointF();
    private DragListener ballDragListener = dragItem -> {
        MyDragShadowBuilder dragShadowBuilder = new MyDragShadowBuilder(dragItem,
                new Point(((int) (dragTouchPoint.x - dragItem.getX())), (int) (dragTouchPoint.y - dragItem.getY())));
        Point shadowSize = new Point();
        Point shadowTouchPoint = new Point();
        dragShadowBuilder.onProvideShadowMetrics(shadowSize, shadowTouchPoint);
        dragItem.startDrag(null, dragShadowBuilder, dragItem.getTag(), 0);
    };

    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener onBallTouchListener = (v, event) -> {
        try {
            Loger.i("onBallTouchListener getAction" + event.getAction());
            dragTouchPoint.set(event.getX(), event.getY());
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Loger.i("onBallTouchListener ballDragListener startDrag");
                ballDragListener.startDrag(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //控件初始化
        title = findViewById(R.id.title);
        successView = findViewById(R.id.successView);
        ivIcon = findViewById(R.id.ivIcon);
        View btnReplay = findViewById(R.id.btnReplay);
        View btnNext = findViewById(R.id.btnNext);
        View ballLayout = findViewById(R.id.ballLayout);
        gameBalls[0] = new GameBall(findViewById(R.id.item1View), GameColor.Red, findViewById(R.id.proxyImage1));
        gameBalls[1] = new GameBall(findViewById(R.id.item2View), GameColor.Yellow, findViewById(R.id.proxyImage2));
        gameBalls[2] = new GameBall(findViewById(R.id.item3View), GameColor.Green, findViewById(R.id.proxyImage3));
        gameBalls[3] = new GameBall(findViewById(R.id.item4View), GameColor.SkyBlue, findViewById(R.id.proxyImage4));
        gameBalls[4] = new GameBall(findViewById(R.id.item5View), GameColor.DarkBlue, findViewById(R.id.proxyImage5));
        gameBalls[5] = new GameBall(findViewById(R.id.item6View), GameColor.Pink, findViewById(R.id.proxyImage6));

        gameBoxes[0] = new GameBox(findViewById(R.id.answerBox1));
        gameBoxes[1] = new GameBox(findViewById(R.id.answerBox2));
        gameBoxes[2] = new GameBox(findViewById(R.id.answerBox3));
        gameBoxes[3] = new GameBox(findViewById(R.id.answerBox4));
        gameBoxes[4] = new GameBox(findViewById(R.id.answerBox5));
        gameBoxes[5] = new GameBox(findViewById(R.id.answerBox6));

        List<DragListManager.ActionView> actionViews = initActionViewsForGameBalls();
        DragListManager dragManager = new DragListManager(this, actionViews);
        for (GameBall gameBall : gameBalls) {
            gameBall.getView().setOnTouchListener(onBallTouchListener);
            gameBall.getView().setOnDragListener(dragManager);
        }
        ballLayout.setOnDragListener(dragManager);
        findViewById(R.id.mainView).setOnDragListener(dragManager);
        //初始化题目
        renderGameViewBySrc(createTest());
        btnNext.setOnClickListener(v -> {
            NativeMessageDialog nativeMessageDialog = new NativeMessageDialog(getContext());
            nativeMessageDialog.setDialogMessage("开发中");
            nativeMessageDialog.show();
        });
        btnReplay.setOnClickListener(v -> {
            for (GameBox box : gameBoxes) {
                box.setGameBall(null);
                box.invalidate();
            }
            checkGame();
        });
        findViewById(R.id.mainView).post(this::printViews);

    }


    private void printViews() {
        for (GameBall ball : gameBalls) {
            View view = ball.getView();
            Rect rect = new Rect();
            Point point = new Point();
            view.getGlobalVisibleRect(rect, point);
            ball.setCenterPoint(point);
        }
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
        gameSrc.setGameHint("请观察图片，把合适的圆点放到右侧方格中");
        return gameSrc;
    }


    private void renderGameViewBySrc(@NonNull GameSrc gameSrc) {
        title.setTag(gameSrc.getHint());
        title.setText(gameSrc.getHint());
        ImageView gamePageView = findViewById(R.id.gamePageView);
        gamePageView.setImageDrawable(gameSrc.questionDrawable);
        for (int i = 0; i < gameSrc.answer.length && i < gameBoxes.length; i++) {
            gameBoxes[i].getImageView().setImageDrawable(gameSrc.answer[i].answerDrawable);
            gameBoxes[i].setRightColor(gameSrc.answer[i].answerColor);
            gameBoxes[i].setGameBall(gameBalls[i]);
            gameBoxes[i].invalidate();
            gameBoxes[i].initDelay((i+1)*50);
        }

    }

    private List<DragListManager.ActionView> initActionViewsForGameBalls() {
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
        boolean allFilled = true;
        for (GameBall ball : gameBalls) {
            if (!ball.isInit()) {
                allFilled = false;
            }
            if (!allFilled) {
                break;
            }
        }
        for (GameBox gameBox : gameBoxes) {
            if (!gameBox.isInit()) {
                allFilled = false;
            }
            if (!gameBox.isFilled()) {
                allFilled = false;
            }
            if (!allFilled) {
                break;
            }
        }
        if (allFilled) {
            onComplete();
        } else {
            onFilling();
        }
    }


    //填写中
    private void onFilling() {
        successView.setVisibility(View.GONE);
        ivIcon.setVisibility(View.GONE);
    }

    //填写完成
    private void onComplete() {
        for (GameBox gameBox : gameBoxes) {
            if (!gameBox.isRight()) {
                View contentView = View.inflate(getContext(), R.layout.simple_error_toast_layout, null);
                Toast toast = new Toast(getContext());
                toast.setView(contentView);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        }
        //正确
        successView.setVisibility(View.VISIBLE);
        ivIcon.setVisibility(View.VISIBLE);
    }

}
