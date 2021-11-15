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

  (let [canvas (.getElementById js/document "canvas")
        rect (.getBoundingClientRect canvas)]

    (reset! context (.getContext canvas "2d"))
    (reset! width (.-width rect))
    (reset! height (.-height rect))

    (aset canvas "width" @width)
    (aset canvas "height" @height)))

(defn draw
  []
  ;; Draw lines
  (.clearRect @context 0 0 width height)
  (.beginPath @context)
  (aset "lineWidth" @context 5)

  (doseq [l hlines]
    (.moveTo @context 0 (* @height l))
    (.lineTo @context @width (* @height l)))

  (doseq [l vlines]
    (.moveTo @context (* @width l) 0)
    (.lineTo @context (* @width l) @height))

  (.stroke @context))

(defn init []
  (setup)
  (draw))
