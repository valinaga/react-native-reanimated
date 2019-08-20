
package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;
import java.lang.Math;

public class AudioNode extends Node implements NodesManager.OnAnimationFrame {
  public boolean isPlaying = false;
  private int index = 2;
  private static Double[] stopValues = {0.1, 0.2, 0.3};
  private static Double[] playValues = {0.5, 0.6, 0.7};
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
      return value + 1; // playValues[index];
    }
    return value + 2; // stopValues[index];
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
