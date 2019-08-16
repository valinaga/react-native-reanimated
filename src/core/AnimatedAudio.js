import AnimatedNode from './AnimatedNode';

class AnimatedAudio extends AnimatedNode {
  _what;

  constructor(what) {
    super({ type: 'audio', what: what.__nodeID }, [what]);
    this._what = what;
  }

  __onEvaluate() {
    return 0;
  }
}

export function createAnimatedAudio(item) {
  return new AnimatedAudio(item);
}
