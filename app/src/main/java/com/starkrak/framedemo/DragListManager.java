package com.starkrak.framedemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.view.DragEvent;
import android.view.View;

import com.starkrak.framedemo.play.GameBall;

import net.gtr.framework.util.Loger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * @author caroline
 */
public class DragListManager implements View.OnDragListener {

    private List<ActionView> actionViews;

    private Context context;

    public DragListManager(@NonNull Context context, @NonNull List<ActionView> actionViews) {
        this.context = context;
        this.actionViews = actionViews;
    }

    public void addActionView(ActionView actionView) {
        actionViews.add(actionView);
    }

    private void showViews() {
        for (ActionView actionView : actionViews) {
            actionView.targetView.setVisibility(View.VISIBLE);
        }
    }

    private void hideViews() {
        for (ActionView actionView : actionViews) {
            actionView.targetView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                Loger.i("开始拖动");
//                showViews();
                return true;
            case DragEvent.ACTION_DRAG_ENTERED:
                Loger.i("进入拖动");
                return true;
            case DragEvent.ACTION_DRAG_LOCATION:
                Loger.i("拖动位置");
//                        if (!canDrag) {
//                            return true;
//                        }
//                        handleActionMoveWhileDragging(event);
//
//                        float x = event.getX();
//                        float y = event.getY();
//                        if (fromPosition == -1) {
//                            fromPosition = dragListener.getPosition(dragState.getItem());
//                        }
//                        int toPosition = RecyclerView.NO_POSITION;
//
//                        final VIEW child = recyclerView.findChildViewUnder(x, y);
//                        if (child != null) {
//                            toPosition = recyclerView.getChildViewHolder(child).getAdapterPosition();
//                        }
//                        if (fromPosition != toPosition) {
//                            swapItems(event);
//                        }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                Loger.i("拖出范围");

                break;
            case DragEvent.ACTION_DRAG_ENDED:
                Loger.i("ACTION_DRAG_ENDED");
//                hideViews();
//                        dragListener.notifyItemChange(dragListener.getPosition(dragState.getItem()), DragListener.NO_ID);
//                        fromPosition = -1;
//                        mScrollDirMask = SCROLL_DIR_NONE;
//                        prepareForNextMove();
                return true;
            case DragEvent.ACTION_DROP:
                Loger.i("ACTION_DROP");
//                        if (!canDrag) {
//                            return true;
//                        }
//                        int targetPosition = dragListener.getPosition(dragState.getItem());
//                        if (targetPosition != originPosition) {
//                            dragListener.onDragEnd(targetPosition);
//                        }
                break;
            default:
                Loger.i("default");

                break;
        }
        Loger.i("sssss-onDrag---" + event.getX() + "/" + event.getY());

        for (ActionView actionView : actionViews) {
            actionView.handlerDragEvent(v, event);
        }
        return true;
    }

    public static class ActionView {
        boolean inView = false;
        private View targetView;
        private OnDragListener targetAction;

        public ActionView(@NonNull View v, @NonNull OnDragListener r) {
            this.targetView = v;
            this.targetAction = r;
        }

        public void handlerDragEvent(@NonNull View dragView, @NonNull DragEvent event) {
            boolean nowInView = isInView(dragView, event);

            if (nowInView == inView) {
                //状态没有变化,内部释放
                handlerActionInternal(event);
                return;
            }
            inView = nowInView;
            onCrossing();

            if (inView) {
                onInBody(event.getLocalState());
            } else {
                onOutBody(event.getLocalState());
            }
        }

        private void handlerActionInternal(DragEvent event) {
            if (inView) {
                //TODO 内部事件
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        Loger.i(this + ",在此释放");
                        targetView.setAlpha(1.0f);
                        dropInBody(event.getLocalState());
                        break;
                    default:
                        break;
                }
            } else {
                //TODO 外部事件
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        break;
                    default:
                        break;
                }
            }
        }

        /**
         * 界内
         */
        private void onInBody(@Nullable Object object) {
            targetView.setAlpha(0.8f);
            if (object instanceof GameBall) {
                Loger.e("onInBody");
                GameBall ball = (GameBall) object;
                targetView.setBackground(ContextCompat.getDrawable(App.getContext(), ball.getGameColor().color));
            }
        }

        /**
         * 界外
         */
        private void onOutBody(@Nullable Object object) {
            targetView.setAlpha(1.0f);
            if (object instanceof GameBall) {
                Loger.e("onOutBody");
                targetView.setBackground(ContextCompat.getDrawable(App.getContext(), android.R.color.white));
            }
        }

        /**
         * 划过边界
         */
        @SuppressLint("MissingPermission")
        private void onCrossing() {
            Loger.i("onCrossing");
            ((Vibrator) App.getContext().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50L);
        }

        /**
         * 内部释放
         */
        private void dropInBody(Object object) {
            Loger.i("dropInBody");
            if (targetAction == null) {
                return;
            }
            if (object instanceof GameBall) {
                Loger.e("dropInBody");
                targetView.setBackground(ContextCompat.getDrawable(App.getContext(), android.R.color.white));
            }
            targetAction.onDropIn(object);
        }

        /**
         * 判断是否在界内
         *
         * @param event
         * @return
         */
        private boolean isInView(@NonNull View dragView, @NonNull DragEvent event) {
            Loger.i("isInView");
            float eventX = event.getX();
            float eventY = event.getY();
            boolean isTouchPointInView = isTouchPointInView(dragView, targetView, eventX, eventY);
            Loger.i("in? = " + isTouchPointInView);
            return isTouchPointInView;
        }

        private boolean isTouchPointInView(View dragView, View view, float x, float y) {
            if (view == null) {
                return false;
            }

            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            int right = left + view.getMeasuredWidth();
            int bottom = top + view.getMeasuredHeight();

            int[] locationD = new int[2];
            dragView.getLocationOnScreen(locationD);
            int leftD = locationD[0];
            int topD = locationD[1];
            int rightD = leftD + dragView.getMeasuredWidth();
            int bottomD = topD + dragView.getMeasuredHeight();

            Loger.i("x/y:" + x + "-" + y);
            Loger.i("left/top/right/bottom:" + left + "-" + top + "-" + right + "-" + bottom);
            Loger.i("dragView-left/top/right/bottom:" + leftD + "-" + topD + "-" + rightD + "-" + bottomD);

            //不能省略的偏移
            x += leftD;
            y += topD;

            return y >= top && y <= bottom && x >= left && x <= right;
        }

        public interface OnDragListener {
            void onDropIn(Object localState);

            void onMove(int x, int y);
        }

    }

}
