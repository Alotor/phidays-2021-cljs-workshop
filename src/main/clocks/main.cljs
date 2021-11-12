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


(def counter (atom 0))

(defn refresh-text
  []
  ;; Get the #counter node
  ;; Change textContent to reflect the current counter value
  (let [node (.getElementById js/document "counter")]
    (aset node "textContent" (str @counter))))

(defn add-counter
  [event]
  ;; Add to counter
  ;; Refresh text
  (swap! counter (fn [old-value] (+ old-value 1)))
  (refresh-text))

(defn sub-counter
  [event]
  ;; Substract from counter
  ;; Refresh text
  (swap! counter (fn [old-value] (- old-value 1)))
  (refresh-text))

(defn setup []
  ;; Get button nodes #add-btn #sub-btn
  ;; Add event listeners
  (let [add-btn (.getElementById js/document "add-btn")
        sub-btn (.getElementById js/document "sub-btn")]
    (.addEventListener add-btn "click" add-counter)
    (.addEventListener sub-btn "click" sub-counter)))
