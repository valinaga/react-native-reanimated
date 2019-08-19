package com.swmansion.reanimated.nodes;

import com.facebook.react.bridge.ReadableMap;
import com.swmansion.reanimated.NodesManager;

public abstract class AudioOpNode extends Node {

  public static class AudioPlayNode extends AudioOpNode {
    public AudioPlayNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
      super(nodeID, config, nodesManager);
    }

    @Override
    protected Double eval(AudioNode audio) {
      audio.play();
      return ZERO;
    }
  }

  public static class AudioStopNode extends AudioOpNode {
    public AudioStopNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
      super(nodeID, config, nodesManager);
    }

    @Override
    protected Double eval(AudioNode audio) {
      audio.stop();
      return ZERO;
    }
  }

  public static class AudioTestNode extends AudioOpNode {
    public AudioTestNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
      super(nodeID, config, nodesManager);
    }

    @Override
    protected Double eval(AudioNode audio) {
      return audio.isPlaying ? 1. : 0.;
    }
  }

  private int audioID;

  public AudioOpNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
    super(nodeID, config, nodesManager);
    audioID = config.getInt("audio");
  }

  @Override
  protected Double evaluate() {
    AudioNode audio = mNodesManager.findNodeById(audioID, AudioNode.class);
    return eval(audio);
  }

  protected abstract Double eval(AudioNode audio);
}
