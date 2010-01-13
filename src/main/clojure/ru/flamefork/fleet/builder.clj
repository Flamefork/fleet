(ns ru.flamefork.fleet.builder
  (:use
    [clojure.contrib.def]
    [ru.flamefork.fleet.runtime])
  (:require
    [clojure.contrib.str-utils2 :as s]))

(defn- consume)

(defvar- consumers {
  :text  #(raw (str "(raw \"" % "\")"))
  :clj   #(raw (str % "\n"))
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
  (read-string (str "
  (do
    (use 'ru.flamefork.fleet.runtime)
    (let [escape-fn @ru.flamefork.fleet/escape-fn]
    (fn [" (s/join " " args) "]"
    (consume ast) ")))")))
