import AnimatedNode from './AnimatedNode';
import AnimatedAudio from './AnimatedAudio';
import invariant from 'fbjs/lib/invariant';

class AnimatedAudioStop extends AnimatedNode {
  _audioNode;

  constructor(audioNode) {
    super({ type: 'audioStop', audio: audioNode.__nodeID });
    invariant(
      audioNode instanceof AnimatedAudio,
      'Node is not of an AnimatedAudio type'
    );
    this._audioNode = audioNode;
  }

  __onEvaluate() {
    this._audioNode.stop();
    return 0;
  }
}

export function createAnimatedAudioStop(audio) {
  return new AnimatedAudioStop(audio);
}
