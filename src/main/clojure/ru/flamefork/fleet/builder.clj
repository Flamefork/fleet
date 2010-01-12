(ns ru.flamefork.fleet.builder
  (:use
    [clojure.contrib.def])
  (:require
    [clojure.contrib.str-utils2 :as s]))

(defn- consume)

(defn- gen-children-consumer
  [s1 s2]
  (fn [ast] (str "(apply str " s1 (apply str (map consume ast)) s2 ")")))

(defvar- consumers {
  :text #(str " \"" % "\" ")
  :clj  #(str " " % " ")
  :embed (gen-children-consumer "(" ")")
  :tpl   (gen-children-consumer "[" "]")
  })

(defn- consume
  [ast]
  (let [[type content] ast
        consumer (type consumers)]
    (consumer content)))

(defn build
  "Build Clojure forms from template-str."
  [args ast]
  (read-string (str "
  (do
    (use 'ru.flamefork.fleet.runtime)
    (fn [" (s/join " " args) "]"
    (consume ast) "))")))
