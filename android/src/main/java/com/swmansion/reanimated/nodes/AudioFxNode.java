package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.JavaOnlyMap;
import com.swmansion.reanimated.NodesManager;
import com.swmansion.reanimated.AudioVisualizer;

public class AudioFxNode extends Node {
  private AudioVisualizer visualizer;

  public AudioFxNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
    // TODO: maybe pass sound index coefficients db for filters
    super(nodeID, config, nodesManager);
    visualizer = AudioVisualizer.getInstance();
    visualizer.setEnabled(true);
  }

  @Override
  protected WritableMap evaluate() {
    JavaOnlyMap map = new JavaOnlyMap();
    float[] frequencies = visualizer.getDbsFrequencies();
    // TODO: add logging to make sure we understand when it is being evaluated
    map.putDouble("low", frequencies[0]);
    map.putDouble("mid", frequencies[1]);
    map.putDouble("high", frequencies[2]);
    return map;
  }
}
