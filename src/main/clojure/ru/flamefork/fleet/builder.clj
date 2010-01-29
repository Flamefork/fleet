(ns ru.flamefork.fleet.builder
  (:use
    [clojure.contrib.def]
    [ru.flamefork.fleet runtime util])
  (:require
    [clojure.contrib.str-utils2 :as su]))

(defn- consume)

(defvar- consumers {
  :text  #(str "\n(raw \"" (escape-string %) "\")")
  :clj   bypass
  :embed #(raw (apply str ["\n(screen escape-fn \n(" (raw (apply str (map consume %))) "))"]))
  :tpl   #(raw (apply str ["\n(screen escape-fn [" (raw (apply str (map consume %))) "])"]))
  })

(defn- consume
  [ast]
  (let [[type content] ast
        consumer (type consumers)]
    (consumer content)))

(defn build
  "Build Clojure forms from template-str."
  [args ast]
  (str
    "(do\n"
    "(use 'ru.flamefork.fleet.runtime)\n"
    "(fn [escape-fn " (su/join " " args) "]"
    (consume ast) "))\n"))