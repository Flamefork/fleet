(ns fleet.builder
  (:use
    [clojure.contrib.def]
    [fleet runtime util])
  (:require
    [clojure.contrib.str-utils2 :as su]))

(defn- consume)

(defvar- consumers {
  :text  #(str "(raw \"" (escape-clj-string %) "\")")
  :clj   bypass
  :embed #(raw (apply str ["(screen escape-fn (" (raw (apply str (map consume %))) "))"]))
  :tpl   #(raw (apply str ["(screen escape-fn [" (raw (apply str (map consume %))) "])"]))
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
    "(do "
    "(use 'fleet.runtime)"
    "(fn [escape-fn " (su/join " " args) "] "
    (consume ast) "))"))