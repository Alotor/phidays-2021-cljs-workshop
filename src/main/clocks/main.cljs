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
  [])

(defn random-color
  [])

(defn draw
  [])

(defn start-loop
  []
  (let [loop (fn loop []
               (draw)
               (js/setTimeout loop 1000))]
    (loop)))

(defn init []
  (setup)
  (start-loop))
