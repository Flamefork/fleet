(ns fleet.runtime
  (:import
    [clojure.lang Sequential IObj]
    [fleet.util CljString]))

(defn raw
  "Prevent encoding of string."
  [s]
  (with-meta
    (if (instance? IObj s)
      s
      (CljString. (.toString s)))
    {:raw true}))

(defn raw?
  [s]
  (:raw (meta s)))

(defmulti screen
  "Process and collect template string(s)."
  (fn [f s] (class s)))

(defmethod screen CharSequence
  [f s]
  (raw (if (raw? s) s (f s))))

(defmethod screen Sequential
  [f s]
  (raw (apply str (map (partial screen f) s))))

(defmethod screen nil
  [f s]
  (raw ""))
