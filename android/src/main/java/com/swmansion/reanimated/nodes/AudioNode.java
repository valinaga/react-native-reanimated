
package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;

public class AudioNode extends Node implements NodesManager.OnAnimationFrame {
  public boolean isPlaying = false;

  public AudioNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
    super(nodeID, config, nodesManager);
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
  protected Double[] evaluate() {
    Double[] values = new Double[3];
    values[0] = 0.1;
    values[1] = 0.3;
    values[2] = 0.5;
    if (isPlaying) {
      values[0] = 0.6;
      values[1] = 0.7;
      values[2] = 0.8;
      return values;
    }
    return values;
  }

  @Override
  public void onAnimationFrame() {
    if (isPlaying) {
      markUpdated();
      mNodesManager.postOnAnimation(this);
    }
  }
}
