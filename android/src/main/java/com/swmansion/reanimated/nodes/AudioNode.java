
package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;

public class AudioNode extends Value implements NodesManager.OnAnimationFrame {
  public boolean isPlaying;

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
  protected Double evaluate() {
    if (isPlaying) {
      return Double.valueOf(0.75);
    }
    return Double.valueOf(0.25);
  }

  @Override
  public void onAnimationFrame() {
    if (isPlaying) {
      markUpdated();
      mNodesManager.postOnAnimation(this);
    }
  }
}
