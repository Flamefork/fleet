(ns ru.flamefork.fleet.compiler
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

(defn- compile-to-str
  "Compilates template-str into clojure code."
  [args ast]
  (str "
  (do
    (use 'ru.flamefork.fleet.runtime)
    (fn [" (s/join " " args) "]"
    (consume ast) "))"))

(defn compile-to-fn
  "Creates anonymous function from AST."
  [args ast]
  (eval (read-string (compile-to-str args ast))))
