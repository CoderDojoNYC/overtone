(ns coderdojonyc.lib
  (:require [clojure.test :refer [is with-test run-tests]])
  (:gen-class))
(use 'overtone.live)

;; We use a saw-wave that we defined in the oscillators tutorial
(definst saw-wave [freq 440 attack 0.01 sustain 0.1 release 0.01 vol 0.8]
  (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

(definst smooth-saw [freq 440 attack 0.01 sustain 0.1 release 0.2 vol 0.5]
  (let [amp-env (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
        filter-env (env-gen (lin-env 0 (* sustain 0.5) release) 1 1 0 1)
        signal (saw freq)]
    (lpf (* signal amp-env vol)
         (-> filter-env (* (i-rand 1000 2000)) (+ 300)))))

;; Let's make easier to pass notes
(defn saw-by-note [music-note]
    (saw-wave (midi->hz (note music-note))))

(defn play-chord [a-chord]
  (doseq [note a-chord] (saw-by-note note)))

; this function will play our sound at whatever tempo we've set our metronome to
(defn looper [nome sound current-beat increment final-beat]
    (let [beat-time (nome current-beat)]
        (at beat-time (sound))
	(when (> final-beat current-beat)
	 (looper nome sound (+ current-beat increment) increment final-beat))))

(def bar-length metro-tock)
(def beat-length metro-tick)
(defn mnow
  "The current timestamp according to a metronome."
  [metro]
  (metro (metro)))

(with-test

  (defn next-bar
    "Returns the timestamp of the next bar"
    [metro]
    (let [bar (metro-bar metro)
          timestamp (metro-bar metro bar)]
      ;; the use of (mnow) rather than (now) here allows us to more accurately
      ;; compare time and avoid getting into infinite loops when scheduling
      ;; periodically, though I'm not totally clear on why.
      (if (> (- timestamp (mnow metro)) 0) timestamp
          ;; bump to the next bar if we're already _in_ the next bar
          (+ timestamp (bar-length metro)))))

  (let [m (metronome 120)]
      (is (<= (- (next-bar m) (now))
             (bar-length m))
          "next-bar returned a timestamp farther than bar-length in the future")))

(defn next-beat
  "Returns the timestamp of the next beat"
  [metro]
  (let [beat (metro-beat metro)
        timestamp (metro-beat metro beat)]
    (if (> (- timestamp (mnow metro)) 0) timestamp
        (+ timestamp (beat-length metro)))))

;; these two don't follow apply-at's function signatures in that they don't
;; require/accept a vector of args as their last argument

(defn apply-next-bar
  "Applies args to fun at the next bar."
  [metro fun & args]
  (apply-at (next-bar metro) fun args))

(defn apply-next-beat
  "Applies args to fun at the next beat."
  [metro fun & args]
  (apply-at (next-beat metro) fun args))

(defn wait-beats
  "Applies args to fun at 'beats' beats after the nearest beat."
  [metro beats fun & args]
  (apply-at (+ (mnow metro)
               (* beats (beat-length metro)))
            fun args))
