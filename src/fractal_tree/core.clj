(ns fractal-tree.core
  (:require [quil.core :as q])
  (:gen-class))

;; Window size.
(def WIDTH 700)
(def HEIGHT 700)

;; This def significantly changes the shape of the tree. Play with it in the REPL.
(def angle-rotation (/ (Math/PI) 6))

(def tree-height (/ HEIGHT 3)) ;; The bigger the value here, the taller the tree will be.
(def branch-decrease 0.66) ;; Every subsequent bransh must be smaller than the previous one.
(def min-branch-size 2) ;; We cannot draw branches shorter than this.

(defn setup []
  (q/frame-rate 30)
  (q/background 0)
  (q/stroke 255))

(defn make-branch [length]
  (q/line 0 0 0 (- length))
  (q/translate 0 (- length))
  (when (> length min-branch-size)
    (q/push-matrix)
    (q/rotate angle-rotation)
    (make-branch (* length branch-decrease)) ;; Branch to the right.
    (q/pop-matrix)
    (q/push-matrix)
    (q/rotate (- angle-rotation))
    (make-branch (* length branch-decrease)) ;; Branch to the left.
    (q/pop-matrix)))

(defn draw-state! []
  (q/translate (/ WIDTH 2) HEIGHT)
  (make-branch tree-height))

(defn -main [& args]
  (q/defsketch fractal-tree
    :title "Fractal tree"
    :renderer :java2d
    :features [:exit-on-close]
    :setup setup
    :draw draw-state!
    :size [WIDTH HEIGHT]))
