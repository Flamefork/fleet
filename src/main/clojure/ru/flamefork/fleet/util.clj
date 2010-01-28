(ns ru.flamefork.fleet.util)

(defn escape-string
  "Escapes common string causes, like for Java or JS."
  [s]
  (.. s
    (replace "\\" "\\\\")
    (replace "\"" "\\\"")
    (replace "\n" "\\n")))
