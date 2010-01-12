(ns ru.flamefork.fleet.builder
  (:use
    [clojure.contrib.def]
    [ru.flamefork.fleet.runtime])
  (:require
    [clojure.contrib.str-utils2 :as s]))

(defn- consume)

(defvar- consumers {
  :text  #(raw (str " (raw \"" % "\") "))
  :clj   #(raw (str " " % " "))
  :embed #(raw (apply str ["(screen f (" (raw (apply str (map consume %))) "))"]))
  :tpl   #(raw (apply str ["(screen f [" (raw (apply str (map consume %))) "])"]))
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
    (fn [" (s/join " " args) "]
    (let [f @ru.flamefork.fleet/escape-fn]"
    (consume ast) ")))")))
