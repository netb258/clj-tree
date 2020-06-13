(ns fractal-tree.core
  (:require [quil.core :as q])
  (:gen-class))

;; Window size.
(def WIDTH 700)
(def HEIGHT 700)

;; This def significantly changes the shape of the tree. Play with it in the REPL.
(def ANGLE-ROTATION (/ (Math/PI) 6))

(def TREE-HEIGHT (/ HEIGHT 3)) ;; The bigger the value here, the taller the tree will be.
(def BRANCH-DECREASE 0.66) ;; Every subsequent branch must be smaller than the previous one.
(def MIN-BRANCH-SIZE 2) ;; We cannot draw branches shorter than this.

(defn setup []
  (q/frame-rate 30)
  (q/background 0)
  (q/stroke 255))

(defn make-branch [length] ;; This function will generate the whole tree.
  (q/line 0 0 0 (- length)) ;; Every branch will be a simple line.
  (q/translate 0 (- length))
  (when (> length MIN-BRANCH-SIZE)
    (q/push-matrix)
    (q/rotate ANGLE-ROTATION)
    (make-branch (* length BRANCH-DECREASE)) ;; Branch to the right.
    (q/pop-matrix)
    (q/push-matrix)
    (q/rotate (- ANGLE-ROTATION))
    (make-branch (* length BRANCH-DECREASE)) ;; Branch to the left.
    (q/pop-matrix)))

(defn draw-state! []
  (q/translate (/ WIDTH 2) HEIGHT)
  (make-branch TREE-HEIGHT))

(defn -main [& args]
  (q/defsketch fractal-tree
    :title "Fractal tree"
    :renderer :java2d
    :features [:exit-on-close]
    :setup setup
    :draw draw-state!
    :size [WIDTH HEIGHT]))
