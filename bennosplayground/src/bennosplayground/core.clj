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

(def note-gap 150)

(defn mario-theme []
  (let [time (now)]
    (at time (saw-by-note :E4))
    (at (+ note-gap time) (saw-by-note :E4))
    (at (+ (* 3 note-gap) time) (saw-by-note :E4))
    (at (+ (* 5 note-gap) time) (saw-by-note :C4))
    (at (+ (* 6 note-gap) time) (saw-by-note :E4))
    (at (+ (* 8 note-gap) time) (saw-by-note :G4))
    (at (+ (* 12 note-gap) time) (saw-by-note :G3))

    (at (+ (* 16 note-gap) time) (saw-by-note :C5))
    (at (+ (* 19 note-gap) time) (saw-by-note :G4))
    (at (+ (* 22 note-gap) time) (saw-by-note :E4))
    (at (+ (* 25 note-gap) time) (saw-by-note :A4))

    (at (+ (* 27 note-gap) time) (saw-by-note :B4))
    (at (+ (* 29 note-gap) time) (saw-by-note :Bb4))
    (at (+ (* 30 note-gap) time) (saw-by-note :A4))
    (at (+ (* 32 note-gap) time) (saw-by-note :G4))
    (at (+ (* 33.333 note-gap) time) (saw-by-note :C5))
    (at (+ (* 34.666 note-gap) time) (saw-by-note :E5))

    (at (+ (* 36 note-gap) time) (saw-by-note :A5))
    (at (+ (* 38 note-gap) time) (saw-by-note :F5))
    (at (+ (* 39 note-gap) time) (saw-by-note :G5))
    (at (+ (* 41 note-gap) time) (saw-by-note :E5))
    (at (+ (* 43 note-gap) time) (saw-by-note :E5))
    (at (+ (* 44 note-gap) time) (saw-by-note :F5))
    (at (+ (* 45 note-gap) time) (saw-by-note :D5))
))


(defn -main []
    (mario-theme)
)

