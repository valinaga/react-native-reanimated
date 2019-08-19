import AnimatedNode from './AnimatedNode';
import AnimatedAudio from './AnimatedAudio';
import invariant from 'fbjs/lib/invariant';

class AnimatedAudioTest extends AnimatedNode {
  _audioNode;

  constructor(audioNode) {
    super({ type: 'audioTest', audio: audioNode.__nodeID });
    invariant(
      audioNode instanceof AnimatedAudio,
      'Node is not of an AnimatedAudio type'
    );
    this._audioNode = audioNode;
  }

  __onEvaluate() {
    return this._audioNode.isStarted() ? 1 : 0;
  }
}

export function createAnimatedAudioTest(clock) {
  return new AnimatedAudioTest(clock);
}
