(ns clocks.date)

(defn parse-date [date]
  {:hour   (.getHours date)
   :minute (.getMinutes date)
   :second (.getSeconds date)})

(defn now
  []
  (parse-date (js/Date.)))

(defn seconds-left [start-time]
  (let [current-time (now)]
    (+ (* 60 (- (+ 25 (:minute start-time))
                (:minute current-time)))
       (- (:second start-time)
          (:second current-time)))))

(defn time-over? [start-time]
  (<= (seconds-left start-time) 0))
