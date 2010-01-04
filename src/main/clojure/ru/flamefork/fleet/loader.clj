(ns ru.flamefork.fleet.loader
  (:use
    [clojure.contrib.def])
  (:import
    [java.io File]))

(defvar *fleet-template-paths*
  (atom '("."))
  "List of path used for template search.")

(defn- make-file
  [path name]
  (.getCanonicalFile (new File path (str (.replace name \- \_) ".fleet"))))

(defn find-file
  [name]
  (first (filter #(.exists %) (map #(make-file % name) @*fleet-template-paths*))))
