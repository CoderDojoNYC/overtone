(ns coderdojonyc.examples
  (:require [overtone.inst.drum :refer [kick noise-snare]]
            [overtone.inst.synth :refer [rise-fall-pad ks1]]
            [coderdojonyc.song :refer [dosong wait loopsong]]))

;; you can eval these expressions one at a time and they all sync up with each
;; other rhythmically

;; drums
(loopsong
 (kick) (wait 2)
 (noise-snare) (wait 2))

;; plucked-string things
(do
  (loopsong
   (ks1 80) (wait 2)
   (ks1 78) (wait 4))

  (loopsong
   (ks1 75) (wait 3)
   (ks1 72) (wait 4))

  (loopsong
   (ks1 56) (ks1 44) (wait 8)
   (ks1 54) (ks1 42) (wait 8))

  (loopsong
   (ks1 60) (wait 9)
   (ks1 63) (wait 9))

  (loopsong
   (ks1 66) (wait 7)
   (ks1 68) (wait 7))

  (loopsong
   (ks1 85) (ks1 (- 85 12)) (wait 5)
   (ks1 82) (ks1 (- 82 12)) (wait 5)
   (ks1 80) (ks1 (- 80 12)) (wait 5)))

;; pads
(dosong
 (doseq [_ (range 2)]
   (rise-fall-pad (midi->hz 80)) (wait 8)
   (rise-fall-pad (midi->hz 75)) (wait 8)
   (rise-fall-pad (midi->hz 78)) (wait 16))
 (rise-fall-pad (midi->hz 73)) (wait 8)
 (rise-fall-pad (midi->hz 72)) (wait 8)
 (rise-fall-pad (midi->hz 66)) (wait 16)
 (rise-fall-pad (midi->hz 68)) (wait 32))
