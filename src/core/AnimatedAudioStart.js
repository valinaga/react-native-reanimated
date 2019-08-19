import AnimatedNode from './AnimatedNode';
import AnimatedAudio from './AnimatedAudio';
import invariant from 'fbjs/lib/invariant';

class AnimatedAudioStart extends AnimatedNode {
  _audioNode;

  constructor(audioNode) {
    super({ type: 'audioStart', audio: audioNode.__nodeID });
    invariant(
      audioNode instanceof AnimatedAudio,
      'Node is not of an AnimatedAudio type'
    );
    this._audioNode = audioNode;
  }

  __onEvaluate() {
    this._audioNode.start();
    return 0;
  }
}

export function createAnimatedAudioStart(audio) {
  return new AnimatedAudioStart(audio);
}
