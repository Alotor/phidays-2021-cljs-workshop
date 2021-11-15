(ns clocks.main
  (:require
   [clocks.draw :as d]
   [clocks.date :as dt]
   [clocks.math :as mth]))

(def pomodoro-time 25)

(defonce request-id (atom nil))
(defonce start-time (atom nil))

(defn update-text
  []
  (let [seconds-left (- (* pomodoro-time 60) (dt/diff-seconds @start-time (dt/now)))
        text
        (if (<= seconds-left 0)
          (str "Time's over")

          (let [minutes (mth/floor (/ seconds-left 60))
                seconds (mth/floor (mod seconds-left 60))]
            (str "Time left: " minutes " minutes, " seconds " seconds.")))

        node (.getElementById js/document "time-left")]

    (aset node "textContent" text)))

(defn draw
  [context width height]
  (let [radius       (* width 0.40)
        cx           (/ width 2)
        cy           (/ height 2)
        current-time (dt/now)
        {:keys [minute second]} @start-time
        angle (* minute (/ 360 60))]

    (.clearRect context 0 0 width height)
    (d/draw-clock context cx cy radius)
    (d/draw-hands context cx cy radius current-time)

    (when @start-time
      (d/time-slice context cx cy radius angle (* pomodoro-time (/ 360 60))))))

(defn setup []
  (let [node    (.getElementById js/document "clock")
        context (.getContext node "2d")
        rect    (.getBoundingClientRect node)
        width   (.-width rect)
        height  (.-height rect)

        update-fn
        (fn []
          (draw context width height)
          (when @start-time
            (update-text)))

        request-cb
        (fn request-cb []
          (update-fn)
          (reset! request-id (.requestAnimationFrame js/window request-cb)))]

    (unchecked-set node "width" width)
    (unchecked-set node "height" height)

    (request-cb)))

(defn init
  []
  (setup))

(defn ^:export handle-start [event]
  (reset! start-time (dt/parse-date (js/Date.)))
  (let [node (.getElementById js/document "stop-btn")]
    (aset node "disabled" false)))

(defn ^:export handle-stop [event]
  (reset! start-time nil)
  (let [btn-node (.getElementById js/document "stop-btn")
        text-node (.getElementById js/document "time-left")]
    (aset btn-node "disabled" true)
    (aset text-node "textContent" "")))

(defn ^:dev/before-load stop
  []
  (when @request-id
    (.cancelAnimationFrame js/window @request-id)
    (reset! request-id nil)))

(defn ^:dev/after-load reload
  []
  (init))
