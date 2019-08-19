import AnimatedNode from './AnimatedNode';
import { clock } from './AnimatedClock';
import { val } from '../val';

export default class AnimatedAudio extends AnimatedNode {
  _started;
  _attached;

  constructor(index) {
    super({ type: 'always', index: index });
  }

  __onEvaluate() {
    return val(this);
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
