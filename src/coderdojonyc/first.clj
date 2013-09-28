;;
;; Minecraft. Part 1. Right hand.
;; Based on: https://www.youtube.com/watch?v=0v5tE9fwqQo
;; 
;; MIDI reference for notes
;; http://computer-music.postbit.com/upload/131/posts/midi-notes-numbers.gif
;; http://www.midimountain.com/midi/midi_note_numbers.html
;;
;; Musical Theory (using JS)
;; https://github.com/saebekassebil/teoria
;;
;; At the bottom, added customization for "playing" a string "pluck" effect and a piano
;;
;; Try clojure:  http://tryclj.com/
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(ns coderdojonyc.first
  (:gen-class))

(use 'overtone.live)


(defonce metro (metronome 120))
(metro)
 
;; We use a saw-wave that we defined in the oscillators tutorial
(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))
 
 
(defn play [music-note ticks]
  (saw-wave (midi->hz (note music-note)) 0.01 (* 0.4 ticks)))
 
 
(defn minecraft-row1 [m beat-num]
  (at (m (+ 0 beat-num)) (play :E4 1))
  (at (m (+ 1 beat-num)) (play :E4 1))
  (at (m (+ 2 beat-num)) (play :E4 1))
  (at (m (+ 3 beat-num)) (play :F#4 1))
  (at (m (+ 4 beat-num)) (play :G4 2))
  (at (m (+ 6 beat-num)) (play :G4 2))
)
 
;; Test
(minecraft-row1 metro (metro))
