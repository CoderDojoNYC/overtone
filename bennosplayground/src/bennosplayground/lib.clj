(ns bennosplayground.lib
    (:gen-class))
(use 'overtone.live)

;; We use a saw-wave that we defined in the oscillators tutorial
(definst saw-wave [freq 440 attack 0.01 sustain 0.1 release 0.01 vol 0.8] 
  (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

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
	 (looper nome sound (+ current-beat increment) increment final-beat)
    )))


