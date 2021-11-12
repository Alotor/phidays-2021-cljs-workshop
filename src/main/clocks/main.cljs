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
  (let [node (.getElementById js/document "text")]
    (aset node "textContent" "Something Nice!"))
  )

(defn setup
  []
  ;; Get button node #click-btn
  ;; Add "click" event listener
  (let [btn (.getElementById js/document "click-btn")]
    (.addEventListener btn "click" handle-click)))
