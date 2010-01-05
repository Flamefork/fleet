(ns ru.flamefork.fleet.loader
  (:use
    [clojure.contrib.def])
  (:import
    [java.io File]))

(defvar *fleet-template-paths*
  (atom '("."))
  "List of path used for template search.")

(defn find-file
  [name]
  (let [filename (str (.replace name \- \_) ".fleet")
        options (map #(File. % filename) @*fleet-template-paths*)
        file (first (filter #(.exists %) options))]
    (if file
      (.getCanonicalFile file)
      (throw (java.io.FileNotFoundException. (str "Fleet template file '" filename "' not found."))))))
