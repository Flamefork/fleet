(ns fleet.util)

(defn bypass
  "Just returns it's argument"
  [arg]
  arg)

(defn escape-clj-string
  "Escapes Clojure string."
  [s]
  (.. s
    (replace "\\" "\\\\")
    (replace "\"" "\\\"")))

(defn escape-string
  "Escapes common string causes, like for Java or JS."
  [s]
  (.. s
    (replace "\\" "\\\\")
    (replace "\"" "\\\"")
    (replace "\n" "\\n")))
