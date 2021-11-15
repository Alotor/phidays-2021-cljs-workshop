(ns clocks.date)

(defn parse-date [date]
  {:hour   (.getHours date)
   :minute (.getMinutes date)
   :second (.getSeconds date)
   :time   (.getTime date)})

(defn now
  []
  (parse-date (js/Date.)))

(defn diff-seconds [{from-time :time} {to-time :time}]
  (/ (- to-time from-time) 1000))


