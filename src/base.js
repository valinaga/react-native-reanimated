export { createAnimatedCond as cond } from './core/AnimatedCond';
export { createAnimatedSet as set } from './core/AnimatedSet';
export { createAnimatedStartClock as startClock } from './core/AnimatedStartClock';
export { createAnimatedStopClock as stopClock } from './core/AnimatedStopClock';
export { createAnimatedClockTest as clockRunning } from './core/AnimatedClockTest';
export { createAnimatedDebug as debug } from './core/AnimatedDebug';
export { createAnimatedCall as call } from './core/AnimatedCall';
export { createAnimatedEvent as event } from './core/AnimatedEvent';
export { createAnimatedAlways as always } from './core/AnimatedAlways';
export { createAnimatedAudio as audio } from './core/AnimatedAudio';
export { createAnimatedAudioStart as startAudio } from './core/AnimatedAudioStart';
export { createAnimatedAudioStop as stopAudio } from './core/AnimatedAudioStop';
export { createAnimatedAudioTest as audioPlaying } from './core/AnimatedAudioTest';

export { createAnimatedConcat as concat } from './core/AnimatedConcat';
export { createAnimatedBlock as block, adapt } from './core/AnimatedBlock';
export { createAnimatedFunction as proc } from './core/AnimatedFunction';
export * from './operators';
