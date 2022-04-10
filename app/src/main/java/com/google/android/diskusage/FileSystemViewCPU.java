package com.google.android.diskusage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.support.annotation.NonNull;
import com.google.android.diskusage.FileSystemState.FileSystemView;
import com.google.android.diskusage.entity.FileSystemEntry;

public final class FileSystemViewCPU extends View implements FileSystemView {
  private final FileSystemState eventHandler;
  
  public FileSystemViewCPU(Context context,
                           @NonNull FileSystemState eventHandler) {
    super(context);
    this.eventHandler = eventHandler;
    setFocusable(true);
    setFocusableInTouchMode(true);
    setBackgroundColor(Color.GRAY);
    eventHandler.setView(this);
  }

  @SuppressLint("ClickableViewAccessibility")
  @Override
  public final boolean onTouchEvent(@NonNull MotionEvent ev) {
    Log.d("diskusage", "touch = " + ev.getX() + ":" + ev.getY());
    eventHandler.onTouchEvent(
        eventHandler.multitouchHandler.newMyMotionEvent(ev));
    return true;
  }
  
  public void requestRepaint() {
    invalidate();
  }
  public void requestRepaintGPU() {
  }
  public void requestRepaint(int l, int t, int r, int b) {
    invalidate(l, t, r, b);
  }

  @Override
  protected final void onDraw(final Canvas canvas) {
    eventHandler.onDraw2(canvas);
  }
  
  @Override
  public final boolean onKeyDown(final int keyCode, final KeyEvent event) {
    if (eventHandler.onKeyDown(keyCode, event)) {
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
  
  @Override
  protected final void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    FileSystemEntry.updateFontsLegacy(getContext());
    eventHandler.layout(changed, left, top, right, bottom, getWidth(), getHeight());
  }
  
  public void onPause() {}
  public void onResume() {}

  @Override
  public void runInRenderThread(@NonNull Runnable r) {
    r.run();
  }

  @Override
  public void killRenderThread() {
  }
}
