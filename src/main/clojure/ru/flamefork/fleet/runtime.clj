(ns ru.flamefork.fleet.runtime
  (:import
    [clojure.lang Sequential IObj]
    [ru.flamefork.util CljString]))

;; copied from clojure.contrib.lazy-xml
(def #^{:private true}
     escape-xml-map
     (zipmap "'<>\"&" (map #(str \& % \;) '[apos lt gt quot amp])))
(defn- escape-xml [text]
  (apply str (map #(escape-xml-map % %) (.toString text))))

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
  class)

(defmethod screen CharSequence
  [s]
  (raw 
    (if (raw? s)
      s
      (escape-xml s))))

(defmethod screen Sequential
  [s]
  (raw (apply str (map screen s))))
