(ns ru.flamefork.fleet
  (:use
    [ru.flamefork.fleet.parser]
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

(defn- generate
  "Compilates template-str into clojure code."
  [args template-str]
  (let [ast (parse template-str)
        tpl-body (consume ast :embed)]
    (str "(fn [" (s/join " " args) "] " tpl-body ")")))

(defn fleet
  "Creates anonymous function from template containing in template-str. Args are '(list of args)."
  [args template-str]
  (eval (read-string (generate args template-str))))
