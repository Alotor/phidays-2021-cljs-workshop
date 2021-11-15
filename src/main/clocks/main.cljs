(ns clocks.main)

(declare init)

(defn ^:dev/before-load stop
  [])

(defn ^:dev/after-load reload
  []
  (init))

(def state (atom {:context nil
                  :width nil
                  :height nil}))

(defn setup
  []
  (let [canvas (.getElementById js/document "canvas")
        rect (.getBoundingClientRect canvas)]
    (reset! state
            {:width (.-width rect)
             :height (.-height rect)
             :context (.getContext canvas "2d")})

    (aset canvas "width" (:width @state))
    (aset canvas "height" (:height @state))))

(defn random-color
  []
  (let [r (* (.random js/Math) 255)
        g (* (.random js/Math) 255)
        b (* (.random js/Math) 255)]
    {:r r :g g :b b}))

(defn draw
  []
  (let [context (:context @state)
        width   (:width @state)
        height  (:height @state)

        color (random-color)]

    (.clearRect context 0 0 width height)

    (.beginPath context)
    (aset context "lineWidth" 5)
    (.rect context 10 10 (- width 20) (- height 20))
    (.stroke context)

    (.beginPath context)
    (aset context "fillStyle" (str "rgb(" (:r color) "," (:g color) "," (:b color) ")"))
    (.rect context 10 10 (- width 20) (- height 20))
    (.fill context)))

(defn start-loop
  []
  (let [loop (fn loop []
               (draw)
               (js/setTimeout loop 1000))]
    (loop)))

(defn init []
  (setup)
  (start-loop))
