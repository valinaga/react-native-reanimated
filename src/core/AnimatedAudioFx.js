import AnimatedNode from './AnimatedNode';

class AnimatedAudioFx extends AnimatedNode {
  constructor() {
    super({ type: 'audiofx' });
  }
}

export function createAnimatedAudioFx() {
  return new AnimatedAudioFx();
}
