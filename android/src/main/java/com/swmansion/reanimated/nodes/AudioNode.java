
package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;
import java.lang.Math;

public class AudioNode extends Node implements NodesManager.OnAnimationFrame {
  public boolean isPlaying = false;
  private int index = 2;
  private Double value = 0.0;

  public AudioNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
    super(nodeID, config, nodesManager);
    index = config.getInt("index");
  }

  public void play() {
    if (isPlaying) {
      return;
    }
    isPlaying = true;
    mNodesManager.postOnAnimation(this);
  }

  public void stop() {
    isPlaying = false;
  }

  @Override
  protected Double evaluate() {
    if (isPlaying) {
      return value;
    }
    return value - 1;
  }

  @Override
  public void onAnimationFrame() {
    value = Math.random();
    if (isPlaying) {
      markUpdated();
      mNodesManager.postOnAnimation(this);
    }
  }
}
