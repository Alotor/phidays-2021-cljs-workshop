(ns clocks.main)

(declare init)
(declare setup)

(defn ^:dev/before-load stop
  [])

(defn ^:dev/after-load reload
  []
  (init))

(defn init []
  (setup))

(defn handle-click
  [event]
  ;; Get text node
  ;; Change text content
  )

(defn setup
  []
  ;; Get button node #click-btn
  ;; Add "click" event listener
  )
