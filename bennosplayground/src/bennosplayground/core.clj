(ns bennosplayground.core
    (:gen-class))
(use 'overtone.live)
(use 'bennosplayground.lib)

(defn mario-theme [m beat-num]
    (println 'mario-theme)
    (println beat-num)
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
)


(def kick (sample (freesound-path 2086)))
(definst noisey [freq 420 attack 0.004 sustain 0.1 release 0.04 vol 0.15] 
  (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
     (white-noise) ; also have (white-noise) and others...
     vol))

(definst noisey-p [freq 420 attack 0.004 sustain 0.1 release 0.04 vol 0.4] 
  (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
     (pink-noise) ; also have (white-noise) and others...
     vol))

(def m (metronome (* 95 4)))

(defn -main []
    (let [start-beat (+ (m) 8)]
       (mario-theme m start-beat)
       (looper m noisey-p (+ start-beat 4) 8 (+ start-beat 44))
       (looper m noisey start-beat 8 (+ start-beat 44))
    )
)

