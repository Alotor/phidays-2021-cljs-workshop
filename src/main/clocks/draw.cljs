(ns clocks.draw
  (:require
   [clocks.math :as mth]))

(def PI (.-PI js/Math))
(def TWO_PI (* 2 PI))

(defn deg->rad [angle]
  "Convers degrees to radians"
  (* angle (/ PI 180)))

(defn get-point
  "Given the coordinates of a circle
   - center (cx,cy)
   - radius Returns a point in the angle and a distance (from 0 to 1) where 0 is the center and 1 is the perimeter"
  ([cx cy radius angle]
   (get-point cx cy radius angle 1))
  ([cx cy radius angle dist]
   (let [angle (deg->rad angle)]
     {:x (+ cx (* (.sin js/Math angle) (* radius dist)))
      :y (- cy (* (.cos js/Math angle) (* radius dist)))})))

(defn circle
  "Draws a circle in the canvas context `ctx`"
  [ctx cx cy radius]
  (aset ctx "strokeStyle" "black")
  (aset ctx "lineWidth" 4)
  (.beginPath ctx)
  (.arc ctx cx cy radius 0 TWO_PI)
  (.stroke ctx)
  ctx)

(defn text
  "Draws text in the canvas context `ctx`"
  [ctx {:keys [x y]} text]
  (aset ctx "font" "24px Arial")
  (aset ctx "textAlign" "center")
  (aset ctx "textBaseline" "middle")
  (aset ctx "fillStyle" "black")
  (.fillText ctx text x y)
  ctx)

(defn line
  "Draws a line in the context `ctx` from point `p1` to point `p2`.
  Optionaly can receive a `width` and a `color`"
  ([ctx p1 p2]
   (line ctx p1 p2 1 "black"))

  ([ctx {x1 :x y1 :y} {x2 :x y2 :y} width color]
   (aset ctx "strokeStyle" color)
   (aset ctx "lineWidth" width)
   (.beginPath ctx)
   (.moveTo ctx x1 y1)
   (.lineTo ctx x2 y2)
   (.stroke ctx)
   ctx))

(defn time-slice
  "Draws a \"pizza slice\" shape"
  ([ctx cx cy radius from-angle angle]
   (time-slice ctx cx cy radius from-angle angle "rgba(255,0,0,0.2)"))
  ([ctx cx cy radius from-angle angle color]
   (aset ctx "fillStyle" color)
   (let [to-angle   (+ from-angle angle)
         from-angle (deg->rad (- from-angle 90))
         to-angle   (deg->rad (- to-angle 90))]
     (.beginPath ctx)
     (.moveTo ctx cx cy)
     (.arc ctx cx cy radius from-angle to-angle)
     (.fill ctx)
     ctx)))

(defn draw-clock
  [ctx cx cy radius]

  ;; Draw outside border
  (circle ctx cx cy radius)

  ;; Draw small lines marking hours/minutes
  (doseq [hour (range 1 13 0.2)]
    (let [angle (* hour (/ 360 12))
          line-start (if (mth/round? hour) 0.85 0.92)
          p1 (get-point cx cy radius angle line-start)
          p2 (get-point cx cy radius angle 0.95)]
      (line ctx p1 p2)))

  ;; Draw hour numbers
  (doseq [hour (range 1 13)]
    (let [angle (* hour (/ 360 12))
          p (get-point cx cy radius angle 0.75)]
      (text ctx p (str hour)))))

(defn draw-hands
  [ctx cx cy radius {:keys [hour minute second]}]

  ;; Draws the three clock hands
  (let [hands [[hour   "red"    12 6 0.40]
               [minute "blue"   60 2 0.65]
               [second "silver" 60 1 0.65]]]

    (doseq [[value color divisions line-width line-size] hands]
      (let [angle (* value (/ 360 divisions))
            p1 (get-point cx cy radius angle -0.05)
            p2 (get-point cx cy radius angle line-size)]
        (line ctx p1 p2 line-width color))))

  ;; Draw the small dot on the center of the clock
  (circle ctx cx cy (* radius 0.01)))
