import AnimatedNode from './AnimatedNode';

class AnimatedGetKey extends AnimatedNode {
  constructor(key, value) {
    super({ type: 'getkey', key: key, value: value.__nodeID }, [value]);
  }
}

export function createAnimatedGetKey(key, value) {
  return new AnimatedGetKey(key, value);
}
