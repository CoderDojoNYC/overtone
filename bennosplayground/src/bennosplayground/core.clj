(ns bennosplayground.core
    (:gen-class))
(use 'overtone.live)

;; We use a saw-wave that we defined in the oscillators tutorial
(definst saw-wave [freq 440 attack 0.01 sustain 0.1 release 0.01 vol 0.4] 
  (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))

;; Let's make easier to pass notes
(defn saw-by-note [music-note]
    (saw-wave (midi->hz (note music-note))))

(defn play-chord [a-chord]
  (doseq [note a-chord] (saw-by-note note)))

; this function will play our sound at whatever tempo we've set our metronome to 
(defn looper [nome sound]    
    (let [beat (nome)]
        (at (nome beat) (sound))
        (apply-at (nome (inc beat)) looper nome sound [])))

(defn mario-theme [m beat-num]
  (let [time (now)]
    (at (m (+ 0 beat-num)) (saw-by-note :E4))
    (at (m (+ 1 beat-num)) (saw-by-note :E4))
    (at (m (+ 3 beat-num)) (saw-by-note :E4))
    (at (m (+ 5 beat-num)) (saw-by-note :C4))
    (at (m (+ 6 beat-num)) (saw-by-note :E4))
    (at (m (+ 8 beat-num)) (saw-by-note :G4))
    (at (m (+ 12 beat-num)) (saw-by-note :G3))

    (at (m (+ 16 beat-num)) (saw-by-note :C5))
    (at (m (+ 19 beat-num)) (saw-by-note :G4))
    (at (m (+ 22 beat-num)) (saw-by-note :E4))
    (at (m (+ 25 beat-num)) (saw-by-note :A4))

    (at (m (+ 27 beat-num)) (saw-by-note :B4))
    (at (m (+ 29 beat-num)) (saw-by-note :Bb4))
    (at (m (+ 30 beat-num)) (saw-by-note :A4))
    (at (m (+ 32 beat-num)) (saw-by-note :G4))
    (at (m (+ 33.333 beat-num)) (saw-by-note :C5))
    (at (m (+ 34.666 beat-num)) (saw-by-note :E5))

    (at (m (+ 36 beat-num)) (saw-by-note :A5))
    (at (m (+ 38 beat-num)) (saw-by-note :F5))
    (at (m (+ 39 beat-num)) (saw-by-note :G5))
    (at (m (+ 41 beat-num)) (saw-by-note :E5))
    (at (m (+ 43 beat-num)) (saw-by-note :E5))
    (at (m (+ 44 beat-num)) (saw-by-note :F5))
    (at (m (+ 45 beat-num)) (saw-by-note :D5))
))


(def quarter-note 150)
(def kick (sample (freesound-path 2086)))
(def m (metronome 95))
(def m-four-x (metronome (* 95 4)))

(defn -main []
    (mario-theme m-four-x (m-four-x))
    (looper m kick)
)

