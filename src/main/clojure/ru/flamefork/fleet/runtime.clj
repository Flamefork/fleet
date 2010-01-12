(ns ru.flamefork.fleet.runtime
  (:import
    [clojure.lang Sequential]))

(defmulti screen
  "Process and collect template string(s)."
  class)

(defmethod screen CharSequence
  [s]
  s)

(defmethod screen Sequential
  [s]
  (apply str (map screen s)))
