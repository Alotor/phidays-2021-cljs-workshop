(ns clocks.main)

(declare init)

(defn ^:dev/before-load stop
  [])

(defn ^:dev/after-load reload
  []
  (init))

(def context (atom nil))
(def width   (atom nil))
(def height  (atom nil))

(def hlines [ 0 0.25 0.5 0.75 1.0 ])
(def vlines [ 0 0.25 0.5 0.75 1.0 ])

(defn setup
  []
  ;; Setup canvas

  ;; Store in the state: context, width and height
  )

(defn draw
  []
  ;; Draw lines
  )

(defn init []
  (setup)
  (draw))
