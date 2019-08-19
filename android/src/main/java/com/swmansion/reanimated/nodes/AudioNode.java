
package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;

public class AudioNode extends Node implements NodesManager.OnAnimationFrame {
  public boolean isPlaying = false;
  private int index = 2;
  private static double[] stopValues = [0.1, 0.2, 0.3];
  private static double[] playValues = [0.5, 0.6, 0.7];

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
      return Double.valueOf(playValues[index]);
    }
    return Double.valueOf(stopValues[index]);
  }

  @Override
  public void onAnimationFrame() {
    if (isPlaying) {
      markUpdated();
      mNodesManager.postOnAnimation(this);
    }
  }
}
