(ns clocks.main)

(declare init)

(defn ^:dev/before-load stop
  [])

(defn ^:dev/after-load reload
  []
  (init))

(defn init []
  (let [main (.getElementById js/document "main")]
    (aset main "innerHTML" "<h1 style=\"color: DarkOrchid;\">WELCOME TO CLOJURE!!</h1>")))
