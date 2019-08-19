import AnimatedNode from './AnimatedNode';

export default class AnimatedAudio extends AnimatedNode {
  _started;

  constructor() {
    super({ type: 'audio'});
  }

  __onEvaluate() {
    return 0;
  }

  start() {
    this._started = true;
  }

  stop() {
    this._started = false;
  }

  isStarted() {
    return this._started;
  }

}

export function createAnimatedAudio(item) {
  return new AnimatedAudio(item);
}
