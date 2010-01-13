(ns ru.flamefork.fleet
  (:require
    [clojure.contrib.lazy-xml])
  (:use
    [clojure.contrib.def]
    [ru.flamefork.fleet loader parser builder]))

;;
; Private
;;

(defvar- search-paths)

(defn fleet-
  [args template-str]
  (eval (build args (parse template-str))))

(defn read-template-
  [fn-name]
  (slurp (.getPath (find-file (name fn-name) @search-paths))))

;;
; Public Low-Level
;;

(defvar search-paths
  (atom [])
  "Template files search paths.")

(defvar escape-fn
  (atom clojure.contrib.lazy-xml/escape-xml)
  "Template escaping function.")

(defmacro fleet
  "Creates anonymous function from template containing in template-str."
  [args template-str]
  `(fleet- '~args ~template-str))

;;
; Public High-Level
;;

(defn add-search-path
  "Add path to end of search path list."
  [path]
  (swap! search-paths conj path))

(defmacro deftemplate
  "Creates function with name fn-name and defined args from source.
  Source could be ommited, in this case Fleet will inflect template file name from fn-name."
  ([fn-name args]
    `(def ~fn-name (fleet ~args (read-template- '~fn-name))))
  ([fn-name args source]
    `(def ~fn-name (fleet ~args ~source))))
