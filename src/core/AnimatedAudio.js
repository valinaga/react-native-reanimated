import AnimatedNode from './AnimatedNode';
import { clock } from './AnimatedClock';

export default class AnimatedAudio extends AnimatedNode {
  _started;
  _attached;

  constructor() {
    super({ type: 'audio'});
  }

  __onEvaluate() {
    return val(clock);
  }

  __attach() {
    super.__attach();
    if (this._started && !this._attached) {
      clock.__addChild(this);
    }
    this._attached = true;
  }

  __detach() {
    if (this._started && this._attached) {
      clock.__removeChild(this);
    }
    this._attached = false;
    super.__detach();
  }

  start() {
    if (!this._started && this._attached) {
      clock.__addChild(this);
    }
    this._started = true;
  }

  stop() {
    if (this._started && this._attached) {
      clock.__removeChild(this);
    }
    this._started = false;
  }

  isStarted() {
    return this._started;
  }

}

export function createAnimatedAudio(item) {
  return new AnimatedAudio(item);
}
