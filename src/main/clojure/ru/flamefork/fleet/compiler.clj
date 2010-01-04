(ns ru.flamefork.fleet.compiler
  (:use
    [clojure.contrib.def])
  (:require
    [clojure.contrib.str-utils2 :as s]))

(defn- consume)

(defn- gen-children-consumer
  [s1 s2 mode]
  (fn [ast] (str "(apply str " s1 (apply str (map #(consume % mode) ast)) s2 ")")))

(defvar- consumers {
  :tpl {
    :text  #(str " \"" % "\" ")
    :embed (gen-children-consumer "(" ")" :embed)
    }
  :embed {
    :text  #(str " " % " ")
    :tpl (gen-children-consumer "[" "]" :tpl)
    }
  })

(defn- consume
  [ast mode]
  (let [c ((mode consumers) (first ast))]
    (c (second ast))))

(defn- compile-to-str
  "Compilates template-str into clojure code."
  [args ast]
  (str "(fn [" (s/join " " args) "] " (consume ast :embed) ")"))

(defn compile-to-fn
  "Creates anonymous function from AST. Args are '(list of args)."
  [args ast]
  (eval (read-string (compile-to-str args ast))))
