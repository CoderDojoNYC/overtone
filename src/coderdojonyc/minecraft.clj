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

(ns coderdojonyc.minecraft
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
 
;;
;; Test
;; (minecraft-row1 metro (metro))
;;
 
(defn minecraft-row2 [m beat-num]
  (at (m (+ 0 beat-num)) (play :G4 1))
  (at (m (+ 1 beat-num)) (play :G4 1))
  (at (m (+ 2 beat-num)) (play :G4 1))
  (at (m (+ 3 beat-num)) (play :A4 1))
  (at (m (+ 4 beat-num)) (play :B4 2))
  (at (m (+ 6 beat-num)) (play :B4 2))
)
 
(defn minecraft-row3 [m beat-num]
  (at (m (+ 0 beat-num)) (play :B4 1))
  (at (m (+ 1 beat-num)) (play :B4 1))
  (at (m (+ 2 beat-num)) (play :B4 1))
  (at (m (+ 3 beat-num)) (play :D5 1))
  (at (m (+ 4 beat-num)) (play :E5 2))
  (at (m (+ 6 beat-num)) (play :E5 2))
)
 
(defn minecraft-row4 [m beat-num]
  (at (m (+ 0 beat-num)) (play :E5 1))
  (at (m (+ 1 beat-num)) (play :E5 1))
  (at (m (+ 2 beat-num)) (play :E5 1))
  (at (m (+ 3 beat-num)) (play :F#5 1))
  (at (m (+ 4 beat-num)) (play :E5 2))
  (at (m (+ 6 beat-num)) (play :E5 2))
)
 
(defn minecraft-row7 [m beat-num]
  (at (m (+ 0 beat-num)) (play :B4 1))
  (at (m (+ 1 beat-num)) (play :B4 1))
  (at (m (+ 2 beat-num)) (play :B4 1))
  (at (m (+ 3 beat-num)) (play :B4 1))
  (at (m (+ 4 beat-num)) (play :E4 2))
  (at (m (+ 6 beat-num)) (play :E4 2))
)
 
 
(defn minecraft-row8 [m beat-num]
  (at (m (+ 0 beat-num)) (play :E4 1))
  (at (m (+ 1 beat-num)) (play :E4 1))
  (at (m (+ 2 beat-num)) (play :E4 1))
  (at (m (+ 3 beat-num)) (play :F#4 1))
  (at (m (+ 4 beat-num)) (play :E4 2))
  (at (m (+ 6 beat-num)) (play :E4 2))
)
 
 
;;--------------------------------
;; Test each piece!
;;
;; (minecraft-row1 metro (metro))
;; (minecraft-row2 metro (metro))
;; (minecraft-row3 metro (metro))
;; (minecraft-row4 metro (metro))
;; (minecraft-row7 metro (metro))
;; (minecraft-row8 metro (metro))
 
(defn minecraft [m beat-num]
  (minecraft-row1 m beat-num)
  (minecraft-row2 m (+ beat-num 8))
  (minecraft-row3 m (+ beat-num 16))
  (minecraft-row4 m (+ beat-num 24))
  (minecraft-row1 m (+ beat-num 32))
  (minecraft-row2 m (+ beat-num 40))
  (minecraft-row7 m (+ beat-num 48))
  (minecraft-row8 m (+ beat-num 56))
)
 
 
;; String: pluck effect
;;
; (definst pluck-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4]
;   (pluck (* (white-noise)
;             (env-gen (perc 0.0001 2) :action FREE)) 1 3 (/ 1 freq)
;   )
; )
; (defn play [music-note ticks]
;   (pluck-wave (midi->hz (note music-note))  0.01 (* 0.4 ticks))
; )
 
 
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Now, let's try the piano sound
;;
;; (use 'overtone.inst.piano) 
;;
;; (defn play [music-note ticks]
;;  (piano (note music-note))
;; )
 

(minecraft metro (metro))
